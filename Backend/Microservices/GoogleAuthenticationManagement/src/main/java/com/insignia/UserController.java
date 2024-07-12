package com.insignia;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

	@GetMapping("/secure")
	public String showHome(Principal p) {
		System.out.println(p);
		return "secure";
	}

	@GetMapping("/")
	public ModelAndView showLogin() {
		System.err.println("hello");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index.html");
		return modelAndView;
	}
}
