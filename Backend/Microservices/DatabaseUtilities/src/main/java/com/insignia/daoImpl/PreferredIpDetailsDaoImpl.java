package com.insignia.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.PreferredIpDetailsDaoInterface;
import com.insignia.entity.PreferredIpDetails;
import com.insignia.repo.PreferredIpDetailsRepository;

@Repository
public class PreferredIpDetailsDaoImpl implements PreferredIpDetailsDaoInterface {

	@Autowired
	private PreferredIpDetailsRepository preferredIpDetailsRepository;

	@Override
	public PreferredIpDetails saveIPWhitelistingManagement(PreferredIpDetails ipWhitelistingManagement) {
		return preferredIpDetailsRepository.save(ipWhitelistingManagement);
	}

	@Override
	public List<PreferredIpDetails> getDetailsByCustomerSequeceNumber(Long customerSequeceNumber) {
		return preferredIpDetailsRepository.getDetailsByCustomerSequenceNumber(customerSequeceNumber);
	}

	@Override
	public List<PreferredIpDetails> getAllIpDetails() {
		return preferredIpDetailsRepository.findAll();
	}

	@Override
	public void deleteByCustomerSequenceNumber(Long customerSequenceNumber) {
		preferredIpDetailsRepository.deleteDetailsByCustomerSequenceNumber(customerSequenceNumber);

	}

	@Override
	public void deleteByIpDetails(Long customerSequenceNumber, String ipDetails) {
		preferredIpDetailsRepository.deleteDetailsByIpDetails(customerSequenceNumber, ipDetails);

	}

	@Override
	public List<Long> detailsExistByCustomerSequeceNumber(Long customerSequeceNumber) {
		return preferredIpDetailsRepository.detailsExistByCustomerSequenceNumber(customerSequeceNumber);
	}

	@Override
	public Object ipDetailsExistOrNot(Long customerSequenceNumber, String ipDetails) {

		return preferredIpDetailsRepository.detailsExistByIpDetails(customerSequenceNumber, ipDetails);
	}

}
