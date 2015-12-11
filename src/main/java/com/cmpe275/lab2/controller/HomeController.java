package com.cmpe275.lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	
	/*
	 
	 Welcome page
	 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String startApp( Model model) 
	{
		System.out.println("Started Lab2");
		return "index";
	} 
	
	
}
