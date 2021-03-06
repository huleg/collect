/**
 * 
 */
package org.openforis.collect.designer.form;

import org.apache.commons.lang3.StringUtils;
import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.metamodel.ui.UIOptions.CoordinateAttributeFieldsOrder;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.idm.metamodel.CoordinateAttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;

/**
 * @author S. Ricci
 *
 */
public class CoordinateAttributeDefinitionFormObject<T extends CoordinateAttributeDefinition> extends AttributeDefinitionFormObject<T> {
	
	private String fieldsOrderValue;
	private String xFieldLabel;
	private String yFieldLabel;
	private String srsFieldLabel;
	private boolean allowOnlyDeviceCoordinate;
	
	CoordinateAttributeDefinitionFormObject(EntityDefinition parentDefn) {
		super(parentDefn);
	}

	@Override
	public void saveTo(T dest, String languageCode) {
		super.saveTo(dest, languageCode);
		saveFieldOrderValue(dest);
		dest.setFieldLabel(CoordinateAttributeDefinition.X_FIELD_NAME, languageCode, xFieldLabel);
		dest.setFieldLabel(CoordinateAttributeDefinition.Y_FIELD_NAME, languageCode, yFieldLabel);
		dest.setFieldLabel(CoordinateAttributeDefinition.SRS_FIELD_NAME, languageCode, srsFieldLabel);

		((CollectSurvey) dest.getSurvey()).getAnnotations().setAllowOnlyDeviceCoordinate(dest, allowOnlyDeviceCoordinate);
	}

	protected void saveFieldOrderValue(T dest) {
		CollectSurvey survey = (CollectSurvey) dest.getSurvey();
		UIOptions uiOptions = survey.getUIOptions();
		CoordinateAttributeFieldsOrder fieldsOrder;
		if ( StringUtils.isBlank(fieldsOrderValue) ) {
			fieldsOrder = null;
		} else {
			fieldsOrder = CoordinateAttributeFieldsOrder.valueOf(fieldsOrderValue);
		}
		uiOptions.setFieldsOrder(dest, fieldsOrder);
	}
	
	@Override
	public void loadFrom(T source, String languageCode) {
		super.loadFrom(source, languageCode);
		loadFieldsOrderValue(source);
		xFieldLabel = source.getFieldLabel(CoordinateAttributeDefinition.X_FIELD_NAME, languageCode);
		yFieldLabel = source.getFieldLabel(CoordinateAttributeDefinition.Y_FIELD_NAME, languageCode);
		srsFieldLabel = source.getFieldLabel(CoordinateAttributeDefinition.SRS_FIELD_NAME, languageCode);
		
		allowOnlyDeviceCoordinate = ((CollectSurvey) source.getSurvey()).getAnnotations().isAllowOnlyDeviceCoordinate(source);
	}

	protected void loadFieldsOrderValue(T source) {
		CollectSurvey survey = (CollectSurvey) source.getSurvey();
		UIOptions uiOptions = survey.getUIOptions();
		CoordinateAttributeFieldsOrder fieldsOrder = uiOptions.getFieldsOrder(source);
		fieldsOrderValue = fieldsOrder.name();
	}

	public String getFieldsOrderValue() {
		return fieldsOrderValue;
	}

	public void setFieldsOrderValue(String fieldsOrderValue) {
		this.fieldsOrderValue = fieldsOrderValue;
	};
	
	public String getxFieldLabel() {
		return xFieldLabel;
	}

	public void setxFieldLabel(String xFieldLabel) {
		this.xFieldLabel = xFieldLabel;
	}

	public String getyFieldLabel() {
		return yFieldLabel;
	}

	public void setyFieldLabel(String yFieldLabel) {
		this.yFieldLabel = yFieldLabel;
	}

	public String getSrsFieldLabel() {
		return srsFieldLabel;
	}

	public void setSrsFieldLabel(String srsFieldLabel) {
		this.srsFieldLabel = srsFieldLabel;
	}

	public boolean isAllowOnlyDeviceCoordinate() {
		return allowOnlyDeviceCoordinate;
	}
	
	public void setAllowOnlyDeviceCoordinate(boolean allowOnlyDeviceCoordinate) {
		this.allowOnlyDeviceCoordinate = allowOnlyDeviceCoordinate;
	}
}
