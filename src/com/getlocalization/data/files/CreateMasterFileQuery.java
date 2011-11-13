package com.getlocalization.data.files;

import com.getlocalization.client.Query;
import com.getlocalization.client.QuerySecurityException;

import java.io.*;

public class CreateMasterFileQuery extends Query {

	/**
	 * Creates new master file to given Get Localization project. 
	 * 
	 * @param file File that is sent to Get Localization
	 * @param projectId The project name that appears in your Get Localization URL.
	 * @param platformId One of the platforms from supported platform list. http://www.getlocalization.com/library/api/get-localization-file-management-api/
	 * @param languageId Standard IANA language code.
	 * 
	 */
	public CreateMasterFileQuery(File file, String projectId, String platformId, String languageId)
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
			String url = "https://www.getlocalization.com/" + projectId + 
					"/api/create-master/" + platformId + "/" + languageId;
			
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
