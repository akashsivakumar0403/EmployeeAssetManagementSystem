package com.eams.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class usercontroller {
	
	@GetMapping("/Welcome")
	public String hello() {
		return "Welcome to EAMS";
	}

}
