package com.microservices.customerdetail.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

	private String houseNo;
	private String street;
	private String city;
	private String state;
	private String country;
	private String pin;
	private Boolean defaultAddress;
	private String addressType;
}
