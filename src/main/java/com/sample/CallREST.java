package com.sample;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

@SuppressWarnings({ "restriction", "deprecation" })
public class CallREST {

	public static void main(String[] args) {

	  try {
		  CallREST cr= new CallREST();
		  cr.posthisrest("name=kadd&age=29");
		
	  } catch (Exception e) {

		e.printStackTrace();

	  } 

	}
	public void posthisrest(String ktest){
	try {

		@SuppressWarnings("resource")
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
			"http://localhost:8181/jboss-helloworld-rs/rest/add");

		
		//StringEntity input = new StringEntity("name=ktest&age=44");
		StringEntity input = new StringEntity(ktest);
		input.setContentType("application/x-www-form-urlencoded");
		postRequest.setEntity(input);
		//curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -H "Cache-Control: no-cache"
		//-H "Postman-Token: 58be18cf-762a-b597-cb7f-f28fe8eaef64" -d 'name=asdf&age=44' "http://localhost:8081/jboss-helloworld-rs/rest/add"
		
		HttpResponse response = httpClient.execute(postRequest);
        /*
		if (response.getStatusLine().getStatusCode() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatusLine().getStatusCode());
		}
		*/

		BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));

		String output;
		System.out.println("Output from Server hosting REST service .... ");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			System.out.println("*********************\n");
		}

		httpClient.getConnectionManager().shutdown();

	  } catch (Exception e) {

		e.printStackTrace();

	  } 

	}
}