package com.insignia.serviceInterface;

import jakarta.mail.MessagingException;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.model.AuthenticationRequest;
import com.insignia.model.AuthenticationResponse;

public interface OtpServiceInterface {
	public AuthenticationResponse saveAndUpdateCustomer(AuthenticationRequest authenticationRequest)
			throws MessagingException, InvalidInputParametersException;

}

