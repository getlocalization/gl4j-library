package com.getlocalization.data.files;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestTranslationsQuery {

	@Test
	public void test() throws Exception {
		
		TranslationsQuery query = new TranslationsQuery("javatestsuite");
		query.setBasicAuth("javatestuser", "asdf1234");
		
		query.doQuery();
	}

}
