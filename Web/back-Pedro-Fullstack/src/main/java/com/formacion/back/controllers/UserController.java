package com.formacion.back.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.formacion.back.entities.Movie1;
import com.formacion.back.entities.User;
import com.formacion.back.entities.User.ROLE;
import com.formacion.back.repository.MovieRepository;
import com.formacion.back.repository.UserRepository;
import com.formacion.back.service.MovieService;
import com.formacion.back.service.UsersService;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/user")
public class UserController{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UsersService userService;

	
	@PostMapping("/login")
	public @ResponseBody User login(@RequestParam String username, @RequestParam String password) {
		if(userService.login(username,password) == null)
			return null;
			return userService.login(username,password);
	}
	
	
	@PostMapping("/signup")
	public @ResponseBody String signup(@RequestParam String username, @RequestParam String name,
			@RequestParam String surname, @RequestParam String email, @RequestParam String password,
			@RequestParam String passwordRepeated) {
		
		if(username.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty() ||password.isEmpty())
			return "Some fields are blank, please fill them";
		if(!password.equals(passwordRepeated))
			return "The passwords must be equals";
		if(userRepository.findByEmail(email) != null)
			return "This email is used by another user";
		if(userRepository.findByUsername(username) != null)
			return "There is already a user with that username";
		
		User u = new User();
		u.setUsername(username);
		u.setName(name);
		u.setSurname(surname);
		u.setEmail(email);
		u.setPassword(password);
		u.setPasswordRepeated(passwordRepeated);
		u.setRole(ROLE.USER);
		
		
		userRepository.save(u);
		return "success";
	}

}
