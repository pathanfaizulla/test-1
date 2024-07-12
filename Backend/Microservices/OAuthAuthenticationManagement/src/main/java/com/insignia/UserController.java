package com.insignia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.insignia.model.AuthenticationRequest;
import com.insignia.model.AuthenticationResponse;

@RestController
@CrossOrigin(origins = "https://oauth.insigniaconsultancy.com:8082")
public class UserController {

	@Autowired
	public RestTemplate restTemplate;

	@Value("${jwtUrl}")
	private String jwtUrl;

	@GetMapping("/")
	public ModelAndView showLogin() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index.html");
		return modelAndView;
	}

	@GetMapping("/welcome")
	public ModelAndView welcome() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("welcome.html");
		return modelAndView;
	}

	@GetMapping("/oauth2authentication")
	public ModelAndView gooogleOAuthAuthentication(
			@RequestParam("isToValidatePassword") boolean isToValidatePassword,
			OAuth2AuthenticationToken authentication) {

		OAuth2User authentication1 = authentication.getPrincipal();
		String email = null;

		if (authentication1 instanceof DefaultOAuth2User) {
			DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
			email = oauth2User.getAttribute("email");
		}

		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setEmailId(email);
		authenticationRequest.setIsToValidatePassword(isToValidatePassword);
		 jwtAuthentication(authenticationRequest);
		 ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("home.html");
			return modelAndView;
	}
	
	@PostMapping("/jwtToken")
	public AuthenticationResponse jwtAuthentication(@RequestBody AuthenticationRequest authenticationRequest) {
		return restTemplate.postForObject(jwtUrl + "/authenticate", authenticationRequest,
				AuthenticationResponse.class);
	}
}