package com.hd.zipwhip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hd.zipwhip.response.BasicResponse;
import com.hd.zipwhip.service.ZipWhipService;

@RestController
@RequestMapping("/api/v1")
public class ZipWhipController {
	
	@Autowired
	ZipWhipService service;

	
	@PostMapping(value = "/contacts")
	public ResponseEntity<BasicResponse> updateContacts(@RequestParam("file") MultipartFile file) throws Exception{
		boolean updateZipWhipContacts = service.updateZipWhipContacts(file);
		BasicResponse response = BasicResponse.builder()
								.success(updateZipWhipContacts)
								.build();
		return ResponseEntity.ok(response);
	}
	

}
