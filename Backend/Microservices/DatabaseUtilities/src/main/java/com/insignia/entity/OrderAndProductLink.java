package com.insignia.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_and_product_link")
public class OrderAndProductLink {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sequence_number")
	private Long sequenceNumber;

	@Column(name = "product_quantity")
	private Integer productQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_sequence_number")
	private OrderDetails orderDetails;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@Column(name = "per_unit_price")
	private BigDecimal perUnitPrice;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_sequence_number")
	private ProductDetails productDetails;
}
