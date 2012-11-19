package com.getlocalization.api.files;

import java.io.*;
import java.util.Enumeration;

import com.getlocalization.api.GLException;
import com.getlocalization.api.GLProject;
import com.getlocalization.client.QueryException;
import com.getlocalization.data.files.CreateMasterFileQuery;
import com.getlocalization.data.files.ListMasterFilesQuery;
import com.getlocalization.data.files.UpdateMasterFileQuery;

/**
 * Presentation of a local master file that is sync'ed with Get Localization.  
 */
public class GLMasterFile extends File {

  private static final long serialVersionUID = 1L;
  
  /**
	 * Creates a new <i>GLMasterFile</i> instance by converting the given pathname string into an abstract pathname.
	 * 
	 * @param pathname Path to master file
	 */
	public GLMasterFile(GLProject project, String pathname, FileFormat fileFormat)
	{
		super(pathname);
		
		this.myProject = project;
		this.fileFormat = fileFormat;
	}
	
	/**
	 * Returns whether file already exists on server. This will make
	 * a call to server if data is not available.
	 * 
	 * @return true if file already exists on server.
	 * @throws GLException
	 */
	public boolean isAvailableRemotely() throws GLException
	{
		if(createdToServer)
			return true;
		
		ListMasterFilesQuery query = new ListMasterFilesQuery(myProject.getProjectName());
		query.setBasicAuth(myProject.getUsername(), myProject.getPassword());
		
		try
		{
			query.doQuery();
			
			for (String masterFile : query.getMasterFiles()) {
        if (masterFile.equals(getName())) {
          return true;
        }
      }
			return false;
		}
		catch(QueryException e) {
			if(e.getStatusCode() == 401) {
				throw new GLException("Authentication error, please check your username and password" + e.getMessage());
			} else {
				throw new GLException("Error when processing the query: " + e.getMessage());
			}
		}
		catch(Exception e)
		{
			throw new GLException("Unable to get information whether master file is available or not: " + e.getMessage());
		}
	}
	
	/**
	 * Pushes (adds or updates) master file to Get Localization. 
	 * @throws GLException
	 */
	public void push() throws GLException
	{
		if(isAvailableRemotely()) {
			update();
		} else {
			add();
		}
	}
	
	private void update() throws GLException
	{
		UpdateMasterFileQuery query = new UpdateMasterFileQuery(this, myProject.getProjectName());
		query.setBasicAuth(myProject.getUsername(), myProject.getPassword());
		
		try
		{
			query.doQuery();
		}
		catch(Exception e)
		{
			throw new GLException("Unable to update master file to Get Localization: " + e.getMessage());
		}
	}

	private void add() throws GLException
	{
		CreateMasterFileQuery query = new CreateMasterFileQuery(this, myProject.getProjectName(), fileFormat, myProject.getLanguageId());
		
		query.setBasicAuth(myProject.getUsername(), myProject.getPassword());
		
		try
		{
			query.doQuery();
		}
		catch(Exception e)
		{
			throw new GLException("Unable to create master file to Get Localization: " + e.getMessage());
		}
	}

	private boolean createdToServer = false;
	private GLProject myProject;
	private FileFormat fileFormat;

}
