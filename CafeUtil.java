package com.in.cafe.util;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

public class CafeUtil {
	
	private CafeUtil() {
		
	}
	public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
//		return new ResponseEntity<String>(body "{\"message\":\""+responseMessage+""\"}", httpStatus);
		return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", httpStatus);
	}

}
