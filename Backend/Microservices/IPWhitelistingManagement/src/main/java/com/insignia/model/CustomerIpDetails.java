package com.insignia.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerIpDetails {

	private Long customerSequenceNumber;
	private List<IpDetails> ipDetailsList;

}
