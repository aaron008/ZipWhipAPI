package com.hd.zipwhip.service;

import org.springframework.web.multipart.MultipartFile;

public interface ZipWhipService {
	public boolean updateZipWhipContacts(MultipartFile file) throws Exception;
}
