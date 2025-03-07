package com.in.cafe.serviceimpl;

import java.util.List;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.in.cafe.contents.CafeConstant;
import com.in.cafe.dao.UserDao;
import com.in.cafe.service.UserService;
import com.in.cafe.util.CafeUtil;
import com.in.cafe.pojo.User;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	JavaMailSender mailSender;

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
	    System.out.println("inside map " + requestMap);
	    String responseMessage = "user";
	    try {
	    if(validateSignUpMap(requestMap)) {
    	User user =userDao.findByEmail(requestMap.get("email"));
    	System.out.println("Method1 " + user.getEmail());
	    	if(!Objects.isNull(user)) {
	    		userDao.save(getUserFromMap(requestMap));
	    		return CafeUtil.getResponseEntity(responseMessage + " successfully Registered.", HttpStatus.OK);
	    	}
	    } else {
		return CafeUtil.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
	     }
	    }catch(Exception ex) {
	    	ex.printStackTrace();
	    }
	    return CafeUtil.getResponseEntity(CafeConstant.SOMETHING_WENT_WROMG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private Boolean validateSignUpMap(Map<String, String> requestMap) {
		if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") 
				&& requestMap.containsKey("email") && requestMap.containsKey("password"))
		{
			return true;
		}
		return false;
	}
	
	private User getUserFromMap(Map<String, String> requestMap) {
		User user = new User();
		user.setName(requestMap.get("name"));
		user.setContactNumber(requestMap.get("contactNumber"));
		user.setPassword(requestMap.get("password"));
		user.setRole(requestMap.get("role"));
		user.setEmail(requestMap.get("email"));
		user.setStatus(requestMap.get("status"));
		return user;
		}
	
	@Override
	public List<User> getUser() {
		List<User> user = userDao.findAll();
//     	sendEmail("bhagyashrisay@gmail.com","CMS Mail","User registered successfully");
		return user; 
	}
	
	@Override
	public Optional<User> getUserById(Integer Id) {
	     Optional<User> user = userDao.findById(Id);
		return user; 
	}

	@Override
	public User updateUser(User user) {
		User userDetails =  userDao.save(user);
		return userDetails;
	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Integer Id) {
		// TODO Auto-generated method stub
		userDao.deleteById(Id);
	}

	@Override
	public void sendEmail(String to, String subject, String body) {
		// TODO Auto-generated method stub
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("kiransayankar091@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);

		mailSender.send(message);
		System.out.println("Email sent successfully!");
	}
	

}
