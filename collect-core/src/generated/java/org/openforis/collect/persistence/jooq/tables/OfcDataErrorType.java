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
public class OfcDataErrorType extends org.jooq.impl.TableImpl<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord> {

	private static final long serialVersionUID = -864997453;

	/**
	 * The reference instance of <code>collect.ofc_data_error_type</code>
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
	 * The column <code>collect.ofc_data_error_type.id</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_data_error_type.survey_id</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, java.lang.Integer> SURVEY_ID = createField("survey_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_data_error_type.code</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, java.lang.String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_data_error_type.label</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, java.lang.String> LABEL = createField("label", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>collect.ofc_data_error_type.description</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * Create a <code>collect.ofc_data_error_type</code> table reference
	 */
	public OfcDataErrorType() {
		this("ofc_data_error_type", null);
	}

	/**
	 * Create an aliased <code>collect.ofc_data_error_type</code> table reference
	 */
	public OfcDataErrorType(java.lang.String alias) {
		this(alias, org.openforis.collect.persistence.jooq.tables.OfcDataErrorType.OFC_DATA_ERROR_TYPE);
	}

	private OfcDataErrorType(java.lang.String alias, org.jooq.Table<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord> aliased) {
		this(alias, aliased, null);
	}

	private OfcDataErrorType(java.lang.String alias, org.jooq.Table<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, org.openforis.collect.persistence.jooq.Collect.COLLECT, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord> getPrimaryKey() {
		return org.openforis.collect.persistence.jooq.Keys.OFC_DATA_ERROR_TYPE_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord>>asList(org.openforis.collect.persistence.jooq.Keys.OFC_DATA_ERROR_TYPE_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataErrorTypeRecord, ?>>asList(org.openforis.collect.persistence.jooq.Keys.OFC_DATA_ERROR_TYPE__OFC_DATA_ERROR_TYPE_SURVEY_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.openforis.collect.persistence.jooq.tables.OfcDataErrorType as(java.lang.String alias) {
		return new org.openforis.collect.persistence.jooq.tables.OfcDataErrorType(alias, this);
	}

	/**
	 * Rename this table
	 */
	public org.openforis.collect.persistence.jooq.tables.OfcDataErrorType rename(java.lang.String name) {
		return new org.openforis.collect.persistence.jooq.tables.OfcDataErrorType(name, null);
	}
}
