package com.in.cafe;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/home")
public class HomeControlller {
	
	@GetMapping(path = "/users")
	public String getUser() {
		return "user login successfully";
	}

}
