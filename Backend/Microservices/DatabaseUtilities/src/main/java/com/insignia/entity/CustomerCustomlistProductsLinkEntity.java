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
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "customer_customlist_products_link")
public class CustomerCustomlistProductsLinkEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "sequence_number")
	private Long sequenceNumber;

	@Column(name = "customer_customlist_seq_number", nullable = false)
	private Long customerCustomListSeqNumber;

	@Column(name = "product_seq_number", nullable = false)
	private Long productSeqNumber;

	@Column(name = "product_quantity", length = 3)
	private Integer productQuantity;

}
