package com.itwill.springboot3.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.service.EmployeeSerivce;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	private final EmployeeSerivce empSvc;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list()");
		
		 List<Employee> list = empSvc.read();
		 model.addAttribute("employees", list);
		
	}
	
	@GetMapping("/details/{id}")
	public String empDetails(@PathVariable(name = "id") Integer id, Model model) {
		log.info("empDetails()");
		
		Employee emp = empSvc.employeeDetails(id);
		model.addAttribute("employee", emp);
		
		return "employee/details";
		
	}

}
