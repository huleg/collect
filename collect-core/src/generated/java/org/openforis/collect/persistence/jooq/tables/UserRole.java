/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.1"},
                            comments = "This class is generated by jOOQ")
public class UserRole extends org.jooq.impl.UpdatableTableImpl<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord> {

	private static final long serialVersionUID = 1705710558;

	/**
	 * The singleton instance of user_role
	 */
	public static final org.openforis.collect.persistence.jooq.tables.UserRole USER_ROLE = new org.openforis.collect.persistence.jooq.tables.UserRole();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord> __RECORD_TYPE = org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.user_role.user_id]
	 * REFERENCES user_account [collect.user_account.id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord, java.lang.Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord, java.lang.String> ROLE = createField("role", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * No further instances allowed
	 */
	private UserRole() {
		super("user_role", org.openforis.collect.persistence.jooq.Collect.COLLECT);
	}

	/**
	 * No further instances allowed
	 */
	private UserRole(java.lang.String alias) {
		super(alias, org.openforis.collect.persistence.jooq.Collect.COLLECT, org.openforis.collect.persistence.jooq.tables.UserRole.USER_ROLE);
	}

	@Override
	public org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord> getMainKey() {
		return org.openforis.collect.persistence.jooq.Keys.user_role_pkey;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord>>asList(org.openforis.collect.persistence.jooq.Keys.user_role_pkey);
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.ForeignKey<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord, ?>>asList(org.openforis.collect.persistence.jooq.Keys.user_role__FK_user_user_role);
	}

	@Override
	public org.openforis.collect.persistence.jooq.tables.UserRole as(java.lang.String alias) {
		return new org.openforis.collect.persistence.jooq.tables.UserRole(alias);
	}
}
