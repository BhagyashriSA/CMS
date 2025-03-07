package com.in.cafe.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.in.cafe.pojo.User;

public interface UserService {
	
	ResponseEntity<String> signUp(Map<String, String> requestMap);
	
	List<User> getUser();
	
	Optional<User> getUserById(Integer Id);
	
	ResponseEntity<String> update(Map<String, String> requestMap);
	
	User updateUser(User user);
	
	void deleteById(Integer Id);
	
    void sendEmail(String to, String subject, String body);

}
