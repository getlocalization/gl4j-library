package com.getlocalization.api.files;

import com.getlocalization.data.client.Query;
import com.getlocalization.data.client.QuerySecurityException;

import java.io.*;

public class UpdateMasterFileQuery extends Query {

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
	public void doQuery() throws IOException {
		try
		{
			String url = "https://www.getlocalization.com/" + projectId + "/api/update-master";
			
			postFile(this.file, url);
		}
		catch(QuerySecurityException cse)
		{
			// Making sure that URL starts with https.
			cse.printStackTrace();
		}
	}

	private File file;
	private String projectId, 
				platformId, 
				languageId;
	

}
