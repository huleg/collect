/**
 * 
 */
package org.openforis.collect.io;

import java.io.File;
import java.util.zip.ZipFile;

import org.openforis.collect.io.SurveyRestoreJob.ZipFileExtractor;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.concurrency.Job;
import org.openforis.concurrency.WorkerStatusChangeEvent;
import org.openforis.concurrency.WorkerStatusChangeListener;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author S. Ricci
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SurveyUnmarshallJob extends Job {

	//input
	private File file;
	private boolean validate;

	//output
	private CollectSurvey survey;
	
	@Override
	public void init() {
		try {
			ZipFile zipFile = new ZipFile(file);
			File idmlFile = ZipFileExtractor.extractIdmlFile(zipFile);
			final IdmlUnmarshallTask task = createTask(IdmlUnmarshallTask.class);
			task.setFile(idmlFile);
			task.setValidate(validate);
			task.addStatusChangeListener(new WorkerStatusChangeListener() {
				@Override
				public void statusChanged(WorkerStatusChangeEvent event) {
					if ( event.getTo() == Status.COMPLETED ) {
						SurveyUnmarshallJob.this.survey = task.getSurvey();
					}
				}
			});
			addTask(task);
		} catch ( Exception e ) {
			throw new RuntimeException(e);
		}
		super.init();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
	public CollectSurvey getSurvey() {
		return survey;
	}
	
}
