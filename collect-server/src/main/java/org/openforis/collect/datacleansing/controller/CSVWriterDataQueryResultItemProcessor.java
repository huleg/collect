package org.openforis.collect.datacleansing.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.util.IOUtils;
import org.openforis.collect.datacleansing.DataQuery;
import org.openforis.collect.datacleansing.DataQueryResultItem;
import org.openforis.collect.datacleansing.controller.DataQueryController.AttributeQueryResultItemProcessor;
import org.openforis.commons.io.csv.CsvWriter;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeLabel.Type;
import org.openforis.idm.model.Value;

/**
 * 
 * @author S. Ricci
 *
 */
public class CSVWriterDataQueryResultItemProcessor extends AttributeQueryResultItemProcessor {
	
	private CsvWriter csvWriter;
	
	//output
	private File tempFile;
	
	public CSVWriterDataQueryResultItemProcessor(DataQuery query) {
		super(query);
	}
	
	@Override
	public void init() throws Exception {
		tempFile = File.createTempFile("collect-data-cleansing-query", ".csv");
		csvWriter = new CsvWriter(new FileOutputStream(tempFile), IOUtils.UTF_8, ',', '"');
		writeCSVHeader();
	}
	
	private void writeCSVHeader() {
		List<String> headers = new ArrayList<String>();
		EntityDefinition rootEntity = query.getEntityDefinition().getRootEntity();
		List<AttributeDefinition> keyAttributeDefinitions = rootEntity.getKeyAttributeDefinitions();
		for (AttributeDefinition def : keyAttributeDefinitions) {
			String keyLabel = def.getLabel(Type.INSTANCE);
			if (StringUtils.isBlank(keyLabel)) {
				keyLabel = def.getName();
			}
			headers.add(keyLabel);
		}
		headers.add("Path");
		AttributeDefinition attrDef = (AttributeDefinition) query.getSchema().getDefinitionById(query.getAttributeDefinitionId());
		String attrName = attrDef.getName();
		List<String> fieldNames = attrDef.getFieldNames();
		if (fieldNames.size() > 1) {
			for (String fieldName : fieldNames) {
				headers.add(attrName + "_" + fieldName);
			}
		} else {
			headers.add(attrName);
		}
		csvWriter.writeHeaders(headers.toArray(new String[headers.size()]));
	}

	@Override
	public void process(DataQueryResultItem item) {
		List<String> lineValues = new ArrayList<String>();
		lineValues.addAll(item.getRecordKeyValues());
		lineValues.add(item.extractNodePath());
		AttributeDefinition attrDef = item.getAttributeDefinition();
		Value value = item.extractAttributeValue();
		Map<String, Object> valueMap = value == null ? null : value.toMap();
		List<String> fieldNames = attrDef.getFieldNames();
		for (String fieldName : fieldNames) {
			Object fieldValue = valueMap == null ? null : valueMap.get(fieldName);
			lineValues.add(fieldValue == null ? "": fieldValue.toString());
		}
		csvWriter.writeNext(lineValues.toArray(new String[lineValues.size()]));
	}
	
	@Override
	public void close() throws IOException {
		csvWriter.close();
	}

	public File getOutputFile() {
		return tempFile;
	}
}