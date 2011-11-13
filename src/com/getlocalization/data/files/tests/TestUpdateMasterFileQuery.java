package com.getlocalization.data.files.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.getlocalization.data.files.UpdateMasterFileQuery;

import java.io.File;

public class TestUpdateMasterFileQuery {

	@Test
	public void test() {
		
		File file = new File("testdata/master-file.properties");
		
		System.out.println("Loading test file:" + file.getAbsolutePath());
		
		UpdateMasterFileQuery query = new UpdateMasterFileQuery(file, "javatestsuite");
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
