package com.formacion.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacion.back.entities.User;
import com.formacion.back.repository.UserRepository;
@Service
public class UsersService {

	@Autowired
	private UserRepository userRepository;

	public User login(String username, String password) {
		return userRepository.login(username, password);
	}
}