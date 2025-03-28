package com.in.cafe.serviceimpl;

import java.io.File;
import java.util.List;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.in.cafe.contents.CafeConstant;
import com.in.cafe.dao.AutherRepository;
import com.in.cafe.dao.BookRepository;
import com.in.cafe.dao.CafeUserRepository;
import com.in.cafe.dao.EmployeeDao;
import com.in.cafe.dao.ManagerRepository;
import com.in.cafe.dao.UserDao;
import com.in.cafe.service.UserService;
import com.in.cafe.util.CafeUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import com.in.cafe.pojo.Auther;
import com.in.cafe.pojo.Book;
import com.in.cafe.pojo.CafeUser;
import com.in.cafe.pojo.Employee;
import com.in.cafe.pojo.Manager;
import com.in.cafe.pojo.Staff;
import com.in.cafe.pojo.User;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	ManagerRepository managerRepository;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	AutherRepository autherRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	CafeUserRepository cafeRepository;


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
//     	sendEmail("abcy@gmail.com","CMS Mail","User registered successfully");
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
		message.setFrom("xyz@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);

		mailSender.send(message);
		System.out.println("Email sent successfully!");
	}
	
    public Employee createUser(String name, String email) {
    	Employee employee = new Employee(name, email);
        return employeeDao.save(employee);
    }

	@Override
	public String saveManager(Manager manager) {
		// TODO Auto-generated method stub
		try {
			Staff staff = new Staff();
			staff.setName(manager.getName());
			staff.setPosition("Developer");
			managerRepository.save(staff);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return "Manager save Successfully";
	}

	@Override
	public Auther saveAuther(Auther auther) {
		// TODO Auto-generated method stub
		Auther saveAuther = autherRepository.save(auther);
		return saveAuther;
	}

	@Override
	public Book saveBook(Book book) {
		// TODO Auto-generated method stub
		Book saveBook = bookRepository.save(book);
		return saveBook;
	}

	@Override
	public List<Auther> getAllAuther() {
		// TODO Auto-generated method stub
		List<Auther> auther = autherRepository.findAll();
		return auther;
	}

	@Override
	public List<Book> getAllBook() {
		// TODO Auto-generated method stub
		List<Book> book = bookRepository.findAll();
		return book;
	}

	@Override
	public void saveCafeUser(CafeUser cafeUser) {
		// TODO Auto-generated method stub
		cafeRepository.save(cafeUser);
	}

	@Override
	public void generatePasswordResetToken(String email) {
		// TODO Auto-generated method stub
        CafeUser cafeuser = cafeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Generate a token (this could be UUID or any other strategy)
        String token = UUID.randomUUID().toString();
        cafeuser.setResetPasswordToken(token);
        cafeRepository.save(cafeuser);

        // Send reset email
        sendPasswordResetEmail(cafeuser, token);
	}

	private void sendPasswordResetEmail(CafeUser cafeuser, String token) {
		// TODO Auto-generated method stub
		String resetLink = "http://localhost:8080/user/reset-password" + "?token=" + token;
		
		System.out.println("resetnpassword link :" + resetLink);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("xyz@gmail.com");
		message.setTo(cafeuser.getEmail());
		message.setSubject("Password Reset Request");
		message.setText("To reset your password, click the link below:\n" + resetLink);
		mailSender.send(message);
	}

	@Override
	public void resetPassword(String token, String newPassword) {
		// TODO Auto-generated method stub
		CafeUser cafeUser = cafeRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        // Hash the new password (make sure you hash it before saving)
        String hashedPassword = new BCryptPasswordEncoder().encode(newPassword);
        cafeUser.setPassword(hashedPassword);
        cafeUser.setResetPasswordToken(null); // Remove the token after resetting the password
        cafeRepository.save(cafeUser);
    }

	@Override
	public void changePassword(Map<String, String> request) {
		// TODO Auto-generated method stub
		String email = request.get("email");
		String oldPassword = request.get("oldPassword");
		String newPassword = request.get("newPassword");
		 CafeUser cafeuser = cafeRepository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
		 
		 if(oldPassword.equals(cafeuser.getPassword())) {
			 String hasHadPassword = new BCryptPasswordEncoder().encode(newPassword);
			 cafeuser.setPassword(hasHadPassword);
			 cafeRepository.save(cafeuser);
		 } else {
			 throw new RuntimeException("Invalid password");
		 }
		
	}

	@Override
	public void sendEmailWithAttachment(File file) throws MessagingException  {
		// TODO Auto-generated method stub
		String to = "abc@gmail.com";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message

        helper.setFrom("xyz@gmail.com");
        helper.setTo(to);
		helper.setSubject("Please Find Attached [Document/File]");
		helper.setText("Please find the attachment.");

        // Adding attachment	
        if (file != null && file.exists()) {
            helper.addAttachment(file.getName(), file);
        }  
        mailSender.send(message);
	}
	
	}



	


