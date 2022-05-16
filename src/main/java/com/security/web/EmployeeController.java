package com.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.security.services.EmployeeService;

@Controller
@RequestMapping("/Employee")
public class EmployeeController {

	@Autowired
	private EmployeeService empService; 
	
	@PostMapping("/add")
	public String addEmployee() {
		return "add_employee";
	}
}
