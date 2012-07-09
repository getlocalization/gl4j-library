package com.getlocalization.data.files;

import com.getlocalization.client.Query;
import com.getlocalization.client.QueryException;
import com.getlocalization.client.QuerySecurityException;


import java.io.*;
import java.util.zip.*;
import java.util.*;
import org.json.*;

public class ListMasterFilesQuery extends Query {

	/**
	 * Update an existing master file to given Get Localization project. 
	 * 
	 * @param file File that is sent to Get Localization
	 * @param projectId The project name that appears in your Get Localization URL.
	 * 
	 */
	public ListMasterFilesQuery(String projectId)
	{
		this.projectId = projectId;
		masterFiles = new Vector();
	}
	
	@Override
	public void doQuery() throws IOException, QueryException {
		try
		{
			String url = "https://www.getlocalization.com/" + projectId + "/api/list-master/json";
			byte[] filesJson = getFile(url);
			
			try
			{
				JSONObject files = new JSONObject(new String(filesJson));
				JSONArray fileArray = files.getJSONArray("master_files");
				
				for(int i=0;i<fileArray.length();i++)
				{
					masterFiles.add((String)fileArray.get(i));
				}
			}
			catch(JSONException jse)
			{
				throw new QueryException("Invalid response:" + new String(filesJson), 0);
			}
			
		}
		catch(QuerySecurityException cse)
		{
			// Making sure that URL starts with https.
			cse.printStackTrace();
		}
	}
	
	public Enumeration getMasterFiles()
	{
		return masterFiles.elements();
	}

	private String projectId; 
	private Vector masterFiles;
}
