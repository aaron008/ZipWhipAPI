package com.hd.zipwhip.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.hd.zipwhip.command.GetZipWhipSessionKeyCommand;
import com.hd.zipwhip.command.UpdateZipWhipContactCommand;
import com.hd.zipwhip.response.UpdateZipWhipContactResponse;
import com.hd.zipwhip.response.ZipWhipSessionKeyResponse;
import com.hd.zipwhip.util.QueryString;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;

@Service
public class ZipWhipServiceImp implements ZipWhipService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value(value = "${ZipWhipBaseUrl}")
	private String url;
	
	public boolean updateZipWhipContacts(MultipartFile file) throws Exception {
		String user = System.getenv("zipWhipUser");
		String password = System.getenv("zipWhipPassword");
		
		ZipWhipSessionKeyResponse zipWhipSessionKeyResponse = getZipWhipSessionKeyCommand(user, password);
		
		if (zipWhipSessionKeyResponse.getSuccess()) {
			try {
					List<String> contactQueryList = extractContacts(file, zipWhipSessionKeyResponse.getResponse());
					
					for (String query : contactQueryList) {						
						try{
							UpdateZipWhipContactResponse updateZipWhipContactResponse = updateZipWhipContactResponse(query);
							if (!updateZipWhipContactResponse.getSuccess()) {
								System.out.println("Error Updating contact - query - "+query);
							}
						}catch (Exception e1){
							System.out.println("Error Updating contact - query - "+query);
							return false;
						}
						
					}
			}catch(Exception e2) {
				return false;
			}
		}
		
		return true;
		
	}



	private List<String> extractContacts(MultipartFile file, String session ) throws IOException {
		BufferedReader br = null;
		List<String> contactQueryList = new ArrayList<>();
		String line;
		InputStream is = file.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));
		
		//skip first line, first line is header
		if (br != null)
			br.readLine();
		
		QueryString qs;
		while((line = br.readLine()) != null) {
			qs = new QueryString();
			String[] contact = line.split(",", -1);
			
			for (int i = 0; i < contact.length; i++) {
				if(i==0 && contact[0] != null && !contact[0].isEmpty() ) {
					qs.add("firstName", contact[0]);
				}else if(i==1 && contact[1] != null && !contact[1].isEmpty() ) {
					qs.add("lastName", contact[1]);
				}else if(i==2 && contact[2] != null && !contact[2].isEmpty() ) {
					qs.add("mobileNumber", contact[2]);
				}else if(i==3 && contact[3] != null && !contact[3].isEmpty() ) {
					qs.add("businessName", contact[3]);
				}else if(i==4 && contact[4] != null && !contact[4].isEmpty() ) {
					qs.add("jobTitle", contact[4]);
				}else if(i==5 && contact[5] != null && !contact[5].isEmpty() ) {
					qs.add("email", contact[5]);
				}else if(i==6 && contact[6] != null && !contact[6].isEmpty() ) {
					qs.add("addressLine1", contact[6]);
				}else if(i==7 && contact[7] != null && !contact[7].isEmpty() ) {
					qs.add("addressLine2", contact[7]);
				}else if(i==8 && contact[8] != null && !contact[8].isEmpty() ) {
					qs.add("city", contact[8]);
				}else if(i==9 && contact[9] != null && !contact[9].isEmpty() ) {
					qs.add("state", contact[9]);
				}else if(i==10 && contact[10] != null && !contact[10].isEmpty() ) {
					qs.add("zipcode", contact[10]);
				}else if(i==11 && contact[11] != null && !contact[11].isEmpty() ) {
					qs.add("country", contact[11]);
				}else if(i==12 && contact[12] != null && !contact[12].isEmpty() ) {
					qs.add("custom1", contact[12]);
				}else if(i==13 && contact[13] != null && !contact[13].isEmpty() ) {
					qs.add("custom2", contact[13]);
				}else if(i==14 && contact[14] != null && !contact[14].isEmpty() ) {
					qs.add("notes", contact[14]);
				}
				
				qs.add("session", session);
			}
			
			contactQueryList.add(qs.toString());
		}
		
		return contactQueryList;
	}
	
	
	
	protected ZipWhipSessionKeyResponse getZipWhipSessionKeyCommand(String user, String password) throws InterruptedException {
		return new GetZipWhipSessionKeyCommand(Factory.asKey("GetZipWhipSessionKeyCommand"), restTemplate, url, user, password).execute();
	}
	
	protected UpdateZipWhipContactResponse updateZipWhipContactResponse(String query) throws InterruptedException {
		return new UpdateZipWhipContactCommand(Factory.asKey("UpdateZipWhipContactCommand"), restTemplate, url, query).execute();
	}
	
	

	



	
}
