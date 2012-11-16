package com.getlocalization.api.files;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getlocalization.api.*;
import com.getlocalization.api.files.*;
import com.getlocalization.client.QueryException;
import com.getlocalization.data.files.TranslationsQuery;

/**
 * This class is used to download translations from the Get Localization service.
 *
 */
public class GLTranslations {
  
  private static Logger log = LoggerFactory.getLogger(GLTranslations.class);
	
	/**
	 * Creates a new <i>GLTranslations</i> instance.
	 * 
	 * @param project Project you're downloading translations from.
	 */
	public GLTranslations(GLProject project)
	{
		this.myProject = project;
	}
	
	/**
	 * Pull the translations from server to target directory. Note that request
	 * may take some time as translated files are generated on the server-side and
	 * depending of the load it's possible but unlikely that call immediately throws 
	 * GLServerBusyException which means you should try again in a moment.
	 * 
	 * @param targetDirectory
	 * @throws GLException, GLServerBusyException
	 */
	public void pull(String targetDirectory) throws GLException, GLServerBusyException
	{
		TranslationsQuery query = new TranslationsQuery(myProject.getProjectName());
		query.setBasicAuth(myProject.getUsername(), myProject.getPassword());
		
		try
		{
			query.doQuery();
			
			File zipFile = query.getTranslationsZipFile();
			
			unzip(zipFile, new File(targetDirectory).getAbsolutePath());
		}
		catch(IOException io)
		{
			log.warn(null, io);
			throw new GLException("Unable to save translations: " + io.getMessage());
		}
		catch(Exception e)
		{
			log.warn(null,e);
			throw new GLException("Unable to download translations: " + e.getMessage());
		}
	}
	
	/**
	 * Pull the translations from server to target directory but don't unzip them. Note that request
	 * may take some time as translated files are generated on the server-side and
	 * depending of the load it's possible but unlikely that call immediately throws 
	 * GLServerBusyException which means you should try again in a moment.
	 * 
	 * @param targetDirectory
	 * @throws GLException, GLServerBusyException
	 */
	public File pull() throws GLException, GLServerBusyException
	{
		TranslationsQuery query = new TranslationsQuery(myProject.getProjectName());
		query.setBasicAuth(myProject.getUsername(), myProject.getPassword());
		
		try
		{
			query.doQuery();
			
			File zipFile = query.getTranslationsZipFile();
			
			return zipFile;
		}
		catch(QueryException e) {
			if(e.getStatusCode() == 401)
				throw new GLException("Authentication error, please check your username and password" + e.getMessage());
			else
				throw new GLException("Error when processing the query: " + e.getMessage());
		}
		catch(IOException io)
		{
		  log.warn(null, io);
			throw new GLException("Unable to save translations: " + io.getMessage());
		}
		catch(Exception e)
		{
		  log.warn(null, e);
			throw new GLException("Unable to download translations: " + e.getMessage());
		}
	}
	
	private void unzip(File zip, String target) throws IOException
	{
		ZipFile zipFile = new ZipFile(zip);
		Enumeration entries = zipFile.entries();
		
		while(entries.hasMoreElements())
		{
			ZipEntry entry = (ZipEntry)entries.nextElement();
			
			if(entry.isDirectory()) {
				(new File(target + "/" + entry.getName())).mkdir();
				continue;
			}
			
			File output = new File(target + "/" + entry.getName());
			
			new File(output.getParent()).mkdirs();
			
			copyInputStream(zipFile.getInputStream(entry),
			           new BufferedOutputStream(new FileOutputStream(output)));
		}
	}
	
	private static final void copyInputStream(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int len;

		while((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
	}
	
	private GLProject myProject;
}
