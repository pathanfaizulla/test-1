package com.insignia.validations;

import com.insignia.constants.CustomerPersonalDetailsConstants;
import com.insignia.constants.RolesAndPermissionsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class RolesAndPermissonsValidations {

	public static void validateCustomerSequenceNumber(Long customerSequenceNumber, int length)
			throws InvalidInputParametersException {
		if (customerSequenceNumber == null || customerSequenceNumber.toString().isBlank()) {
			throw new InvalidInputParametersException(RolesAndPermissionsConstants.validCustomerSequenceNumberErrorCode,
					RolesAndPermissionsConstants.validCustomerSequenceNumber);
		}

		String customerSequenceNumberStr = customerSequenceNumber.toString();

		if (customerSequenceNumberStr.length() < length) {
			throw new InvalidInputParametersException(
					RolesAndPermissionsConstants.validCustomerSequenceNumberLengthErrorCode,
					RolesAndPermissionsConstants.validCustomerSequenceNumberLength);
		}
	}

	public static void validateRoleName(String roleName, int length) throws InvalidInputParametersException {

		if (roleName == null || roleName == "" || roleName.isBlank()) {
			throw new InvalidInputParametersException(RolesAndPermissionsConstants.validRoleNameErrorCode,
					RolesAndPermissionsConstants.validRoleName);

		} else if (roleName.length() > length) {
			throw new InvalidInputParametersException(RolesAndPermissionsConstants.validRoleNameLengthErrorCode,
					RolesAndPermissionsConstants.validRoleNameLength);

		} else if (!roleName.matches(CustomerPersonalDetailsConstants.regularExpression)) {
			throw new InvalidInputParametersException(
					RolesAndPermissionsConstants.validRoleNameInvalidCharactersErrorCode,
					RolesAndPermissionsConstants.validRoleNameExpression);
		}

	}

	public static void validateUpdatePermissions(String updatePermissions, int length)
			throws InvalidInputParametersException {

		if (updatePermissions != null && updatePermissions.length() > length) {
			throw new InvalidInputParametersException(
					RolesAndPermissionsConstants.validUpdatePermissionsLengthErrorCode,
					RolesAndPermissionsConstants.validUpdatePermissionsLength);

		}
	}

}
