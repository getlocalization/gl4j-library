package com.getlocalization.client;

import java.net.*;
import java.io.*;

import javax.net.ssl.*;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Query {
  
  private static Logger log = LoggerFactory.getLogger(Query.class);
	public Query()
	{
		this.forcedSSL = false;
	}
	
	public void setBasicAuth(String username, String password)
	{
		this.forcedSSL = true;
		this.username = username;
		this.password = password;
	}
	
	public int postFile(File file, String url) throws IOException, QuerySecurityException, QueryException
	{
		if(forcedSSL && !url.startsWith("https"))
			throw new QuerySecurityException("SSL is required with basic auth");
		
		int status = 0;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
  		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
  
  		 httpclient.getCredentialsProvider().setCredentials(
                   new AuthScope(AuthScope.ANY_HOST, 443), 
                   new UsernamePasswordCredentials(username, password));
  
  		HttpPost httppost = new HttpPost(url);
  		
  		MultipartEntity mpEntity = new MultipartEntity();
  		
  		log.info("Posting file:" + file.getAbsolutePath());
  		
  		ContentBody cbFile = new FileBody(file, file.getName(), "text/plain", "utf8");
  		mpEntity.addPart("file", cbFile);
  
  		httppost.setEntity(mpEntity);
  
  		HttpResponse response = httpclient.execute(httppost);
  		HttpEntity resEntity = response.getEntity();
  
  		String resString = null;
  		if (resEntity != null && log.isInfoEnabled()) {
  		  resString = EntityUtils.toString(resEntity); 
  			log.info(resString);
  		}
  		
  		status = response.getStatusLine().getStatusCode();
  		
  		if(status != 200) {
  		  if (resString == null) {
  		    resString = EntityUtils.toString(resEntity);
  		  }
        throw new QueryException(resString, status);
  		}
		} finally {
		  httpclient.getConnectionManager().shutdown();
		}
		return status;
	}
	
	public byte[] getFile(String url) throws IOException, QuerySecurityException, QueryException
	{
		if(forcedSSL && !url.startsWith("https"))
			throw new QuerySecurityException("SSL is required with basic auth");
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		httpclient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, 443), 
                 new UsernamePasswordCredentials(username, password));
		
		HttpGet httpget = new HttpGet(url);
		
		HttpResponse response = httpclient.execute(httpget);
		
		HttpEntity entity = response.getEntity();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		if (entity != null) {
		    InputStream instream = entity.getContent();
		    int l;
		    byte[] tmp = new byte[1];
		    while ((l = instream.read(tmp)) != -1) {
		    	bos.write(tmp);
		    }
		}
		
		int status = response.getStatusLine().getStatusCode();
		
		if(status != 200)
			throw new QueryException(new String(bos.toByteArray()), status);
		
		return bos.toByteArray();
	}
	
	abstract public void doQuery() throws IOException, QueryException;
	
	private String username;
	private String password;
	private boolean forcedSSL;
}
