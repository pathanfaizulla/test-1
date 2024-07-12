package com.insignia.daoImpl;

import com.insignia.constants.CustomerBasicDetailsConstants;
import com.insignia.constants.SecretKeyConstant;
import com.insignia.customExceptions.RoleNotFoundException;
import com.insignia.daoInterface.JwtDao;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.entity.RolesAndPermissions;
import com.insignia.entity.TokensEntity;
import com.insignia.repo.CustomerBasicDetailsRepository;
import com.insignia.repo.TokensEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class JwtDaoImpl implements JwtDao {

	@Autowired
	private TokensEntityRepo tokenRepo;

	@Autowired
	private CustomerBasicDetailsRepository customerRepo;

	public JwtDaoImpl(TokensEntityRepo tokenRepo, CustomerBasicDetailsRepository customerRepo) {
		super();
		this.tokenRepo = tokenRepo;
		this.customerRepo = customerRepo;
	}

	public CustomerBasicDetailsEntity findByUserName(String userName)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, ParseException, RoleNotFoundException {

		List<Object[]> resultList = customerRepo.fetchUserDetails(userName);

		CustomerBasicDetailsEntity cbde = new CustomerBasicDetailsEntity();
		if (!resultList.isEmpty()) {
			Object[] custObj = resultList.get(0);

			if (custObj[3] == null) {
				throw new RoleNotFoundException(CustomerBasicDetailsConstants.validateRoleErrorCode,
						CustomerBasicDetailsConstants.validateRole);
			}

			RolesAndPermissions rolesAndPermissions = new RolesAndPermissions();
			cbde.setUserName(custObj[0] != null ? String.valueOf(custObj[0]) : null);
			cbde.setCustomerPassword(custObj[1] != "" ? String.valueOf(custObj[1])
					: custObj[1].toString());
			cbde.setCustomerSequenceNumber(custObj[2] != null ? Long.valueOf(custObj[2].toString()) : null);
			rolesAndPermissions.setRoleName(custObj[3] != null ? String.valueOf(custObj[3]) : null);
			rolesAndPermissions.setRoleId(custObj[4] != null ? Integer.valueOf(custObj[4].toString()) : null);
			rolesAndPermissions.setPermission1(custObj[5] != null ? Boolean.valueOf(custObj[5].toString()) : null);
			rolesAndPermissions.setPermission2(custObj[6] != null ? Boolean.valueOf(custObj[6].toString()) : null);
			rolesAndPermissions.setPermission3(custObj[7] != null ? Boolean.valueOf(custObj[7].toString()) : null);
			rolesAndPermissions.setPermission4(custObj[8] != null ? Boolean.valueOf(custObj[8].toString()) : null);
			rolesAndPermissions.setRoleApprovedDate(
					custObj[9] != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(custObj[9].toString())
							: null);
			rolesAndPermissions.setRoleRevokedDate(
					custObj[10] != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(custObj[10].toString())
							: null);
			rolesAndPermissions.setPermissionChangeDate(
					custObj[11] != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(custObj[11].toString())
							: null);
			rolesAndPermissions.setUpdatedPermissions(custObj[12] != null ? String.valueOf(custObj[12]) : null);
			rolesAndPermissions
					.setCustomerSequenceNumber(custObj[2] != null ? Long.valueOf(custObj[2].toString()) : null);
			cbde.setOtp(custObj[13] != null ? (custObj[13].toString()) : null);
			cbde.setIsotpAuthenticated(custObj[14] != null ? Boolean.valueOf(custObj[14].toString()) : null);
			cbde.setOtpGeneratedAt(
					custObj[15] != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(custObj[15].toString())
							: null);
			cbde.setOtpExpiryAt(
					custObj[16] != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(custObj[16].toString())
							: null);
			
			cbde.setRolesAndPermissions(Arrays.asList(rolesAndPermissions));
		}
		return cbde;
	}

	public Optional<List<TokensEntity>> fetchTokendetails(Long sequenceNumber) throws ParseException {

		TokensEntity tke = new TokensEntity();
		List<TokensEntity> listOfEntity = new ArrayList<>();
		List<Object[]> tokenEntity = tokenRepo.fetQueryForTOken(sequenceNumber);
		if (!tokenEntity.isEmpty()) {
			Object[] tokenObj = tokenEntity.get(0);
			tke.setTokenSequenceNumber(sequenceNumber);
			tke.setIsLongLivedToken(tokenObj[0] != null ? Boolean.valueOf(tokenObj[0].toString()) : false);
			tke.setIsTokenExpired(tokenObj[1] != null ? Boolean.valueOf(tokenObj[1].toString()) : false);
			tke.setTokenCreatedAt(
					tokenObj[2] != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(tokenObj[2].toString())
							: null);
			tke.setTokenDetails(tokenObj[3] != null ? String.valueOf(tokenObj[3]) : null);
			tke.setTokenExpiresAt(
					tokenObj[4] != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(tokenObj[4].toString())
							: null);
			tke.setTokenRevokedAt(
					tokenObj[5] != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(tokenObj[5].toString())
							: null);
			tke.setTokenType(tokenObj[6] != null ? String.valueOf(tokenObj[6]) : null);
			listOfEntity.add(tke);
		}

		return Optional.ofNullable(listOfEntity.isEmpty() ? null : listOfEntity);
	}

	@Transactional
	public void updateTokenDetails(Integer expirationTime, Boolean isRememberMeSelected, String jwt,
			Long customerSeqNumber) {
		tokenRepo.updateToken(isRememberMeSelected, new Date(System.currentTimeMillis() + SecretKeyConstant.EXPIRY_DURATION * expirationTime),
				false, new Date(), jwt, null, customerSeqNumber);

	}

	@Transactional
	public void deleteTokenDetails(Long sequenceNumber) {
		tokenRepo.deleteToken(sequenceNumber);
	}

	@Transactional
	public void createTokenDetails(Integer expirationTime, Boolean isRememberMeSelected, Long customerSeqNumber) {
		tokenRepo.createToken(customerSeqNumber, CustomerBasicDetailsConstants.tokenType,
				CustomerBasicDetailsConstants.tokenAlgoritham,
				new Date(System.currentTimeMillis() + SecretKeyConstant.EXPIRY_DURATION * expirationTime), new Date(), null, false,
				isRememberMeSelected);
	}

	@Override
	public Object[] getPassword(String applicationId, String tenantId, String customerId) {

		return customerRepo.getPassword(applicationId, tenantId, customerId);
	}

	@Override
	public void updatePassword(String password, String customerId) {
		customerRepo.updatePassword(password, customerId);
	}

	@Override
	public List<Object[]> getOtp(String applicationId, String tenantId, String customerId) {

		return customerRepo.getOtp(applicationId, tenantId, customerId);
	}

}