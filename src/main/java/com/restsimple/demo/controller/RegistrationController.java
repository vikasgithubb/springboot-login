package com.restsimple.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.restsimple.demo.entity.RegistrationForm;
import com.restsimple.demo.entity.User;
import com.restsimple.demo.repository.UserRepository;

@Controller
public class RegistrationController {
     
	
	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;

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
	public ModelAndView addUser(@RequestParam("email") String email, RegistrationForm form) {
		ModelAndView mv = new ModelAndView("success");
		List<User> list = userRepo.findByEMAIL(email);

		if (list.size() != 0) {
			mv.addObject("message", "Oops!  There is already a user registered with the email provided.");

		} else {
			userRepo.save(form.toUser(passwordEncoder));
			mv.addObject("message", "User has been successfully registered.");
		}

		return mv;
	}

	@GetMapping("/dummy")
	public String dummy() {
		return "dummy";
	}

	@PostMapping("/login")
	public String login_user(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpSession session, ModelMap modelMap) {

		User auser = userRepo.findByUsernamePassword(username, password);

		if (auser != null) {
			String uname = auser.getUsername();
			String upass = auser.getPassword();

			if (username.equalsIgnoreCase(uname) && password.equalsIgnoreCase(upass)) {
				session.setAttribute("username", username);
				return "dummy";
			} else {
				modelMap.put("error", "Invalid Account");
				return "login";
			}
		} else {
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
