package com.in.cafe.restimpl;

import java.util.Collections;
import java.util.List
;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.in.cafe.contents.CafeConstant;
import com.in.cafe.pojo.User;
import com.in.cafe.rest.UserRest;
import com.in.cafe.service.UserService;
import com.in.cafe.util.CafeUtil;



@RestController
public class UserRestImpl implements UserRest {
	
	@Autowired
    UserService userservice;
    
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			return userservice.signUp(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();	
		}
		return CafeUtil.getResponseEntity(CafeConstant.SOMETHING_WENT_WROMG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getUser() {
	    List<User> users; // Declare outside try block
	    try {
	        users = userservice.getUser();
	        return new ResponseEntity<>(users, HttpStatus.OK); // Return 200 OK for success
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR); // Return empty list on failure
	    }
	}

	@Override
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Integer id) {
        try {
            Optional<User> user = userservice.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user); // Returns 200 OK with user data
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if user not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 on failure
        }
    }


	@Override
	public ResponseEntity<User> updateUser(Integer id, User userDetails) {
		// TODO Auto-generated method stub
		 Optional<User> optionalUser = userservice.getUserById(id);
	       if (!optionalUser.isPresent()) {
	            return ResponseEntity.notFound().build();
	        }
	
	        User user = optionalUser.get();
	        if (userDetails.getName() != null) user.setName(userDetails.getName());
	        if (userDetails.getEmail() != null) user.setEmail(userDetails.getEmail());
	        if (userDetails.getContactNumber() != null) user.setContactNumber(userDetails.getContactNumber());
	        if (userDetails.getPassword() != null) user.setPassword(userDetails.getPassword());
	        if (userDetails.getStatus() != null) user.setStatus(userDetails.getStatus());
	        if (userDetails.getRole() != null) user.setRole(userDetails.getRole());
	        
	        User updatedUser = userservice.updateUser(user);
	        return ResponseEntity.ok(updatedUser);
	}

	@Override
	public ResponseEntity<String> deleteUser(Integer id) {
		// TODO Auto-generated method stub
		 Optional<User> optionalUser = userservice.getUserById(id);
	       if (!optionalUser.isPresent()) {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body("User not found with ID: " + id);
	        }
	       userservice.deleteById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

	@Override
	public String sendEmail(String to, String subject, String body) {
		// TODO Auto-generated method stub
		userservice.sendEmail(to, subject, body);
        return "Email Sent Successfully!";
	}

}
