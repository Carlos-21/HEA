package com.udemy.backendninja.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.udemy.backendninja.model.User;

@Controller
public class HomeController {

	@GetMapping("/login")
	public String showLogin(Model model, @RequestParam(name = "error", required = false) String error) {
		
		model.addAttribute("Usuario", new User());
		model.addAttribute("error", error);
		return "view/login";
	}
	
	@GetMapping({"/afterlogin","/"})
	public String afterlogin(Model model, HttpServletRequest request, Authentication authentication) {
		String username=authentication.getName();		
		model.addAttribute("nombreUsuario",username);
		/*User us= new User();
		us.setUsername(username);
		request.getSession().setAttribute("usuario", us);
		System.out.println(us.getUsername());
		model.addAttribute("usuario", us);
		*/
		return "view/AfterLogin";
	}
	
	
	
	
}
