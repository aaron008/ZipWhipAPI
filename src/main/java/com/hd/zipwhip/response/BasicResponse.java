package com.hd.zipwhip.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {

	private boolean success;
	private String message;
	private String error;
	private String errorCode;
}
