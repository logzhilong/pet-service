package com.momoplan.pet.framework.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("/login.html")
	public String login(Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("wlcome to pet manager login......");
		return "login";
	}
	
	@RequestMapping("/logout.html")
	public String logout(Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("wlcome to pet manager logout......");
		return "login";
	}
	
}
