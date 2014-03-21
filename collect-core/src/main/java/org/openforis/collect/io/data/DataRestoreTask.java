package org.openforis.collect.io.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipFile;

import org.openforis.collect.io.BackupFileExtractor;
import org.openforis.collect.io.data.BackupDataExtractor.BackupRecordEntry;
import org.openforis.collect.io.exception.DataImportExeption;
import org.openforis.collect.manager.RecordManager;
import org.openforis.collect.manager.UserManager;
import org.openforis.collect.model.CollectRecord;
import org.openforis.collect.model.CollectRecord.Step;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.collect.persistence.RecordPersistenceException;
import org.openforis.collect.persistence.SurveyImportException;
import org.openforis.collect.persistence.xml.DataHandler;
import org.openforis.collect.persistence.xml.DataUnmarshaller;
import org.openforis.collect.persistence.xml.DataUnmarshaller.ParseRecordResult;
import org.openforis.collect.utils.OpenForisIOUtils;
import org.openforis.concurrency.Task;
import org.openforis.idm.model.Entity;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * @author S. Ricci
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DataRestoreTask extends Task {

	private RecordManager recordManager;
	private UserManager userManager;

	//input
	private ZipFile zipFile;

	private CollectSurvey packagedSurvey;
	private CollectSurvey existingSurvey;
	private List<Integer> entryIdsToImport;
	private boolean overwriteAll;
	
	//temporary instance variables
	private DataUnmarshaller dataUnmarshaller;
	private List<Integer> processedRecords;
	private HashMap<String, String> errorByEntryName;
	private BackupFileExtractor backupFileExtractor;
	private boolean oldBackupFormat;
	
	public DataRestoreTask() {
		super();
		this.processedRecords = new ArrayList<Integer>();
		this.errorByEntryName = new HashMap<String, String>();
		this.oldBackupFormat = false;
	}

	@Override
	protected void initInternal() throws Throwable {
		super.initInternal();
		dataUnmarshaller = initDataUnmarshaller(packagedSurvey, existingSurvey);
		backupFileExtractor = new BackupFileExtractor(zipFile);
		oldBackupFormat = backupFileExtractor.isOldFormat();
	}
	
	@Override
	protected void execute() throws Throwable {
		processedRecords = new ArrayList<Integer>();
		for (Integer entryId : entryIdsToImport) {
			if ( isRunning() && ! processedRecords.contains(entryId) ) {
				importEntries(entryId);
				processedRecords.add(entryId);
				incrementItemsProcessed();
			} else {
				break;
			}
		}
	}
	
	private void importEntries(int entryId) throws IOException, DataImportExeption, RecordPersistenceException {
		CollectRecord lastProcessedRecord = null;
		Step originalRecordStep = null;
		Step[] steps = Step.values();
		for (Step step : steps) {
			String entryName = getBackupEntryName(entryId, step);
			InputStream entryIS = backupFileExtractor.findEntryInputStream(entryName);
			if ( entryIS != null ) {
				InputStreamReader reader = OpenForisIOUtils.toReader(entryIS);
				ParseRecordResult parseRecordResult = parseRecord(reader);
				CollectRecord parsedRecord = parseRecordResult.getRecord();
				if (parsedRecord == null) {
					String message = parseRecordResult.getMessage();
					addError(entryName, message);
				} else {
					parsedRecord.setStep(step);
					if ( lastProcessedRecord == null ) {
						CollectRecord oldRecordSummary = findAlreadyExistingRecordSummary(parsedRecord);
						if (oldRecordSummary == null) {
							//insert new record
							recordManager.save(parsedRecord);
							log().info("Inserted: " + parsedRecord.getId() + " (from file " + entryName + ")");
						} else {
							//overwrite existing record
							originalRecordStep = oldRecordSummary.getStep();
							parsedRecord.setId(oldRecordSummary.getId());
							recordManager.save(parsedRecord);
							log().info("Updated: " + oldRecordSummary.getId() + " (from file " + entryName  + ")");
						}
						lastProcessedRecord = parsedRecord;
					} else {
						replaceData(parsedRecord, lastProcessedRecord);
						recordManager.save(lastProcessedRecord);
					}
					if ( parseRecordResult.hasWarnings() ) {
//							addWarnings(entryName, parseRecordResult.getWarnings());
					}
				}
			}
		}
		//reset record step to the original one and revalidate the record
		if ( lastProcessedRecord != null && originalRecordStep != null && originalRecordStep.compareTo(lastProcessedRecord.getStep()) > 0 ) {
			CollectSurvey survey = (CollectSurvey) lastProcessedRecord.getSurvey();
			CollectRecord originalRecord = recordManager.load(survey, lastProcessedRecord.getId(), originalRecordStep);
			originalRecord.setStep(originalRecordStep);
			validateRecord(originalRecord);
			recordManager.save(originalRecord);
		}
	}

	protected String getBackupEntryName(int entryId, Step step) {
		if ( oldBackupFormat ) {
			return step.getStepNumber() + "/" + entryId + ".xml";
		} else {
			BackupRecordEntry recordEntry = new BackupRecordEntry(step, entryId);
			String entryName = recordEntry.getName();
			return entryName;
		}
	}

	private void addError(String entryName, String message) {
		errorByEntryName.put(entryName, message);
	}

	private DataUnmarshaller initDataUnmarshaller(CollectSurvey packagedSurvey, CollectSurvey existingSurvey) throws SurveyImportException {
		CollectSurvey currentSurvey = existingSurvey == null ? packagedSurvey : existingSurvey;
		DataHandler handler = new DataHandler(userManager, currentSurvey, packagedSurvey);
		DataUnmarshaller dataUnmarshaller = new DataUnmarshaller(handler);
		return dataUnmarshaller;
	}

	private CollectRecord findAlreadyExistingRecordSummary(CollectRecord parsedRecord) {
		CollectSurvey survey = (CollectSurvey) parsedRecord.getSurvey();
		List<String> keyValues = parsedRecord.getRootEntityKeyValues();
		Entity rootEntity = parsedRecord.getRootEntity();
		String rootEntityName = rootEntity.getName();
		List<CollectRecord> oldRecords = recordManager.loadSummaries(survey, rootEntityName, keyValues.toArray(new String[0]));
		if ( oldRecords == null || oldRecords.isEmpty() ) {
			return null;
		} else if ( oldRecords.size() == 1 ) {
			return oldRecords.get(0);
		} else {
			throw new IllegalStateException(String.format("Multiple records found in survey %s with key(s): %s", survey.getName(), keyValues));
		}
	}

	private ParseRecordResult parseRecord(Reader reader) throws IOException {
		ParseRecordResult result = dataUnmarshaller.parse(reader);
		if ( result.isSuccess() ) {
			CollectRecord record = result.getRecord();
			validateRecord(record);
			record.updateRootEntityKeyValues();
			record.updateEntityCounts();
		}
		return result;
	}

	private void validateRecord(CollectRecord record) {
		try {
			recordManager.validate(record);
		} catch (Exception e) {
			log().info("Error validating record: " + record.getRootEntityKeyValues());
		}
	}

	private void replaceData(CollectRecord fromRecord, CollectRecord toRecord) {
		toRecord.setCreatedBy(fromRecord.getCreatedBy());
		toRecord.setCreationDate(fromRecord.getCreationDate());
		toRecord.setModifiedBy(fromRecord.getModifiedBy());
		toRecord.setModifiedDate(fromRecord.getModifiedDate());
		toRecord.setStep(fromRecord.getStep());
		toRecord.setState(fromRecord.getState());
		toRecord.setRootEntity(fromRecord.getRootEntity());
		toRecord.updateRootEntityKeyValues();
		toRecord.updateEntityCounts();
		recordManager.validate(toRecord);
	}
	
	public RecordManager getRecordManager() {
		return recordManager;
	}
	
	public void setRecordManager(RecordManager recordManager) {
		this.recordManager = recordManager;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public ZipFile getZipFile() {
		return zipFile;
	}
	
	public void setZipFile(ZipFile zipFile) {
		this.zipFile = zipFile;
	}
	
	public CollectSurvey getPackagedSurvey() {
		return packagedSurvey;
	}
	
	public void setPackagedSurvey(CollectSurvey packagedSurvey) {
		this.packagedSurvey = packagedSurvey;
	}
	
	public CollectSurvey getExistingSurvey() {
		return existingSurvey;
	}
	
	public void setExistingSurvey(CollectSurvey existingSurvey) {
		this.existingSurvey = existingSurvey;
	}
	
	public boolean isOverwriteAll() {
		return overwriteAll;
	}

	public void setOverwriteAll(boolean overwriteAll) {
		this.overwriteAll = overwriteAll;
	}

	public List<Integer> getEntryIdsToImport() {
		return entryIdsToImport;
	}

	public void setEntryIdsToImport(List<Integer> entryIdsToImport) {
		this.entryIdsToImport = entryIdsToImport;
	}

	public boolean isOldBackupFormat() {
		return oldBackupFormat;
	}
	
	public void setOldBackupFormat(boolean oldBackupFormat) {
		this.oldBackupFormat = oldBackupFormat;
	}
}
