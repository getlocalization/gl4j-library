package com.getlocalization.data.files;

import com.getlocalization.client.Query;
import com.getlocalization.client.QueryException;
import com.getlocalization.client.QuerySecurityException;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateMasterFileQuery extends Query {

  private static Logger log = LoggerFactory.getLogger(UpdateMasterFileQuery.class);
	/**
	 * Update an existing master file to given Get Localization project. 
	 * 
	 * @param file File that is sent to Get Localization
	 * @param projectId The project name that appears in your Get Localization URL.
	 * 
	 */
	public UpdateMasterFileQuery(File file, String projectId)
	{
		this.file = file;
		this.projectId = projectId;
		this.platformId = platformId;
		this.languageId = languageId;
	}
	
	@Override
	public void doQuery() throws QueryException, IOException {
		try
		{
			String url = "https://www.getlocalization.com/" + projectId + "/api/update-master";
			
			postFile(this.file, url);
		}
		catch(QuerySecurityException cse)
		{
			// Making sure that URL starts with https.
		  log.warn(null, cse);
		}
	}

	private File file;
	private String projectId, 
				platformId, 
				languageId;
	

}
