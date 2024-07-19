package com.itwill.springboot2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot2.domain.Dept;

public interface DeptRepository extends JpaRepository<Dept, Integer> {
    
}
