/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.5.0"},
                            comments = "This class is generated by jOOQ")
public class OfcDataErrorType extends org.jooq.impl.UpdatableTableImpl<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord> {

	private static final long serialVersionUID = -251433406;

	/**
	 * The singleton instance of collect.ofc_data_error_type
	 */
	public static final org.openforis.collect.persistence.jooq.tables.OfcDataErrorType OFC_DATA_ERROR_TYPE = new org.openforis.collect.persistence.jooq.tables.OfcDataErrorType();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord> getRecordType() {
		return org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord.class;
	}

	/**
	 * The table column <code>collect.ofc_data_error_type.id</code>
	 * <p>
	 * This column is part of the table's PRIMARY KEY
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * The table column <code>collect.ofc_data_error_type.survey_id</code>
	 * <p>
	 * This column is part of a FOREIGN KEY: <code><pre>
	 * CONSTRAINT ofc_data_error_type__ofc_data_error_type_survey_fkey
	 * FOREIGN KEY (survey_id)
	 * REFERENCES collect.ofc_survey (id)
	 * </pre></code>
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, java.lang.Integer> SURVEY_ID = createField("survey_id", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * The table column <code>collect.ofc_data_error_type.code</code>
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, java.lang.String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * The table column <code>collect.ofc_data_error_type.label</code>
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, java.lang.String> LABEL = createField("label", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * The table column <code>collect.ofc_data_error_type.description</code>
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR, this);

	public OfcDataErrorType() {
		super("ofc_data_error_type", org.openforis.collect.persistence.jooq.Collect.COLLECT);
	}

	public OfcDataErrorType(java.lang.String alias) {
		super(alias, org.openforis.collect.persistence.jooq.Collect.COLLECT, org.openforis.collect.persistence.jooq.tables.OfcDataErrorType.OFC_DATA_ERROR_TYPE);
	}

	@Override
	public org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord> getMainKey() {
		return org.openforis.collect.persistence.jooq.Keys.OFC_DATA_ERROR_TYPE_PKEY;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord>>asList(org.openforis.collect.persistence.jooq.Keys.OFC_DATA_ERROR_TYPE_PKEY);
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.ForeignKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, ?>>asList(org.openforis.collect.persistence.jooq.Keys.OFC_DATA_ERROR_TYPE__OFC_DATA_ERROR_TYPE_SURVEY_FKEY);
	}

	@Override
	public org.openforis.collect.persistence.jooq.tables.OfcDataErrorType as(java.lang.String alias) {
		return new org.openforis.collect.persistence.jooq.tables.OfcDataErrorType(alias);
	}
}
