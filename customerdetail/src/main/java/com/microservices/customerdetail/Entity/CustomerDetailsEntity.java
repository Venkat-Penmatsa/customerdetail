package com.microservices.customerdetail.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CusomterDetails")
@Getter
@Setter
public class CustomerDetailsEntity {

	@Id
	private int id;

	@Column(name = "firstName")
	private String fistName;

	@Column(name = "lastName")
	private String lastName;

}
