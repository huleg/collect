/**
 * 
 */
package org.openforis.collect.manager;

import java.util.List;

import org.openforis.collect.exception.MultipleEditException;
import org.openforis.collect.model.CollectRecord;
import org.openforis.collect.model.RecordSummary;
import org.openforis.collect.model.User;
import org.openforis.collect.persistence.AccessDeniedException;
import org.openforis.collect.persistence.DuplicateIdException;
import org.openforis.collect.persistence.InvalidIdException;
import org.openforis.collect.persistence.NonexistentIdException;
import org.openforis.collect.persistence.RecordDAO;
import org.openforis.collect.persistence.RecordLockedException;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author M. Togna
 * 
 */
public class RecordManager {

	@Autowired
	private RecordDAO recordDAO;

	protected void init() {
		unlockAll();
	}

	@Transactional
	public Record create(Survey survey, String entityName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public Record load(String entityName, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void save(Record record) {
		// TODO Auto-generated method stub

	}

	@Transactional
	public void delete(String entityName, long id) {
		// TODO Auto-generated method stub

	}

	/**
	 * Returns a record and lock it
	 * 
	 * @param entityName
	 * @param id
	 * @return
	 */
	@Transactional
	public CollectRecord checkout(Survey survey, User user, int recordId) throws RecordLockedException, NonexistentIdException, AccessDeniedException {
		CollectRecord record = recordDAO.load(survey, recordId);
		recordDAO.lock(recordId, user);
		return record;
	}

	@Transactional
	public List<RecordSummary> getSummaries() {
		// TODO implement getRecordSummaries
		return null;
	}

	@Transactional
	public List<RecordSummary> getSummaries(EntityDefinition rootEntityDefinition, int offset, int maxNumberOfRecords, String orderByFieldName, String filter) {
		List<RecordSummary> recordsSummary = recordDAO.loadRecordSummaries(rootEntityDefinition, offset, maxNumberOfRecords, orderByFieldName, filter);
		return recordsSummary;
	}

	@Transactional
	public int getCountRecords(EntityDefinition rootEntityDefinition, String filter) {
		int count = recordDAO.getCountRecords(rootEntityDefinition, filter);
		return count;
	}

	@Transactional
	public Record create(String name, Survey survey, String rootEntityId) throws MultipleEditException, DuplicateIdException, InvalidIdException, DuplicateIdException, AccessDeniedException,
			RecordLockedException {
		// TODO
		return null;
	}

	@Transactional
	public void lock(Record record) {

	}

	@Transactional
	public void unlock(Record record, User user) throws RecordLockedException {
		recordDAO.unlock(record.getId(), user);
	}

	@Transactional
	public void unlockAll() {
		recordDAO.unlockAll();
	}

	@Transactional
	public void updateRootEntityKey(String recordId, String newRootEntityKey) throws DuplicateIdException, InvalidIdException, NonexistentIdException, AccessDeniedException, RecordLockedException {

	}

	@Transactional
	public void promote(String recordId) throws InvalidIdException, MultipleEditException, NonexistentIdException, AccessDeniedException, RecordLockedException {
	}

	@Transactional
	public void demote(String recordId) throws InvalidIdException, MultipleEditException, NonexistentIdException, AccessDeniedException, RecordLockedException {
	}

}
