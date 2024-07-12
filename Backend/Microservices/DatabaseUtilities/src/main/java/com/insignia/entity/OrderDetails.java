package com.insignia.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_sequence_number")
	private Long orderSequenceNumber;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "order_date")
	private Date orderDate;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "invoice_id")
	private String InvoiceId;

	@Column(name = "customer_sequence_number")
	private Long customerSequenceNumber;

	@Column(name = "address_sequence_number")
	private Long addressSequenceNumber;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "orderDetails",cascade = CascadeType.ALL )
	private List<OrderAndProductLink> orderAndProductLinkList;
	

}
