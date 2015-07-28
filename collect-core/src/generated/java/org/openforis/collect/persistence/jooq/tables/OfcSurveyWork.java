/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.4"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OfcSurveyWork extends org.jooq.impl.TableImpl<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord> {

	private static final long serialVersionUID = -1064376438;

	/**
	 * The reference instance of <code>collect.ofc_survey_work</code>
	 */
	public static final org.openforis.collect.persistence.jooq.tables.OfcSurveyWork OFC_SURVEY_WORK = new org.openforis.collect.persistence.jooq.tables.OfcSurveyWork();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord> getRecordType() {
		return org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord.class;
	}

	/**
	 * The column <code>collect.ofc_survey_work.id</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_survey_work.name</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord, java.lang.String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_survey_work.uri</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord, java.lang.String> URI = createField("uri", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_survey_work.idml</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord, java.lang.String> IDML = createField("idml", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_survey_work.target</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord, java.lang.String> TARGET = createField("target", org.jooq.impl.SQLDataType.VARCHAR.length(5).defaulted(true), this, "");

	/**
	 * The column <code>collect.ofc_survey_work.date_created</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord, java.sql.Timestamp> DATE_CREATED = createField("date_created", org.jooq.impl.SQLDataType.TIMESTAMP.defaulted(true), this, "");

	/**
	 * The column <code>collect.ofc_survey_work.date_modified</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord, java.sql.Timestamp> DATE_MODIFIED = createField("date_modified", org.jooq.impl.SQLDataType.TIMESTAMP.defaulted(true), this, "");

	/**
	 * The column <code>collect.ofc_survey_work.collect_version</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord, java.lang.String> COLLECT_VERSION = createField("collect_version", org.jooq.impl.SQLDataType.VARCHAR.length(55).defaulted(true), this, "");

	/**
	 * Create a <code>collect.ofc_survey_work</code> table reference
	 */
	public OfcSurveyWork() {
		this("ofc_survey_work", null);
	}

	/**
	 * Create an aliased <code>collect.ofc_survey_work</code> table reference
	 */
	public OfcSurveyWork(java.lang.String alias) {
		this(alias, org.openforis.collect.persistence.jooq.tables.OfcSurveyWork.OFC_SURVEY_WORK);
	}

	private OfcSurveyWork(java.lang.String alias, org.jooq.Table<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord> aliased) {
		this(alias, aliased, null);
	}

	private OfcSurveyWork(java.lang.String alias, org.jooq.Table<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, org.openforis.collect.persistence.jooq.Collect.COLLECT, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord> getPrimaryKey() {
		return org.openforis.collect.persistence.jooq.Keys.OFC_SURVEY_WORK_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcSurveyWorkRecord>>asList(org.openforis.collect.persistence.jooq.Keys.OFC_SURVEY_WORK_PKEY, org.openforis.collect.persistence.jooq.Keys.OFC_SURVEY_WORK_NAME_KEY, org.openforis.collect.persistence.jooq.Keys.OFC_SURVEY_WORK_URI_KEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.openforis.collect.persistence.jooq.tables.OfcSurveyWork as(java.lang.String alias) {
		return new org.openforis.collect.persistence.jooq.tables.OfcSurveyWork(alias, this);
	}

	/**
	 * Rename this table
	 */
	public org.openforis.collect.persistence.jooq.tables.OfcSurveyWork rename(java.lang.String name) {
		return new org.openforis.collect.persistence.jooq.tables.OfcSurveyWork(name, null);
	}
}
