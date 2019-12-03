package com.hd.zipwhip.command;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hd.zipwhip.response.UpdateZipWhipContactResponse;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class UpdateZipWhipContactCommand extends HystrixCommand<UpdateZipWhipContactResponse>{

	private String url;
	private String PATH = "/contact/save";
	private String user;
	private String query;
	private RestTemplate restTemplate;
	
	public UpdateZipWhipContactCommand(HystrixCommandGroupKey group, 
									   RestTemplate restTemplate,
									   String baseUrl,
									   String query) {
		super(group, 30000);
		this.restTemplate = restTemplate;
		this.url = baseUrl+PATH;
		this.query =query;
	}

	@Override
	protected UpdateZipWhipContactResponse run() throws Exception {
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		
		HttpEntity requestEntity = new HttpEntity(query,headers);
		
		ResponseEntity<UpdateZipWhipContactResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity , UpdateZipWhipContactResponse.class);
		return responseEntity.getBody();
	}

}
