package org.openforis.collect.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * 
 * @author S. Ricci
 *
 */
public class BackupFileExtractor implements Closeable {

	public static final String RECORD_FILE_DIRECTORY_NAME = "upload";

	private ZipFile zipFile;

	public BackupFileExtractor(File file) throws ZipException, IOException {
		this(new ZipFile(file));
	}
	
	public BackupFileExtractor(ZipFile zipFile) {
		this.zipFile = zipFile;
	}
	
	public File extractInfoFile() {
		return extract(SurveyBackupJob.INFO_FILE_NAME);
	}
	
	public SurveyBackupInfo extractInfo() {
		try {
			File infoFile = extractInfoFile();
			SurveyBackupInfo info = SurveyBackupInfo.parse(new FileInputStream(infoFile));
			return info;
		} catch (Exception e) {
			throw new RuntimeException("Error extracting info file from archive", e);
		}
	}
	
	public File extractIdmlFile() {
		return extract(SurveyBackupJob.SURVEY_XML_ENTRY_NAME);
	}
	
	public File extract(String entryName) {
		return extract(entryName, true);
	}
	
	public File extract(String entryName, boolean required) {
		ZipEntry entry = findEntry(entryName);
		if ( entry == null ) {
			if ( required ) {
				throw new RuntimeException("Entry not found in packaged file: " + entryName);
			} else {
				return null;
			}
		} else {
			return extract(entry);
		}
	}

	private File extract(ZipEntry entry) {
		String entryName = entry.getName();
		try {
			InputStream is = zipFile.getInputStream(entry);
			String fileName = FilenameUtils.getName(entryName);
			File tempFile = File.createTempFile("collect", fileName);
			FileUtils.copyInputStreamToFile(is, tempFile);
			return tempFile;
		} catch (IOException e) {
			throw new RuntimeException(String.format("Error extracting file %s from backup archive: %s", entryName, e.getMessage()), e);
		}
	}
	
	public List<String> listEntriesInPath(String path) {
		if ( ! path.endsWith(SurveyBackupJob.ZIP_FOLDER_SEPARATOR) ) {
			path += SurveyBackupJob.ZIP_FOLDER_SEPARATOR;
		}
		List<String> result = new ArrayList<String>();
		Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
		while ( zipEntries.hasMoreElements() ) {
			ZipEntry zipEntry = zipEntries.nextElement();
			String name = zipEntry.getName();
			if ( ! zipEntry.isDirectory() && name.startsWith(path) ) {
				result.add(name);
			}
		}
		return result;
	}
	
	public Set<String> listDirectoriesInPath(String path) {
		if ( ! path.endsWith(SurveyBackupJob.ZIP_FOLDER_SEPARATOR) ) {
			path += SurveyBackupJob.ZIP_FOLDER_SEPARATOR;
		}
		Set<String> result = new LinkedHashSet<String>();
		Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
		while ( zipEntries.hasMoreElements() ) {
			ZipEntry zipEntry = zipEntries.nextElement();
			String name = zipEntry.getName();
			if (name.startsWith(path)) {
				String directoryName = extractFirstLevelDirectoryName(path, zipEntry);
				if (directoryName != null) {
					result.add(directoryName);
				}
			}
		}
		return result;
	}
	
	private String extractFirstLevelDirectoryName(String startFromPath, ZipEntry zipEntry) {
		String[] directorySeparators = new String[]{"\\", "/"};
		String nameEndPart = zipEntry.getName().substring(startFromPath.length());
		int indexOfFolderSeparator = -1;
		for (String directorySeparator : directorySeparators) {
			indexOfFolderSeparator = nameEndPart.indexOf(directorySeparator);
			if (indexOfFolderSeparator > 0) {
				return nameEndPart.substring(0, indexOfFolderSeparator);
			}
		}
		return null;
	}
	
	public int countEntriesInPath(String path) {
		List<String> entries = listEntriesInPath(path);
		return entries.size();
	}

	public List<String> listSpeciesEntryNames() {
		List<String> entries = listEntriesInPath(SurveyBackupJob.SPECIES_FOLDER);
		return entries;
	}
	
	public List<File> extractFilesInPath(String folder) throws IOException {
		List<File> result = new ArrayList<File>();
		List<String> entryNames = listEntriesInPath(folder);
		for (String name : entryNames) {
			File tempFile = extract(name);
			result.add(tempFile);
		}
		return result;
	}
	
	public ZipEntry findEntry(String entryName) {
		Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
		while ( zipEntries.hasMoreElements() ) {
			ZipEntry zipEntry = zipEntries.nextElement();
			String name = zipEntry.getName();
			if ( ! zipEntry.isDirectory() && name.equals(entryName)  ) {
				return zipEntry;
			}
		}
		return null;
	}
	
	public InputStream findEntryInputStream(String entryName) throws IOException {
		ZipEntry entry = findEntry(entryName);
		if ( entry == null ) {
			return null;
		} else {
			InputStream is = zipFile.getInputStream(entry);
			return is;
		}
	}

	public boolean containsEntry(String name) {
		ZipEntry entry = findEntry(name);
		return entry != null;
	}
	
	public boolean containsEntriesInPath(String path) {
		List<String> entryNames = listEntriesInPath(path);
		return ! entryNames.isEmpty();
	}
	
	public List<String> getEntryNames() {
		List<String> result = new ArrayList<String>();
		Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
		while ( zipEntries.hasMoreElements() ) {
			ZipEntry zipEntry = zipEntries.nextElement();
			String name = zipEntry.getName();
			result.add(name);
		}
		return result;
	}
	
	public boolean isIncludingRecordFiles() {
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) entries.nextElement();
			if ( zipEntry.getName().startsWith(RECORD_FILE_DIRECTORY_NAME)) {
				return true;
			}
		}
		return false;
	}

	public boolean isOldFormat() {
		return ! containsEntry(SurveyBackupJob.INFO_FILE_NAME);
	}
	
	public int size() {
		return zipFile.size();
	}

	@Override
	public void close() throws IOException {
		if (zipFile != null ) {
			zipFile.close();
		}
	}
	
}