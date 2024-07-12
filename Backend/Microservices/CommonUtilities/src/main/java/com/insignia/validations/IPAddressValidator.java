package com.insignia.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPAddressValidator {

	private static final String IPv4_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	private static final String IPv6_PATTERN = "^(?:[0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}$|"
			+ "^(?:[0-9a-fA-F]{1,4}:){1,7}:|" + "^(?:[0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}$|"
			+ "^(?:[0-9a-fA-F]{1,4}:){1,5}(?::[0-9a-fA-F]{1,4}){1,2}$|"
			+ "^(?:[0-9a-fA-F]{1,4}:){1,4}(?::[0-9a-fA-F]{1,4}){1,3}$|"
			+ "^(?:[0-9a-fA-F]{1,4}:){1,3}(?::[0-9a-fA-F]{1,4}){1,4}$|"
			+ "^(?:[0-9a-fA-F]{1,4}:){1,2}(?::[0-9a-fA-F]{1,4}){1,5}$|"
			+ "^[0-9a-fA-F]{1,4}:(?:(?::[0-9a-fA-F]{1,4}){1,6})$|" + "^:(?:(?::[0-9a-fA-F]{1,4}){1,7}|:)$";

	private static final Pattern IPv4_PATTERN_REGEX = Pattern.compile(IPv4_PATTERN);
	private static final Pattern IPv6_PATTERN_REGEX = Pattern.compile(IPv6_PATTERN);

	public static boolean isValidIPv4(String ipAddress) {
		Matcher matcher = IPv4_PATTERN_REGEX.matcher(ipAddress);
		return matcher.matches();
	}

	public static boolean isValidIPv6(String ipAddress) {
		Matcher matcher = IPv6_PATTERN_REGEX.matcher(ipAddress);
		return matcher.matches();
	}

}