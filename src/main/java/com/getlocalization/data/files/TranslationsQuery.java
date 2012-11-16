package com.getlocalization.data.files;

import com.getlocalization.client.Query;
import com.getlocalization.client.QueryException;
import com.getlocalization.client.QuerySecurityException;

import java.io.*;
import java.util.zip.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TranslationsQuery extends Query {

  private static Logger log = LoggerFactory.getLogger(TranslationsQuery.class);
	/**
	 * Update an existing master file to given Get Localization project. 
	 * 
	 * @param file File that is sent to Get Localization
	 * @param projectId The project name that appears in your Get Localization URL.
	 * 
	 */
	public TranslationsQuery(String projectId)
	{
		this.projectId = projectId;
	}
	
	@Override
	public void doQuery() throws QueryException, IOException {
		try
		{
			String url = "https://www.getlocalization.com/" + projectId + "/api/translations/zip";
			
			byte[] zip = getFile(url);
			
			zipFile = File.createTempFile("getlocalization", ".zip");
			FileOutputStream fos = new FileOutputStream(zipFile);
			fos.write(zip);
			fos.close();
		}
		catch(QuerySecurityException cse)
		{
			// Making sure that URL starts with https.
		  log.warn(null, cse);
		}
	}
	
	public File getTranslationsZipFile()
	{
		return zipFile;
	}

	private String projectId; 
	private File zipFile;
	

}
