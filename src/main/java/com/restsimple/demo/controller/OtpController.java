package com.restsimple.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restsimple.demo.email.OtpService;

@Controller
public class OtpController {

	@Autowired
	public OtpService otpService;


	private Integer storedData;

	public void storeOtp(int inputData) {
		storedData = inputData;
	}

	@PostMapping("/submit-otp")
	public String validateotp(@RequestParam("otp") Integer otp) {

		if (storedData != null) {
			// Process the stored data
			if (storedData == otp) {
				return "login";}

			else {
				return "Please enter the correct otp";}
		} 
		else {
			return "Please enter the  otp";
		}

	}

}
