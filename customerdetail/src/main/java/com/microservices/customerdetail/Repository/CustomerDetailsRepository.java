package com.microservices.customerdetail.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservices.customerdetail.Entity.CustomerDetailsEntity;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetailsEntity, Integer> {

	@Query(value = "select * from Cusomter_Details where id =?1 ", nativeQuery = true)
	public CustomerDetailsEntity findCustomerByCustId(int custId);

}
