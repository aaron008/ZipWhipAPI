package com.hd.zipwhip.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContactRequest {
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String businessName; //Company
	private String jobTitle;
	private String email;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipcode; //postal code
	private String country;
	private String customField1;
	private String customField2;
	private String notes;
}
