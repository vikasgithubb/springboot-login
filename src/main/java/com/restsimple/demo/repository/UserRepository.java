package com.restsimple.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.restsimple.demo.entity.User;



public interface UserRepository extends CrudRepository<User, Integer> {
	
	@Query("from User where email=?1")
	public List<User> findByEMAIL(String email);
	
	@Query("from User where email=?1 and password=?2")
	public User findByUsernamePassword(String username,String password);

	User findByUsername(String username);


}
