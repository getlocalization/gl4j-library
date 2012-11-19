package com.getlocalization.data.files;

import com.getlocalization.api.files.FileFormat;
import com.getlocalization.client.Query;
import com.getlocalization.client.QueryException;
import com.getlocalization.client.QuerySecurityException;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateMasterFileQuery extends Query {
  
  private static Logger log = LoggerFactory.getLogger(CreateMasterFileQuery.class);

	/**
	 * Creates new master file to given Get Localization project. 
	 * 
	 * @param file File that is sent to Get Localization
	 * @param projectId The project name that appears in your Get Localization URL.
	 * @param platformId One of the platforms from supported platform list. http://www.getlocalization.com/library/api/get-localization-file-management-api/
	 * @param languageId Standard IANA language code.
	 * 
	 */
	public CreateMasterFileQuery(File file, String projectId, FileFormat fileFormat, String languageId)
	{
		this.file = file;
		this.projectId = projectId;
		this.fileFormat = fileFormat;
		this.languageId = languageId;
	}
	
	@Override
	public void doQuery() throws QueryException, IOException {
		try
		{
			String url = "https://www.getlocalization.com/" + projectId + 
					"/api/create-master/" + fileFormat.name() + "/" + languageId;
			
			postFile(this.file, url);
		}
		catch(QuerySecurityException cse)
		{
			// Making sure that URL starts with https.
		  log.warn(null, cse);
		}
	}

	private File file;
	private String projectId; 
	private String languageId;
  private FileFormat fileFormat;
	

}
