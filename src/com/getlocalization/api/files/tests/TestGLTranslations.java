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
import com.getlocalization.api.files.GLTranslations;
import com.getlocalization.data.files.ListMasterFilesQuery;

public class TestGLTranslations {

	@Test
	public void test() {
		//ListMasterFilesQuery query = new ListMasterFilesQuery("javatestsuite");
		//query.setBasicAuth("javatestuser", "asdf1234");
		
		try
		{
			GLProject project = new GLProject("javatestsuite", "javatestuser", "asdf1234");
			GLTranslations translations = new GLTranslations(project);
			
			try {new File("testdata/translations/").mkdirs();}catch(Exception e){}
			
			translations.pull("testdata/translations/");
		}
		catch(Exception e)
		{
			fail("Unable to process translations:" + e.getMessage());
		}
	}
}
