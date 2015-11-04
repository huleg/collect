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
public class OfcDataReport extends org.jooq.impl.TableImpl<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord> {

	private static final long serialVersionUID = 956012659;

	/**
	 * The reference instance of <code>collect.ofc_data_report</code>
	 */
	public static final org.openforis.collect.persistence.jooq.tables.OfcDataReport OFC_DATA_REPORT = new org.openforis.collect.persistence.jooq.tables.OfcDataReport();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord> getRecordType() {
		return org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord.class;
	}

	/**
	 * The column <code>collect.ofc_data_report.id</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_data_report.uuid</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord, java.lang.String> UUID = createField("uuid", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>collect.ofc_data_report.query_group_id</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord, java.lang.Integer> QUERY_GROUP_ID = createField("query_group_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_data_report.record_step</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord, java.lang.Integer> RECORD_STEP = createField("record_step", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>collect.ofc_data_report.dataset_size</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord, java.lang.Integer> DATASET_SIZE = createField("dataset_size", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>collect.ofc_data_report.last_record_modified_date</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord, java.sql.Timestamp> LAST_RECORD_MODIFIED_DATE = createField("last_record_modified_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * The column <code>collect.ofc_data_report.creation_date</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord, java.sql.Timestamp> CREATION_DATE = createField("creation_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * Create a <code>collect.ofc_data_report</code> table reference
	 */
	public OfcDataReport() {
		this("ofc_data_report", null);
	}

	/**
	 * Create an aliased <code>collect.ofc_data_report</code> table reference
	 */
	public OfcDataReport(java.lang.String alias) {
		this(alias, org.openforis.collect.persistence.jooq.tables.OfcDataReport.OFC_DATA_REPORT);
	}

	private OfcDataReport(java.lang.String alias, org.jooq.Table<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord> aliased) {
		this(alias, aliased, null);
	}

	private OfcDataReport(java.lang.String alias, org.jooq.Table<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, org.openforis.collect.persistence.jooq.Collect.COLLECT, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord> getPrimaryKey() {
		return org.openforis.collect.persistence.jooq.Keys.OFC_DATA_REPORT_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord>>asList(org.openforis.collect.persistence.jooq.Keys.OFC_DATA_REPORT_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<org.openforis.collect.persistence.jooq.tables.records.OfcDataReportRecord, ?>>asList(org.openforis.collect.persistence.jooq.Keys.OFC_DATA_REPORT__OFC_DATA_REPORT_QUERY_GROUP_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.openforis.collect.persistence.jooq.tables.OfcDataReport as(java.lang.String alias) {
		return new org.openforis.collect.persistence.jooq.tables.OfcDataReport(alias, this);
	}

	/**
	 * Rename this table
	 */
	public org.openforis.collect.persistence.jooq.tables.OfcDataReport rename(java.lang.String name) {
		return new org.openforis.collect.persistence.jooq.tables.OfcDataReport(name, null);
	}
}