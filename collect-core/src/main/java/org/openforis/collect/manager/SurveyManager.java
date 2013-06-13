/**
 * 
 */
package org.openforis.collect.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.openforis.collect.manager.exception.CodeListImportException;
import org.openforis.collect.manager.exception.SurveyValidationException;
import org.openforis.collect.manager.validation.SurveyValidator;
import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.collect.model.CollectSurveyContext;
import org.openforis.collect.model.SurveySummary;
import org.openforis.collect.persistence.RecordDao;
import org.openforis.collect.persistence.SurveyDao;
import org.openforis.collect.persistence.SurveyImportException;
import org.openforis.collect.persistence.SurveyWorkDao;
import org.openforis.collect.utils.CollectIOUtils;
import org.openforis.commons.collection.CollectionUtils;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.IdmlParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author M. Togna
 * @author S. Ricci
 * 
 */
public class SurveyManager {

	@Autowired
	private CodeListManager codeListManager;
	@Autowired
	private SamplingDesignManager samplingDesignManager;
	@Autowired
	private SpeciesManager speciesManager;
	@Autowired
	private SurveyDao surveyDao;
	@Autowired
	private SurveyWorkDao surveyWorkDao;
	@Autowired
	private RecordDao recordDao;
	@Autowired
	private CollectSurveyContext collectSurveyContext;
	@Autowired
	private SurveyValidator validator;
	
	private List<CollectSurvey> surveys;
	private Map<Integer, CollectSurvey> surveysById;
	private Map<String, CollectSurvey> surveysByName;
	private Map<String, CollectSurvey> surveysByUri;

	public SurveyManager() {
		surveys = new ArrayList<CollectSurvey>();
		surveysById = new HashMap<Integer, CollectSurvey>();
		surveysByName = new HashMap<String, CollectSurvey>();
		surveysByUri = new HashMap<String, CollectSurvey>();
	}

	@Transactional
	public void init() {
		initSurveysCache();
	}

	protected void initSurveysCache() {
		surveysById.clear();
		surveysByName.clear();
		surveysByUri.clear();
		surveys = surveyDao.loadAll();
		for (CollectSurvey survey : surveys) {
			addToCache(survey);
		}
	}

	private void addToCache(CollectSurvey survey) {
		if ( ! surveys.contains(survey) ) {
			surveys.add(survey);
		}
		surveysById.put(survey.getId(), survey);
		surveysByName.put(survey.getName(), survey);
		surveysByUri.put(survey.getUri(), survey);
	}
	
	protected void removeFromCache(CollectSurvey survey) {
		surveys.remove(survey);
		surveysById.remove(survey.getId());
		surveysByName.remove(survey.getName());
		surveysByUri.remove(survey.getUri());
	}
	
	public List<CollectSurvey> getAll() {
		return CollectionUtils.unmodifiableList(surveys);
	}
	
	@Transactional
	public CollectSurvey get(String name) {
		CollectSurvey survey = surveysByName.get(name);
		return survey;
	}
	
	@Transactional
	public CollectSurvey getById(int id) {
		CollectSurvey survey = surveysById.get(id);
		return survey;
	}
	
	@Transactional
	public CollectSurvey getByUri(String uri) {
		CollectSurvey survey = surveysByUri.get(uri);
		return survey;
	}
	
	@Transactional
	public CollectSurvey importModel(InputStream is, String name, boolean overwrite, boolean validate)
			throws SurveyImportException, SurveyValidationException {
		File tempFile = null;
		try {
			tempFile = CollectIOUtils.copyToTempFile(new InputStreamReader(is));
			return importModel(tempFile, name, overwrite,
					validate);
		} finally {
			tempFile.delete();
		}
	}

	@Transactional
	public CollectSurvey importModel(File surveyFile, String name,
			boolean overwrite, boolean validate) throws SurveyImportException, SurveyValidationException {
		try {
			if ( validate ) {
				validator.validateAgainstSchema(surveyFile);
			}
			CollectSurvey survey = unmarshallSurvey(surveyFile, true);
			if ( validate ) {
				validator.validate(survey);
			}
			SurveySummary oldSurveyWork = loadSurveyWorkSummaryByUri(survey.getUri());
			CollectSurvey oldPublishedSurvey = getByUri(survey.getUri());
			if ( oldSurveyWork == null && oldPublishedSurvey == null ) {
				importNewModel(surveyFile, name, survey);
			} else if ( ! overwrite ) {
				throw new IllegalArgumentException("Survey already existing but not asking for overwite");
			} else if ( oldPublishedSurvey != null ) {
				updatePublishedSurvey(surveyFile, survey, validate);
			} else {
				updateExistingSurveyWork(surveyFile, survey, oldSurveyWork);
			}
			return survey;
		} catch ( CodeListImportException e ) {
			throw new SurveyImportException(e);
		} catch (IdmlParseException e) {
			throw new SurveyImportException(e);
		}
	}

	protected void updateExistingSurveyWork(File surveyFile,
			CollectSurvey survey, SurveySummary oldSurveyWorkSummary)
			throws SurveyImportException, CodeListImportException {
		Integer id = oldSurveyWorkSummary.getId();
		survey.setId(id);
		survey.setName(oldSurveyWorkSummary.getName());
		survey.setWork(true);
		codeListManager.deleteBySurvey(id, true);
		saveSurveyWork(survey);
		codeListManager.parseXMLAndStoreItems(survey, surveyFile);
	}

	protected void updatePublishedSurvey(File surveyFile,
			CollectSurvey survey, boolean validate) throws SurveyValidationException,
			SurveyImportException, CodeListImportException {
		CollectSurvey oldPublishedSurvey = getByUri(survey.getUri());
		Integer id = oldPublishedSurvey.getId();
		survey.setId(id);
		survey.setName(oldPublishedSurvey.getName());
		if ( validate ) {
			validator.checkCompatibility(oldPublishedSurvey, survey);
		}
		codeListManager.deleteBySurvey(id, false);
		updateModel(survey);
		codeListManager.parseXMLAndStoreItems(survey, surveyFile);
	}

	protected void importNewModel(File surveyFile, String name, CollectSurvey survey)
			throws SurveyImportException, CodeListImportException {
		survey.setName(name);
		surveyDao.importModel(survey);
		addToCache(survey);
		codeListManager.parseXMLAndStoreItems(survey, surveyFile);
	}
	
	@Deprecated
	@Transactional
	public void importModel(CollectSurvey survey) throws SurveyImportException {
		surveyDao.importModel(survey);
		addToCache(survey);
	}
	
	@Deprecated
	@Transactional
	public void updateModel(CollectSurvey survey) throws SurveyImportException {
		//remove old survey from surveys cache
		CollectSurvey oldSurvey = surveysByName.get(survey.getName());
		if ( oldSurvey != null ) {
			removeFromCache(oldSurvey);
		} else {
			throw new SurveyImportException("Could not find survey to update");
		}
		surveyDao.updateModel(survey);
		addToCache(survey);
	}

	@Transactional
	public List<SurveySummary> getSurveySummaries(String lang) {
		List<SurveySummary> summaries = new ArrayList<SurveySummary>();
		for (Survey survey : surveys) {
			Integer id = survey.getId();
			String projectName = survey.getProjectName(lang);
			String name = survey.getName();
			String uri = survey.getUri();
			SurveySummary summary = new SurveySummary(id, name, uri, projectName);
			summaries.add(summary);
		}
		sortByName(summaries);
		return summaries;
	}

	protected void sortByName(List<SurveySummary> summaries) {
		Collections.sort(summaries, new Comparator<SurveySummary>() {
			@Override
			public int compare(SurveySummary s1, SurveySummary s2) {
				return s1.getName().compareTo(s2.getName());
			}
		});
	}
	
	public String marshalSurvey(Survey survey)  {
		try {
			String result = surveyDao.marshalSurvey(survey);
			return result;
		} catch (SurveyImportException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void marshalSurvey(Survey survey, OutputStream os)  {
		try {
			surveyDao.marshalSurvey(survey, os);
		} catch (SurveyImportException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public CollectSurvey unmarshalSurvey(InputStream is) throws IdmlParseException, SurveyValidationException {
		return unmarshalSurvey(is, false, false);
	}
	
	public CollectSurvey unmarshalSurvey(InputStream is,
			boolean validateAgainstSchema, boolean skipCodeListItems)
			throws IdmlParseException, SurveyValidationException {
		InputStreamReader reader = new InputStreamReader(is);
		return unmarshalSurvey(reader, validateAgainstSchema, skipCodeListItems);
	}

	public CollectSurvey unmarshalSurvey(Reader reader) throws IdmlParseException, SurveyValidationException {
		return unmarshalSurvey(reader, false, false);
	}
	
	public CollectSurvey unmarshalSurvey(Reader reader,
			boolean validateAgainstSchema, boolean skipCodeListItems)
			throws IdmlParseException, SurveyValidationException {
		if ( validateAgainstSchema ) {
			File tempFile = CollectIOUtils.copyToTempFile(reader);
			validateSurveyXMLAgainstSchema(tempFile);
			CollectSurvey result = unmarshallSurvey(tempFile, skipCodeListItems);
			tempFile.delete();
			return result;
		} else {
			return surveyDao.unmarshalIdml(reader, skipCodeListItems);
		}
	}

	protected CollectSurvey unmarshallSurvey(File file, boolean skipCodeListItems) throws IdmlParseException {
		FileInputStream tempIs = null;
		try {
			tempIs = new FileInputStream(file);
			return surveyDao.unmarshalIdml(tempIs, skipCodeListItems);
		} catch (Exception e) {
			//should never enter here
			throw new RuntimeException(e); 
		} finally {
			IOUtils.closeQuietly(tempIs);
		}
	}

	protected void validateSurveyXMLAgainstSchema(File file) throws SurveyValidationException {
		validator.validateAgainstSchema(file);
	}

	@Transactional
	public List<SurveySummary> loadSurveySummaries() {
		List<SurveySummary> result = surveyDao.loadSummaries();
		return CollectionUtils.unmodifiableList(result);
	}
	
	@Transactional
	public CollectSurvey loadSurveyWork(int id) {
		return surveyWorkDao.load(id);
	}
	
	@Transactional
	public List<SurveySummary> loadSurveyWorkSummaries() {
		List<SurveySummary> result = surveyWorkDao.loadSummaries();
		return result;
	}
	
	@Transactional
	public SurveySummary loadSurveyWorkSummary(int id) {
		return surveyWorkDao.loadSurveySummary(id);
	}
	
	@Transactional
	public SurveySummary loadSurveyWorkSummaryByName(String name) {
		return surveyWorkDao.loadSurveySummaryByName(name);
	}
	
	@Transactional
	public SurveySummary loadSurveyWorkSummaryByUri(String uri) {
		return surveyWorkDao.loadSurveySummaryByUri(uri);
	}
	
	@Transactional
	public CollectSurvey loadPublishedSurveyForEdit(String uri) {
		CollectSurvey surveyWork = surveyWorkDao.loadByUri(uri);
		if ( surveyWork == null ) {
			CollectSurvey publishedSurvey = (CollectSurvey) surveyDao.loadByUri(uri);
			surveyWork = createSurveyWork(publishedSurvey);
		}
		return surveyWork;
	}

	@Transactional
	public boolean isSurveyWork(CollectSurvey survey) {
		Integer id = survey.getId();
		String name = survey.getName();
		SurveySummary workSurveySummary = loadSurveyWorkSummaryByName(name);
		if (workSurveySummary == null || workSurveySummary.getId() != id ) {
			CollectSurvey publishedSurvey = get(name);
			if (publishedSurvey == null || publishedSurvey.getId() != id ) {
				throw new IllegalStateException("Survey with name '" + name
						+ "' not found");
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
	public CollectSurvey createSurveyWork() {
		CollectSurvey survey = (CollectSurvey) collectSurveyContext.createSurvey();
		UIOptions uiOptions = survey.createUIOptions();
		survey.addApplicationOptions(uiOptions);
		return survey;
	}
	
	protected CollectSurvey createSurveyWork(CollectSurvey survey) {
//		CollectSurvey surveyWork = survey.clone();
		CollectSurvey surveyWork = survey;
		surveyWork.setId(null);
		surveyWork.setPublished(true);
		surveyWork.setWork(true);
		return surveyWork;
	}
	
	@Transactional
	public void saveSurveyWork(CollectSurvey survey) throws SurveyImportException {
		Integer id = survey.getId();
		if ( id == null ) {
			surveyWorkDao.insert(survey);
			CollectSurvey publishedSurvey = surveyDao.loadByUri(survey.getUri());
			if ( publishedSurvey != null ) {
				int surveyWorkId = survey.getId();
				int publishedSurveyId = publishedSurvey.getId();
				samplingDesignManager.duplicateSamplingDesignForWork(publishedSurveyId, surveyWorkId);
				speciesManager.duplicateTaxonomyForWork(publishedSurveyId, surveyWorkId);
			}
		} else {
			surveyWorkDao.update(survey);
		}
	}
	
	@Transactional
	public void publish(CollectSurvey survey) throws SurveyImportException {
		Integer surveyWorkId = survey.getId();
		CollectSurvey publishedSurvey = get(survey.getName());
		if ( publishedSurvey == null ) {
			survey.setPublished(true);
			importModel(survey);
			initSurveysCache();
		} else {
			updateModel(survey);
		}
		if ( surveyWorkId != null ) {
			int publishedSurveyId = survey.getId();
			samplingDesignManager.publishSamplingDesign(surveyWorkId, publishedSurveyId);
			speciesManager.publishTaxonomies(surveyWorkId, publishedSurveyId);
			surveyWorkDao.delete(surveyWorkId);
		}
	}
	
	@Transactional
	public void deleteSurvey(Integer id) {
		CollectSurvey survey = getById(id);
		if ( survey != null ) {
			recordDao.deleteBySurvey(id);
			speciesManager.deleteTaxonomiesBySurvey(id);
			samplingDesignManager.deleteBySurvey(id);
			surveyDao.delete(id);
			removeFromCache(survey);
		}
	}
	
	@Transactional
	public void deleteSurveyWork(Integer id) {
		speciesManager.deleteTaxonomiesBySurveyWork(id);
		samplingDesignManager.deleteBySurveyWork(id);
		surveyWorkDao.delete(id);
	}

	/*
	 * Getters and setters
	 * 
	 */
	public SamplingDesignManager getSamplingDesignManager() {
		return samplingDesignManager;
	}

	public void setSamplingDesignManager(SamplingDesignManager samplingDesignManager) {
		this.samplingDesignManager = samplingDesignManager;
	}

	public SpeciesManager getSpeciesManager() {
		return speciesManager;
	}

	public void setSpeciesManager(SpeciesManager speciesManager) {
		this.speciesManager = speciesManager;
	}

	public SurveyDao getSurveyDao() {
		return surveyDao;
	}

	public void setSurveyDao(SurveyDao surveyDao) {
		this.surveyDao = surveyDao;
	}

	public SurveyWorkDao getSurveyWorkDao() {
		return surveyWorkDao;
	}

	public void setSurveyWorkDao(SurveyWorkDao surveyWorkDao) {
		this.surveyWorkDao = surveyWorkDao;
	}

	public CollectSurveyContext getCollectSurveyContext() {
		return collectSurveyContext;
	}

	public void setCollectSurveyContext(CollectSurveyContext collectSurveyContext) {
		this.collectSurveyContext = collectSurveyContext;
	}

}
