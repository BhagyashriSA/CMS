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

import com.in.cafe.pojo.Auther;
import com.in.cafe.pojo.Book;
import com.in.cafe.pojo.CafeUser;
import com.in.cafe.pojo.Employee;
import com.in.cafe.pojo.Manager;
import com.in.cafe.pojo.Person;
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
	
//	@PostMapping(path = "/send")
//	public String sendEmail(
//	        @RequestParam(name = "to", required = false, defaultValue = "bhagyashrisay@gmail.com") String to,
//	        @RequestParam(name = "subject", required = false, defaultValue = "No Subject") String subject,
//	        @RequestParam(name = "body", required = false, defaultValue = "No Content") String body);
	
	@PostMapping(path = "/send")
	public String sendEmail(@RequestBody Map<String, String> requestBody);

	@GetMapping(path = "/sendMail")
	public String sendMailById();
	
    @PostMapping(path = "/createuser")
    public ResponseEntity<String> createUser(@RequestBody(required = true) String name, @RequestBody(required = true) String email); 

    @GetMapping(path = "/show")
    public String show();
    
    @PostMapping(path = "/takeInput")
	public ResponseEntity<String> takeInput(@RequestBody(required = true) Employee employee);
    
    @PostMapping(path="/saveppersondata")
    public ResponseEntity<String> savePersonData(@RequestBody(required = true) Person person);
     
    @GetMapping(path="/person/{id}")
    public ResponseEntity<Optional<Person>> getPersonById(@PathVariable Long id); 

//    @GetMapping(path="/checkToken")
//    public ResponseEntity<String> checkToken();
    
    @PostMapping(path = "/saveManager")
    public String saveManager(@RequestBody(required =  true) Manager manager);
    
    @PostMapping(path = "/saveAuther")
    public ResponseEntity<Auther> saveAuther(@RequestBody(required = true) Auther auther);
    
    @PostMapping(path = "/saveBook")
    public ResponseEntity<Book> saveBook(@RequestBody(required = true) Book book);
    
    @GetMapping(path = "/getAllAuther")
    public ResponseEntity<List<Auther>> getAllAuther();
    
    @GetMapping(path = "/getAllBook")
    public ResponseEntity<List<Book>> getAllBook();
    
    @PostMapping(path = "/saveCafeUser")
    public String saveCafeUser(@RequestBody(required = true) CafeUser cafeUser);
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request);

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request);

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request);
    
    @PostMapping("/send-attachment")
    public ResponseEntity<String> sendEmailWithAttachment(
    		@RequestBody Map<String, String> filePath);

} 




