package com.insignia.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.SubscriptionRequest;
import com.insignia.model.SubscriptionResponse;

public interface SubscriptionServiceInterface {

	public SubscriptionResponse saveSubscriptionPlan(SubscriptionRequest subscriptionRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public SubscriptionResponse updateSubscriptionPlan(SubscriptionRequest subscriptionRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public void deleteSubscriptionPlan(SubscriptionRequest subscriptionRequest)
			throws TokenExpiredException, InvalidInputParametersException;

	public SubscriptionResponse activateSubscriptionPlan(SubscriptionRequest subscriptionRequest)
			throws InvalidInputParametersException, ParseException, TokenExpiredException;

	public SubscriptionResponse deactivateSubscriptionPlan(SubscriptionRequest subscriptionRequest)
			throws InvalidInputParametersException, ParseException, TokenExpiredException;

	public Optional<List<SubscriptionResponse>> getSubscriptionDetails(SubscriptionRequest subscriptionRequest) throws InvalidInputParametersException, TokenExpiredException;

}
