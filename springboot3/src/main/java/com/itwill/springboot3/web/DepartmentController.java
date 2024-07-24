package com.itwill.springboot3.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot3.domain.Department;
import com.itwill.springboot3.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/department")
public class DepartmentController {

	private final DepartmentService deptSvc;
	
	  @GetMapping("/list")
	  public void list(Model model){
	    log.info("list()");

	    List<Department> list = deptSvc.read();

	    model.addAttribute("department", list);
	  }

	  @GetMapping("/details/{id}")
	  public String deptDetails(@PathVariable(name = "id") Integer id, Model model) {
	    log.info("deptDetails(id={})", id);

	    Department dept = deptSvc.departmentDetails(id);
	    model.addAttribute("department", dept);

	    return "department/details";
	  }
}