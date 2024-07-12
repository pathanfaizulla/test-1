package com.insignia.daoInterface;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.insignia.customExceptions.RoleNotFoundException;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.entity.TokensEntity;

public interface JwtDao {

	public Optional<List<TokensEntity>> fetchTokendetails(Long seqNo) throws ParseException;

	public void updateTokenDetails(Integer expirationTime, Boolean isRememberMeSelected, String jwt,
			Long customerSeqNumber);

	public void deleteTokenDetails(Long seqNo);

	public void createTokenDetails(Integer expirationTime, Boolean isRememberMeSelected, Long customerSeqNumber);

	public CustomerBasicDetailsEntity findByUserName(String userName)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, ParseException, RoleNotFoundException;

	public Object[] getPassword(String applicationId, String tenantId, String customerId);
	
	public void updatePassword(String password, String customerId);
	
	public List<Object[]> getOtp(String applicationId, String tenantId, String customerId);
	
	

}
