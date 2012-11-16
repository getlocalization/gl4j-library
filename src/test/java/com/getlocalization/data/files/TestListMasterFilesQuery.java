package com.getlocalization.data.files;

import static org.junit.Assert.*;

import java.util.Enumeration;

import org.junit.Test;

public class TestListMasterFilesQuery {

	@Test
	public void test() throws Exception {
		// TODO this test depends on the master file already being uploaded, not good
	  
		ListMasterFilesQuery query = new ListMasterFilesQuery("javatestsuite");
		query.setBasicAuth("javatestuser", "asdf1234");
		
		query.doQuery();
		
		Enumeration<?> e = query.getMasterFiles();
		
		while(e.hasMoreElements())
		{
			String name = (String)e.nextElement();
			
			if(name.equals("master-file.properties")) {
				return;
			}
		}
		
		fail("Cannot find master-file.properties from project");
	}

}
