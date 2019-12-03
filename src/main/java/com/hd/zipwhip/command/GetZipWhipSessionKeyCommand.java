package com.hd.zipwhip.command;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hd.zipwhip.response.ZipWhipSessionKeyResponse;
import com.hd.zipwhip.util.QueryString;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class GetZipWhipSessionKeyCommand extends HystrixCommand<ZipWhipSessionKeyResponse>{

	private String url;
	private String PATH = "/user/login";
	private String user;
	private String password;
	private RestTemplate restTemplate;
	
	public GetZipWhipSessionKeyCommand(HystrixCommandGroupKey group, RestTemplate restTemplate, String baseUrl,String user, String password) {
		super(group, 30000);
		this.restTemplate = restTemplate;
		this.url = baseUrl+PATH;
		this.user = user;
		this.password =password;
	}

	@Override
	protected ZipWhipSessionKeyResponse run() throws Exception {
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		
		QueryString qs = new QueryString("username", user);
		qs.add("password", password);
		String query = "username="+user+"&password="+password;
		HttpEntity requestEntity = new HttpEntity(query,headers);
		
		ResponseEntity<ZipWhipSessionKeyResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity , ZipWhipSessionKeyResponse.class);
		return responseEntity.getBody();
	}

}
