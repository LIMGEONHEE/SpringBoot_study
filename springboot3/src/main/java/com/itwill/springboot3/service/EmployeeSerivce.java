package com.itwill.springboot3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.dto.EmployeeListItemDto;
import com.itwill.springboot3.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeSerivce {

	private final EmployeeRepository empRepo;
	
	public List<EmployeeListItemDto> read() {
		log.info("read()");
		
		List<Employee> list = empRepo.findAll();
		log.info("emp list size = {}", list.size());

		return list.stream() // stream은 list 원소의 통로
				.map(EmployeeListItemDto::fromEntity) // (x) -> EmployeeListItemDto.fromEntity(x), 맵핑
				.toList(); // 가져온걸 리스트에 담음.
	}
	
	public Employee employeeDetails(Integer id) {
		log.info("employeeDetails(id={})", id);
		
		return empRepo.findById(id).orElseThrow();
	}
}
