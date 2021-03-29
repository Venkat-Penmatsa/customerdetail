package com.microservices.customerdetail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.customerdetail.service.CustomerDetailsService;
import com.microservices.customerdetail.vo.Customer;

@RestController
@RequestMapping("/customerDetails/v1")
public class CustomerDetailsController {

	@Autowired
	private CustomerDetailsService customerDetailsService;

	@GetMapping("/allCustomers")
	public ResponseEntity<List<Customer>> getAllCustomerData() {

		List<Customer> customersList = customerDetailsService.getCustomerDetails();

		return ResponseEntity.ok().body(customersList);
	}

	@GetMapping("/customerDetails/{custId}")
	public ResponseEntity<Customer> getCustomerDetailsById(@PathVariable int custId) {

		Customer customersList = customerDetailsService.getCustomerDetailsById(custId);

		return ResponseEntity.ok().body(customersList);
	}

}
