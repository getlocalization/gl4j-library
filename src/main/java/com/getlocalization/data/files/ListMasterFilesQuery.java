package com.getlocalization.data.files;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getlocalization.client.Query;
import com.getlocalization.client.QueryException;
import com.getlocalization.client.QuerySecurityException;

public class ListMasterFilesQuery extends Query {
  
  private static Logger log = LoggerFactory.getLogger(ListMasterFilesQuery.class); 

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
				
				int nboFiles = fileArray.length();
				if (nboFiles > 0) {
  				List<String> fileList = new ArrayList<String>(fileArray.length());
  				for(int i=0;i<fileArray.length();i++)
  				{
  					fileList.add((String)fileArray.get(i));
  				}
  				masterFiles = Collections.unmodifiableList(fileList);
				} else {
				  masterFiles = Collections.emptyList();
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
			log.warn(null, cse);
		}
	}
	
	public List<String> getMasterFiles()
	{
		return masterFiles;
	}

	private String projectId; 
	private List<String> masterFiles = Collections.emptyList();
}
