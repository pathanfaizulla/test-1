package com.insignia.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequest {

	private Long customerSequenceNumber;
	private Integer expirationDuration;
	
	private Long orderSequenceNumber;
	private String orderStatus;
	
	private Long orderId;
	private Date orderDate;
	private String invoiceId;
	private Long addressSequenceNumber;
	private BigDecimal totalPrice;

	private List<OrderAndProductLinkRequest> orderAndProductLinkRequestList;

	
}
