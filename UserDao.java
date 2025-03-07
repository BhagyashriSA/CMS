package com.in.cafe.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.in.cafe.pojo.User;

public interface UserDao  extends JpaRepository<User, Integer>{

	User findByEmail(@Param("email") String Email);
	
	List<User> findAll();
	
	Optional<User> findById(Integer id);
}
