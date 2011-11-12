package com.getlocalization.api.files.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import com.getlocalization.api.files.CreateMasterFileQuery;
import java.io.File;

public class TestCreateMasterFileQuery {

	@Test
	public void test() {
		
		File file = new File("testdata/master-file.properties");
		
		System.out.println("Loading test file:" + file.getAbsolutePath());
		
		CreateMasterFileQuery query = new CreateMasterFileQuery(file, "javatestsuite", "javaproperties", "en");
		query.setBasicAuth("javatestuser", "asdf1234");
		
		try
		{
			query.doQuery();
		}
		catch(Exception e)
		{
			fail("Exception" + e.getMessage());
		}
	}

}
