package com.restsimple.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restsimple.demo.email.OtpService;
import com.restsimple.demo.entity.RegistrationForm;

@Controller
public class OtpController {

	@Autowired
	public OtpService otpService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Integer storedData;

	public void storeOtp(int inputData) {
		storedData = inputData;
	}

	@PostMapping("/submit-otp")
	public String validateotp(@RequestParam("otp") Integer otp) {

		if (storedData != null) {
			// Process the stored data
			if (storedData == otp)
				logger.info("OTP Stored in class : " + storedData + "otpfromuser" + otp);
			logger.info("OTP Stored in class : " + storedData);
			return "login";
		} else {

			return "Please enter the correct otp";
		}

	}

	
}
