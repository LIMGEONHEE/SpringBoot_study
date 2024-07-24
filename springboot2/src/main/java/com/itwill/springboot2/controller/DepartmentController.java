package com.itwill.springboot2.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot2.domain.Department;
import com.itwill.springboot2.service.DepartmentService;


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
  public String deptDetails(@PathVariable Integer id, Model model) {
    log.info("deptDetails(id={})", id);

    Department dept = deptSvc.departmentDetails(id);
    model.addAttribute("department", dept);

    return "department/details";
  }
}
