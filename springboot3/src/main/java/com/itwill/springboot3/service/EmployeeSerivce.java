package com.itwill.springboot3.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.itwill.springboot3.domain.Employee; // 페이징 처리가 되어 있는 객체
import com.itwill.springboot3.dto.EmployeeListItemDto;
import com.itwill.springboot3.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeSerivce {

	private final EmployeeRepository empRepo;
	
	public Page<EmployeeListItemDto> read(int pageNo, Sort sort) { // org.springframework.data.domain.Sort으로 선택. (페이지 넘버, 정렬 기준)
		log.info("read(pageNo={}, sort={})", pageNo, sort);
		
		// Pageable 객체 생성: PageRequest.of(페이지번호, 한 페이지의 아이템 개수, 정렬 기준)
		Pageable pageable = PageRequest.of(pageNo, 10, sort); 

		// findAll(Pageable pageable) 메서드를 호출.
        Page<Employee> page = empRepo.findAll(pageable);
        log.info("hasPrevious = {}", page.hasPrevious()); // 이전 페이지가 있는 지 여부
        log.info("hasNext = {}", page.hasNext()); // 다음 페이지가 있는 지 여부
        log.info("getTotalPages = {}", page.getTotalPages()); // 전체 페이지 개수
        
        // Page<Employee> 타입을 Page<EmployeeListItemDto> 타입으로 변환해서 리턴.
        // (x) -> EmployeeListItemDto.fromEntity(x)
        return page.map(EmployeeListItemDto::fromEntity);


		// 페이징처리 하기 이전 코드
		// List<Employee> list = empRepo.findAll();
		// log.info("emp list size = {}", list.size());

		// return list.stream() // stream은 list 원소의 통로
		// 		.map(EmployeeListItemDto::fromEntity) // (x) -> EmployeeListItemDto.fromEntity(x), 맵핑
		// 		.toList(); // 가져온걸 리스트에 담음.
	}
	
	public Employee read(Integer id) {
		log.info("employeeDetails(id={})", id);
		
		return empRepo.findById(id).orElseThrow();
	}
}
