package com.project.fin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.fin.global.GlobalData;
import com.project.fin.model.Role;
import com.project.fin.model.User;
import com.project.fin.repository.RoleRepository;
import com.project.fin.repository.UserRepository;

@Controller
public class LoginController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();                                               // to clear the cart
		return "login";
	}
	@GetMapping("/register")
	public String registerGet() {
		return "register";
	}
	
	@PostMapping("/register")                            // user gets automatically logged in (autoLogedin)
	public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request)throws ServletException{
		String password=user.getPassword();
		user.setPassword(password);
		
		List<Role> roles =new ArrayList<>();
		roles.add(roleRepository.findById(2).get());    //add Role as 2.USER_ROLE
		user.setRoles(roles);
		userRepository.save(user);
		
		request.login(user.getEmail(), password); 		 // it is used for auto-login
		
		return "redirect:/";
		
	}
}
