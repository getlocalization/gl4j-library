package com.getlocalization.data.files;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getlocalization.client.Query;
import com.getlocalization.client.QueryException;
import com.getlocalization.client.QuerySecurityException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class ListMasterFilesQuery extends Query {
  
  private static Logger log = LoggerFactory.getLogger(ListMasterFilesQuery.class); 
  private static Gson gson = new GsonBuilder().disableInnerClassSerialization().create();

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
		
		  JsonElement files = gson.fromJson(new String(filesJson), JsonElement.class);
		  JsonArray fileArray = files.getAsJsonObject().getAsJsonArray("master_files");

		  if (fileArray.size() > 0) {
			  List<String> fileList = new ArrayList<String>(fileArray.size());
			  
			  for (JsonElement jsonElement : fileArray) {
			    fileList.add(jsonElement.getAsString());
        }
			  masterFiles = Collections.unmodifiableList(fileList);
		  } else {
			  masterFiles = Collections.emptyList();
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
