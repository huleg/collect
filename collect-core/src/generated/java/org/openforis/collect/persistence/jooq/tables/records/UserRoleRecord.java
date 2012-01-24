/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.1"},
                            comments = "This class is generated by jOOQ")
public class UserRoleRecord extends org.jooq.impl.UpdatableRecordImpl<org.openforis.collect.persistence.jooq.tables.records.UserRoleRecord> {

	private static final long serialVersionUID = -355942381;

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public void setId(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.UserRole.USER_ROLE.ID, value);
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public java.lang.Integer getId() {
		return getValue(org.openforis.collect.persistence.jooq.tables.UserRole.USER_ROLE.ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.user_role.user_id]
	 * REFERENCES user_account [collect.user_account.id]
	 * </pre></code>
	 */
	public void setUserId(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.UserRole.USER_ROLE.USER_ID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.user_role.user_id]
	 * REFERENCES user_account [collect.user_account.id]
	 * </pre></code>
	 */
	public java.lang.Integer getUserId() {
		return getValue(org.openforis.collect.persistence.jooq.tables.UserRole.USER_ROLE.USER_ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.user_role.user_id]
	 * REFERENCES user_account [collect.user_account.id]
	 * </pre></code>
	 */
	public org.openforis.collect.persistence.jooq.tables.records.UserAccountRecord fetchUserAccount() {
		return create()
			.selectFrom(org.openforis.collect.persistence.jooq.tables.UserAccount.USER_ACCOUNT)
			.where(org.openforis.collect.persistence.jooq.tables.UserAccount.USER_ACCOUNT.ID.equal(getValue(org.openforis.collect.persistence.jooq.tables.UserRole.USER_ROLE.USER_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 */
	public void setRole(java.lang.String value) {
		setValue(org.openforis.collect.persistence.jooq.tables.UserRole.USER_ROLE.ROLE, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getRole() {
		return getValue(org.openforis.collect.persistence.jooq.tables.UserRole.USER_ROLE.ROLE);
	}

	/**
	 * Create a detached UserRoleRecord
	 */
	public UserRoleRecord() {
		super(org.openforis.collect.persistence.jooq.tables.UserRole.USER_ROLE);
	}
}
