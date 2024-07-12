package com.insignia.daoInterface;

import java.util.List;

import com.insignia.entity.PreferredIpDetails;

public interface PreferredIpDetailsDaoInterface {

	public PreferredIpDetails saveIPWhitelistingManagement(PreferredIpDetails ipWhitelistingManagement);

	public List<PreferredIpDetails> getDetailsByCustomerSequeceNumber(Long customerSequeceNumber);

	public List<PreferredIpDetails> getAllIpDetails();

	public void deleteByCustomerSequenceNumber(Long customerSequenceNumber);

	public void deleteByIpDetails(Long customerSequenceNumber, String ipDetails);

	public List<Long> detailsExistByCustomerSequeceNumber(Long customerSequeceNumber);

	public Object ipDetailsExistOrNot(Long customerSequenceNumber, String ipDetails);

}
