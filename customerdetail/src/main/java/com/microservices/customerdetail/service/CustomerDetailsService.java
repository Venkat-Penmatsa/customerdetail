package com.microservices.customerdetail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.customerdetail.Entity.CustomerDetailsEntity;
import com.microservices.customerdetail.Repository.CustomerDetailsRepository;
import com.microservices.customerdetail.vo.Accounts;
import com.microservices.customerdetail.vo.Address;
import com.microservices.customerdetail.vo.Customer;

@Service
public class CustomerDetailsService {

	@Autowired
	private CustomerDetailsRepository customerDetailsRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;

	public List<Customer> getCustomerDetails() {

		List<Customer> customersList = new ArrayList<Customer>();

		List<CustomerDetailsEntity> customerDetailsEntity = customerDetailsRepository.findAll();

		if (!customerDetailsEntity.isEmpty()) {

			customersList = customerDetailsEntity.stream().map(customerEntity -> new Customer(customerEntity.getId(),
					customerEntity.getFistName(), customerEntity.getLastName())).collect(Collectors.toList());
		}

		return customersList;
	}

	public Customer getCustomerDetailsById(int custId) {

		Customer customer = new Customer();
		CustomerDetailsEntity customerEntity = customerDetailsRepository.findCustomerByCustId(custId);

		if (customerEntity != null) {

			customer = new Customer(customerEntity.getId(), customerEntity.getFistName(), customerEntity.getLastName());
		}

		customer.setAddressList(getAddressDetails(custId));
		customer.setAccountsList(getAccountDetails(custId));

		return customer;
	}

	private List<Accounts> getAccountDetails(int custid) {

		String url = "http://accounts-details-service/accountDetails/v1/customerAccounts/" + custid;

		ResponseEntity<List<Accounts>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Accounts>>() {
				});
		return response.getBody();

	}

	private List<Address> getAddressDetails(int custid) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		
		return circuitBreaker.run(() -> restTemplate
				.exchange("http://ADDRESS-DETAILS-SERVICE/addressDetails/v1/customerAddress/" + custid, HttpMethod.GET,
						null, new ParameterizedTypeReference<List<Address>>() {
						})
				.getBody(), throwable -> getDefaultAddressList());

		/*
		 * ResponseEntity<List<Address>> response = restTemplate.exchange(
		 * "http://ADDRESS-DETAILS-SERVICE/addressDetails/v1/customerAddress/" + custid,
		 * HttpMethod.GET, null, new ParameterizedTypeReference<List<Address>>() { });
		 * 
		 * return (List<Address>) response.getBody();
		 */

	}

	private List<Address> getDefaultAddressList() {

		List<Address> addressList = new ArrayList<Address>();

		Address address = new Address();
		address.setAddressType("dummy");
		address.setCity("dummy");
		address.setCountry("dummy");
		address.setHouseNo("dummy");
		address.setState("dummy");
		address.setStreet("dummy");

		addressList.add(address);

		return addressList;
	}

	/*
	 * private List<Address> getAddressDetails(int custid) {
	 * 
	 * String url = "http://localhost:9092/addressDetails/v1/customerAddress/" +
	 * custid;
	 * 
	 * RestTemplate restTemplate = new RestTemplate(); ResponseEntity<List<Address>>
	 * response = restTemplate.exchange(url, HttpMethod.GET, null, new
	 * ParameterizedTypeReference<List<Address>>() { }); return response.getBody();
	 * 
	 * }
	 */

}
