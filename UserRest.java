package com.in.cafe.rest;

import java.util.List;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.in.cafe.pojo.User;
import com.in.cafe.wrapper.UserWrapper;

@RequestMapping(path = "/user")
public interface UserRest {

	@PostMapping(path = "/signup")
	public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);
    
	@GetMapping(path = "/getUser")
    public ResponseEntity<List<User>> getUser();
	
	@GetMapping(path = "/getUserByid/{id}")
	public ResponseEntity<Optional<User>> getUserById(Integer id);
	
	@PutMapping(path = "/updateUser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails);
	
	@DeleteMapping(path = "/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id);
	
	@PostMapping(path = "/send")
    public String sendEmail(@RequestParam String to, 
	                            @RequestParam String subject, 
	                            @RequestParam String body);
    
} 
