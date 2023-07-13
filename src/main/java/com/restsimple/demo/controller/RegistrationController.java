package com.restsimple.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restsimple.demo.email.MyEmailService;
import com.restsimple.demo.email.OtpService;
import com.restsimple.demo.entity.RegistrationForm;
import com.restsimple.demo.entity.User;
import com.restsimple.demo.repository.UserRepository;

@Controller
public class RegistrationController {

	@Autowired
	public OtpService otpService;

	@Autowired
	public MyEmailService myEmailService;
	
	@Autowired
	public OtpController otpController;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@RequestMapping("/")
	public String home() {
		return "home";
	}

	@RequestMapping("/signup")
	public String getSignup() {
		return "signup";
	}

	@RequestMapping("/login")
	public String getLogin() {
		return "login";
	}

	@PostMapping("/addUser")
	public String addUser(@RequestParam("email") String email, RegistrationForm form) {
		List<User> list = userRepo.findByEMAIL(email);

		if (list.size() != 0) {

			logger.info(list.toString());
		} else {

			int otp = otpService.generateOTP(email);
			logger.info("OTP : " + otp);
			myEmailService.sendOtpMessage(email, "OTP -SpringBoot", String.valueOf(otp));
			userRepo.save(form.toUser(passwordEncoder));
			otpController.storeOtp(otp);

		}

		return "otppage";
	}

	@GetMapping("/dummy")
	public String dummy() {
		return "dummy";
	}

	@PostMapping("/login")
	public String login_user(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpSession session, ModelMap modelMap) {

		User auser = userRepo.findByUsername(username);
		logger.info(auser.toString());

		if (auser != null) {
			String uname = auser.getEmail();
			String upass = auser.getPassword();

			if (username.equalsIgnoreCase(uname) && bCryptPasswordEncoder.matches(password, upass)) {
				logger.info("dataemail : " + uname + "  " + "enemail" + username);
				session.setAttribute("username", username);
				return "dummy";
			} else {
				modelMap.put("error", "Invalid Account");
				return "login";
			}
		}

		else {
			modelMap.put("error", "Invalid Account");
			return "login";
		}

	}

	@GetMapping(value = "/logout")
	public String logout_user(HttpSession session) {
		session.removeAttribute("username");
		session.invalidate();
		return "redirect:/login";
	}

}
