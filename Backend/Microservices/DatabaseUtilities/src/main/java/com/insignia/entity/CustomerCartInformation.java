package com.insignia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_cart_information")
public class CustomerCartInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_cart_sequence_number")
	private Long customerCartSequenceNumber;

	@Column(name = "product_sequence_number")
	private Long productSequenceNumber;

	@Column(name = "cart_sequence_number")
	private Long cartSequenceNumber;

	@Column(name = "product_quantity")
	private String productQuantity;
	
}
