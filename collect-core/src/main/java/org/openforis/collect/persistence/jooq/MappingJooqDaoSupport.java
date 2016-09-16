package org.openforis.collect.persistence.jooq;

import java.util.List;
import java.util.Set;

import org.jooq.Configuration;
import org.jooq.DeleteQuery;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.SelectQuery;
import org.jooq.TableField;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author G. Miceli
 */
public class MappingJooqDaoSupport<E, C extends MappingDSLContext<E>> extends JooqDaoSupport {
	protected Class<C> jooqFactoryClass;

	public MappingJooqDaoSupport(Class<C> jooqFactoryClass) {
		this.jooqFactoryClass = jooqFactoryClass;
	}
	
	protected C dsl() {
		try {
			return jooqFactoryClass.getConstructor(Configuration.class).newInstance(getConfiguration());
		} catch (Exception e) {
			throw new RuntimeException("Failed to create "+jooqFactoryClass, e);
		}
	}

	protected Configuration getConfiguration() {
		return super.dsl().configuration();
	}
	
	public List<E> findStartingWith(TableField<?,String> field, String searchString, int maxResults) {
		C dsl = dsl();
		SelectQuery<?> query = dsl.selectStartsWithQuery(field, searchString);
		query.addLimit(maxResults);
		query.execute();
		Result<?> result = query.getResult();
		List<E> entities = dsl.fromResult(result);
		return entities;

	}
	
	public List<E> findContaining(TableField<?,String> field, String searchString, int maxResults) {
		C dsl = dsl();
		SelectQuery<?> query = dsl.selectContainsQuery(field, searchString);
		query.addLimit(maxResults);
		query.execute();
		Result<?> result = query.getResult();
		List<E> entities = dsl.fromResult(result);
		return entities;

	}
	
	@Transactional
	public E loadById(int id) {
		C dsl = dsl();
		ResultQuery<?> selectQuery = dsl.selectByIdQuery(id);
		Record r = selectQuery.fetchOne();
		if ( r == null ) {
			return null;
		} else {
			return dsl.fromRecord(r);
		}
	}
	
	public List<E> loadAll() {
		C dsl = dsl();
		SelectQuery<?> query = dsl.selectQuery(dsl.getTable());
		Result<?> result = query.fetch();
		List<E> entities = dsl.fromResult(result);
		return entities;
	}
	
	@Transactional
	public void insert(E entity) {
		dsl().insertQuery(entity).execute();
	}
	
	@Transactional
	public void update(E entity) {
		dsl().updateQuery(entity).execute();
	}

	@Transactional
	public void delete(int id) {
		dsl().deleteQuery(id).execute();
	}
	
	@Transactional
	public void deleteByIds(Set<Integer> ids) {
		C dsl = dsl();
		DeleteQuery<?> delete = dsl.deleteQuery(dsl.getTable());
		delete.addConditions(dsl.getIdField().in(ids));
		delete.execute();
	}


}
