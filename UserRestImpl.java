package com.in.cafe.restimpl;

import java.io.File;
import java.io.IOException;
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
import com.in.cafe.pojo.Auther;
import com.in.cafe.pojo.Book;
import com.in.cafe.pojo.CafeUser;
import com.in.cafe.pojo.Category;
import com.in.cafe.pojo.Course;
import com.in.cafe.pojo.Employee;
import com.in.cafe.pojo.Manager;
import com.in.cafe.pojo.Person;
import com.in.cafe.pojo.Product;
import com.in.cafe.pojo.Student;
import com.in.cafe.pojo.User;
import com.in.cafe.rest.UserRest;
import com.in.cafe.service.Personservice;
import com.in.cafe.service.UserService;
import com.in.cafe.util.CafeUtil;

import jakarta.mail.MessagingException;



@RestController
public class UserRestImpl implements UserRest {
	
	@Autowired
    UserService userservice;
	
	@Autowired
	Personservice personService;
	
    
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
	    	userservice.createUser("riya", "riya@gmail.com");
//	    	userservice.sendEmail("bhagyashrisay@gmail.com", "Snding Mail Demo Code", "Mail sent successfully");
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
	public String sendEmail(Map<String, String> requestBody) {
		// TODO Auto-generated method stub
	    String to = requestBody.get("to");
	    String subject = requestBody.get("subject");
	    String body = requestBody.get("body");
		userservice.sendEmail(to, subject, body);
        return "Email Sent Successfully!";
	}

	@Override
	public String sendMailById() {
		// TODO Auto-generated method stub
		userservice.sendEmail("bhagyashrisay@gmail.com", "Snding Mail Demo Code through rest api", "Mail sent successfully to Bhagyashri Sayankar");
        return "Email Sent Successfully! 0000";
	}

	@Override
	public ResponseEntity<String> createUser(String name, String email) {
		// TODO Auto-generated method stub
	     userservice.createUser(name, email);
	     System.out.println("name and email is " + name + " " + email);
	     return null;
	}

	@Override
	public String show() {
		// TODO Auto-generated method stub
		return "Hello welcome to code management system";
	}
	
	
    //correct code for @mappedsuperclass demo
	@Override
	public ResponseEntity<String> takeInput(Employee employee) {
		// TODO Auto-generated method stub
		 userservice.createUser(employee.getName(), employee.getEmail());
		System.out.println("Name is " +employee.getName()+ " " + employee.getEmail());
		return ResponseEntity.ok("BaseEntoty mapped succesfully");
	}

	@Override
	public ResponseEntity<String> savePersonData(Person person) {
		// TODO Auto-generated method stub
		try {
			personService.savePersonData(person, person.getPassport());
		} 
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return ResponseEntity.ok("Data inserted successfully");
	}

	@Override
	public ResponseEntity<Optional<Person>> getPersonById(Long id) {
		// TODO Auto-generated method stub
	    Optional<Person> person = personService.getPersonById(id);
	    return ResponseEntity.ok(person);
	}

	@Override
	public String saveManager(Manager manager) {
		// TODO Auto-generated method stub
		try {
			return userservice.saveManager(manager);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return "Manager saved successfully";
	}

	@Override
	public ResponseEntity<Auther> saveAuther(Auther auther) {
		// TODO Auto-generated method stub
		try {
			Auther savedAuther = userservice.saveAuther(auther);
	        return new ResponseEntity<>(savedAuther, HttpStatus.CREATED);
		} 
		catch(Exception ex) {
			ex.printStackTrace();
		}
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Book> saveBook(Book book) {
		// TODO Auto-generated method stub
		try {
			Book saveBook = userservice.saveBook(book);
		    return new ResponseEntity<>(saveBook, HttpStatus.CREATED);
		} 
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Auther>> getAllAuther() {
		// TODO Auto-generated method stub
		try {
			List<Auther> list = userservice.getAllAuther();
		    return new ResponseEntity<>(list, HttpStatus.CREATED);
		} 
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Book>> getAllBook() {
		// TODO Auto-generated method stub
		try {
			List<Book> list = userservice.getAllBook();
		    return new ResponseEntity<>(list, HttpStatus.CREATED);
		} 
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public String saveCafeUser(CafeUser cafeUser) {
		// TODO Auto-generated method stub
		try {
			userservice.saveCafeUser(cafeUser);
		} catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "CafeUsers save successfullly";
	}

	@Override
	public ResponseEntity<String> forgotPassword(Map<String, String> request) {
		// TODO Auto-generated method stub
        String email = request.get("email");
        try {
        	userservice.generatePasswordResetToken(email);
            return ResponseEntity.ok("Password reset email sent.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

	}

	@Override
	public ResponseEntity<String> resetPassword(Map<String, String> request) {
		// TODO Auto-generated method stub
        String token = request.get("token");
        String newPassword = request.get("password");
        try {
        	userservice.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password successfully reset.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

	@Override
	public ResponseEntity<String> changePassword(Map<String, String> request) {
		// TODO Auto-generated method stub
        try {
        	userservice.changePassword(request);
            return ResponseEntity.ok("Password successfully changed.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
	}

	@Override
	public ResponseEntity<String> sendEmailWithAttachment(Map<String, String> filePath) {
		// TODO Auto-generated method stub
        File file = new File(filePath.get("filepath"));
        try {
        	userservice.sendEmailWithAttachment(file);
        	 return ResponseEntity.ok("Email sent successfully!");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
	}

	@Override
	public ResponseEntity<String> createProduct(Product product) {
		// TODO Auto-generated method stub
	      try {
	        	userservice.createProduct(product);
	        	 return ResponseEntity.ok("Product created successfully!");
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Error in creating product: " + e.getMessage());
	        }
	}

	@Override
	public ResponseEntity<String> createCategory(Category category) {
		// TODO Auto-generated method stub
	      try {
	        	userservice.createCategory(category);
	        	 return ResponseEntity.ok("Category created successfully!");
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Error in creating Category: " + e.getMessage());
	        }
	}

	@Override
	public ResponseEntity<List<Product>> getAllProduct() {
		// TODO Auto-generated method stub
		try {
			List<Product> listOfProduct = userservice.getAllProduct();
			return ResponseEntity.ok(listOfProduct);
		} catch (Exception e) {
	        // Return bad request with a custom error message
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Collections.emptyList());  
		}
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategory() {
		// TODO Auto-generated method stub
		try {
			List<Category> listOfCategory = userservice.getAllCategory();
			return ResponseEntity.ok(listOfCategory);
		} catch (Exception e) {
	        // Return bad request with a custom error message
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Collections.emptyList());  
		}
	}

	@Override
	public ResponseEntity<Optional<Product>> getProductById(Long id) {
		// TODO Auto-generated method stub
		try {
			Optional<Product> product = userservice.getProductById(id);
			return ResponseEntity.ok(product);
		} catch (Exception e) {
	        // Return bad request with a custom error message
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Optional.empty());  
		}
	}

	@Override
	public ResponseEntity<Optional<Category>> getCategoryById(Long id) {
		// TODO Auto-generated method stub
		try {
			Optional<Category> category = userservice.getCategoryById(id);
			return ResponseEntity.ok(category);
		} catch (Exception e) {
	        // Return bad request with a custom error message
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Optional.empty());  
		}
	}
	
    public Product getProById(@PathVariable Long id) {
        return userservice.getProById(id);
    }

	@Override
	public Category getCatgryById(Long id) {
		// TODO Auto-generated method stub
		return userservice.getCatgryById(id);
	}

	@Override
	public Student createStudent(Student student) {
		// TODO Auto-generated method stub
		return userservice.createStudent(student);
	}

	@Override
	public Course createCourse(Course course) {
		// TODO Auto-generated method stub
		return userservice.createCourse(course);
	}

	@Override
	public ResponseEntity<List<Student>> getAllStudent() {
		// TODO Auto-generated method stub
		try {
			List<Student> listOfStudent = userservice.getAllStudent();
			return ResponseEntity.ok(listOfStudent);
		} catch (Exception e) {
	        // Return bad request with a custom error message
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Collections.emptyList());  
		}
	}

	@Override
	public ResponseEntity<List<Course>> getAllCourse() {
		// TODO Auto-generated method stub
		try {
			List<Course> listOfCourse = userservice.getAllCourse();
			return ResponseEntity.ok(listOfCourse);
		} catch (Exception e) {
	        // Return bad request with a custom error message
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Collections.emptyList());  
		}
	}

	@Override
	public Student getStudentById(Long id) {
		// TODO Auto-generated method stub
		return userservice.getStudentById(id);
	}

	@Override
	public Course getCourseById(Long id) {
		// TODO Auto-generated method stub
		return userservice.getCourseById(id);
	}
}
	

//	@Override
//	public ResponseEntity<String> checkToken() {
//		// TODO Auto-generated method stub
//		Boolean statusOfToken = userservice.checkToken();
//		if(statusOfToken) {
//			return ResponseEntity.ok("Token is available");
//		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body("Token not found");
//	}


