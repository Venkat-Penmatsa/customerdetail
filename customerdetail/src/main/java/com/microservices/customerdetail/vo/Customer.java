package com.microservices.customerdetail.vo;

import java.util.List;
import com.microservices.customerdetail.vo.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

	public Customer() {
		
	}

	public Customer(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	private int id;
	private String firstName;
	private String lastName;
	private List<Address> addressList;
	private List<Accounts> accountsList;

}
