package com.getlocalization.data.files;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

import org.junit.Test;

public class TestListMasterFilesQuery {

	@Test
	public void test() throws Exception {
		// TODO this test depends on the master file already being uploaded, not good
	  
		ListMasterFilesQuery query = new ListMasterFilesQuery("javatestsuite");
		query.setBasicAuth("javatestuser", "asdf1234");
		
		query.doQuery();
		
		assertThat(query.getMasterFiles(), hasItem("master-file.properties"));
	}

}
