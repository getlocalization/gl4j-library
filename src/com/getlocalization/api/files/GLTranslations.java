package com.getlocalization.api.files;

import static org.junit.Assert.fail;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.getlocalization.api.*;
import com.getlocalization.api.files.*;
import com.getlocalization.data.files.TranslationsQuery;

/**
 * This class is used to download translations from the Get Localization service.
 *
 */
public class GLTranslations {
	
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
			throw new GLException("Unable to save translations: " + io.getMessage());
		}
		catch(Exception e)
		{
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
