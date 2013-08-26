/**
 * 
 */
package org.openforis.collect.manager.dataimport;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.collect.manager.referencedataimport.CSVDataImportReader;
import org.openforis.collect.manager.referencedataimport.CSVLineParser;
import org.openforis.collect.manager.referencedataimport.ParsingError;
import org.openforis.collect.manager.referencedataimport.ParsingError.ErrorType;
import org.openforis.collect.manager.referencedataimport.ParsingException;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.BooleanAttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.DateAttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.FieldDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.TimeAttributeDefinition;

/**
 * @author S. Ricci
 *
 */
public class DataCSVReader extends CSVDataImportReader<DataLine> {

	private EntityDefinition parentEntityDefinition;

	public DataCSVReader(Reader reader, EntityDefinition parentEntityDefinition) throws IOException, ParsingException {
		super(reader);
		this.parentEntityDefinition = parentEntityDefinition;
		validateAllFile();
	}
	
	@Override
	protected void init() throws IOException, ParsingException {
		csvReader.readHeaders();
		//postpone file validation
	}

	@Override
	protected DataCSVLineParser createLineParserInstance() {
		DataCSVLineParser lineParser = new DataCSVLineParser(this);
		return lineParser;
	}

	@Override
	public boolean validateAllFile() throws ParsingException {
		Validator validator = new Validator();
		validator.validate();
		return true;
	}
	
	protected String getKeyAttributeColumnName(AttributeDefinition keyAttrDefn) {
		StringBuilder sb = new StringBuilder();
		NodeDefinition parentDefn = keyAttrDefn.getParentDefinition();
		sb.append(parentDefn.getName());
		sb.append("_");
		sb.append(keyAttrDefn.getName());
		return sb.toString();
	}
	
	protected List<AttributeDefinition> getAncestorKeyAttributeDefinitions() {
		List<AttributeDefinition> result = new ArrayList<AttributeDefinition>();
		List<EntityDefinition> ancestors = parentEntityDefinition.getAncestorEntityDefinitions();
		for (EntityDefinition ancestor : ancestors) {
			result.addAll(ancestor.getKeyAttributeDefinitions());
		}
		result.addAll(parentEntityDefinition.getKeyAttributeDefinitions());
		return result;
	}
	
	private String getMainFieldName(AttributeDefinition attrDefn) {
		if ( attrDefn instanceof BooleanAttributeDefinition ) {
			return BooleanAttributeDefinition.VALUE_FIELD;
		} else if ( attrDefn instanceof CodeAttributeDefinition ) {
			return CodeAttributeDefinition.CODE_FIELD;
		} else if ( attrDefn instanceof DateAttributeDefinition ) {
			throw new IllegalArgumentException("No main field defined");
		} else if ( attrDefn instanceof TimeAttributeDefinition ) {
			return CodeAttributeDefinition.CODE_FIELD;
		}
		return null;
	}

	class DataCSVLineParser extends CSVLineParser<DataLine> {
		
		DataCSVLineParser(DataCSVReader reader) {
			super(reader, currentCSVLine);
		}
		
		public DataLine parse() throws ParsingException {
			DataLine line = super.parse();
			List<AttributeDefinition> ancestorKeyAttrDefns = getAncestorKeyAttributeDefinitions();
			for (AttributeDefinition keyDefn : ancestorKeyAttrDefns) {
				String keyAttrColName = getKeyAttributeColumnName(keyDefn);
				String value = getColumnValue(keyAttrColName, true, String.class);
				line.addAncestorKey(keyDefn, value);
			}
			List<String> colNames = csvLine.getColumnNames();
			List<String> attrColNames = colNames.subList(ancestorKeyAttrDefns.size(), colNames.size());
			for (String colName : attrColNames) {
				String value = getColumnValue(colName, true, String.class);
				String[] colNameSplitted = colName.split("\\.");
				String attrName = colNameSplitted[0];
				FieldDefinition<?> fieldDefn;
				try {
					AttributeDefinition attrDefn = (AttributeDefinition) parentEntityDefinition.getChildDefinition(attrName);
					String fieldName;
					switch ( colNameSplitted.length ) {
					case 2:
						fieldName = colNameSplitted[1];
						break;
					case 1:
						fieldName = getMainFieldName(attrDefn);
						break;
					default:
						throw new ParsingException(new ParsingError(ErrorType.WRONG_COLUMN_NAME, getLineNumber(), colName));
					}
					fieldDefn = attrDefn.getFieldDefinition(fieldName);
					line.addFieldValue(fieldDefn, value);
				} catch ( Exception e ) {
					throw new ParsingException(new ParsingError(ErrorType.WRONG_COLUMN_NAME, getLineNumber(), colName));
				}
			}
			return line;
		}

	}
	
	class Validator {
		
		private static final String MISSING_REQUIRED_COLUMNS_MESSAGE_KEY = "codeListImport.parsingError.missing_required_columns.message";

		public void validate() throws ParsingException {
			validateHeaders();
		}

		protected void validateHeaders() throws ParsingException {
			List<String> colNames = getColumnNames();
			List<AttributeDefinition> ancestorKeyAttrDefns = new ArrayList<AttributeDefinition>();
			List<EntityDefinition> ancestorEntityDefns = parentEntityDefinition.getAncestorEntityDefinitions();
			for (EntityDefinition ancestorEntityDefn : ancestorEntityDefns) {
				ancestorKeyAttrDefns.addAll(ancestorEntityDefn.getKeyAttributeDefinitions());
			}
			ancestorKeyAttrDefns.addAll(parentEntityDefinition.getKeyAttributeDefinitions());
			for (int i = 0; i < colNames.size(); i++) {
				String colName = StringUtils.trimToEmpty(colNames.get(i));
				if ( i < ancestorKeyAttrDefns.size() ) {
					AttributeDefinition ancestorKeyAttrDefn = ancestorKeyAttrDefns.get(i);
					String expectedColName = getKeyAttributeColumnName(ancestorKeyAttrDefn);
					if ( ! colName.equals(expectedColName) ) {
						ParsingError error = new ParsingError(ErrorType.MISSING_REQUIRED_COLUMNS, 1, 
								(String) null, MISSING_REQUIRED_COLUMNS_MESSAGE_KEY);
						throw new ParsingException(error);
					}
				}
			}
		}

	}

}
