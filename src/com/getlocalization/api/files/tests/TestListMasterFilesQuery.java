package com.getlocalization.api.files.tests;

import static org.junit.Assert.*;

import java.util.Enumeration;

import org.junit.Test;

import com.getlocalization.api.files.ListMasterFilesQuery;

public class TestListMasterFilesQuery {

	@Test
	public void test() {
		
		ListMasterFilesQuery query = new ListMasterFilesQuery("javatestsuite");
		query.setBasicAuth("javatestuser", "asdf1234");
		
		try
		{
			query.doQuery();
			
			Enumeration e = query.getMasterFiles();
			
			boolean success = false;
			while(e.hasMoreElements())
			{
				String name = (String)e.nextElement();
				
				if(name.equals("master-file.properties"))
					success = true;
			}
			
			if(!success)
				fail("Cannot find master-file.properties from project");
		}
		catch(Exception e)
		{
			fail("Exception" + e.getMessage());
		}
	}

}
