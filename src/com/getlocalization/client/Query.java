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

public abstract class Query {
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
	
	public int postFile(File file, String url) throws IOException, QuerySecurityException
	{
		if(forcedSSL && !url.startsWith("https"))
			throw new QuerySecurityException("SSL is required with basic auth");
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		 httpclient.getCredentialsProvider().setCredentials(
                 new AuthScope(AuthScope.ANY_HOST, 443), 
                 new UsernamePasswordCredentials(username, password));

		HttpPost httppost = new HttpPost(url);
		
		MultipartEntity mpEntity = new MultipartEntity();
		
		System.out.println("Posting file:" + file.getAbsolutePath());
		
		ContentBody cbFile = new FileBody(file, file.getName(), "text/plain", "utf8");
		mpEntity.addPart("file", cbFile);

		httppost.setEntity(mpEntity);

		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();

		if (resEntity != null) {
			System.out.println(EntityUtils.toString(resEntity));
		}
		
		httpclient.getConnectionManager().shutdown();
		
		return response.getStatusLine().getStatusCode();
	}
	
	public byte[] getFile(String url) throws IOException, QuerySecurityException
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
		
		return bos.toByteArray();
	}
	
	abstract public void doQuery() throws IOException, QueryException;
	
	private String username;
	private String password;
	private boolean forcedSSL;
}
