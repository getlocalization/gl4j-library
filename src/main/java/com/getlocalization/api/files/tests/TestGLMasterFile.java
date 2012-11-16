package com.getlocalization.api.files.tests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.io.File;
import org.junit.Test;

import com.getlocalization.api.GLException;
import com.getlocalization.api.GLProject;
import com.getlocalization.api.files.GLMasterFile;
import com.getlocalization.data.files.ListMasterFilesQuery;

public class TestGLMasterFile {

	@Test
	public void test() {
		//ListMasterFilesQuery query = new ListMasterFilesQuery("javatestsuite");
		//query.setBasicAuth("javatestuser", "asdf1234");
		
		String testFileName = "";
		
		try
		{
			File t1 = new File("testdata/master-file.properties");
			File t2 = File.createTempFile("testfile", ".properties");
			
			copyFile(t1, t2);
			
			testFileName = t2.getAbsolutePath();
		}
		catch(IOException ioe)
		{
			fail("Can't create test file: " + ioe.getMessage());
		}
		
		 
		try
		{
			GLProject project = new GLProject("javatestsuite", "javatestuser", "asdf1234");
			GLMasterFile masterFile = new GLMasterFile(project, testFileName, "javaproperties");
			
			if(masterFile.isAvailableRemotely())
				fail("File " + testFileName + " is already available!");
			
			masterFile.push();
			
			if(!masterFile.isAvailableRemotely())
				fail("File " + testFileName + " is not available!");
			
			GLMasterFile masterFile2 = new GLMasterFile(project, testFileName, "javaproperties");
			masterFile2.push();
		}
		catch(GLException ex)
		{
			fail("Not yet implemented");
		}
		
	}
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}

}
