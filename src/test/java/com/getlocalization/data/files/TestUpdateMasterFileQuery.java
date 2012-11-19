package com.getlocalization.data.files;

import java.io.File;

import org.junit.Test;

public class TestUpdateMasterFileQuery {
	
  @Test
	public void test() throws Exception {
		
		File file = new File("src/test/resources/master-file.properties");
		
		UpdateMasterFileQuery query = new UpdateMasterFileQuery(file, "javatestsuite");
		query.setBasicAuth("javatestuser", "asdf1234");
		
		query.doQuery();
	}

}
