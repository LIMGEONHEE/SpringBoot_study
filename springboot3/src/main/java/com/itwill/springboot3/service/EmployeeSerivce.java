package com.itwill.springboot3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeSerivce {

	private final EmployeeRepository empRepo;
	
	public List<Employee> read() {
		log.info("read()");
		
		return empRepo.findAll();
	}
	
	public Employee employeeDetails(Integer id) {
		log.info("employeeDetails(id={})", id);
		
		return empRepo.findById(id).orElseThrow();
	}
}
