package com.getlocalization.data.files;

import java.io.File;

import org.junit.Test;

import com.getlocalization.api.files.FileFormat;

public class TestCreateMasterFileQuery {
  
  // TODO This test fails if the master file already exists

	@Test
	public void test() throws Exception {
		
		File file = new File("src/test/resources/master-file.properties");
		
		CreateMasterFileQuery query = new CreateMasterFileQuery(file, "javatestsuite", FileFormat.javaproperties, "en");
		query.setBasicAuth("javatestuser", "asdf1234");
		
		query.doQuery();
	}

}
