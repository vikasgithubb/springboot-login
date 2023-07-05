package com.restsimple.demo.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class RegistrationForm {

	private final String username;
	private final String password;
	private final String lastname;
	private final String email;
	private final String phone;

	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(username, passwordEncoder.encode(password), lastname, email, phone);
	}

}
