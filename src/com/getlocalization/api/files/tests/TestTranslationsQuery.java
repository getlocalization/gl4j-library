package com.getlocalization.api.files.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import com.getlocalization.api.files.TranslationsQuery;
import java.io.File;

public class TestTranslationsQuery {

	@Test
	public void test() {
		
		TranslationsQuery query = new TranslationsQuery("javatestsuite");
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
