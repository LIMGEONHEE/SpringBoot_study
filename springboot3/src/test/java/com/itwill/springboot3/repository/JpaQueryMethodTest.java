package com.itwill.springboot3.repository;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot3.domain.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class JpaQueryMethodTest {

    @Autowired
    private EmployeeRepository empRepo;
    
    @Test
    public void test() {
      log.info("test()");
       List<Employee> list;
   // list =  empRepo.findByDepartmentId(30);
   // list = empRepo.findByFirstName("Steven");
   // list = empRepo.findByFirstNameContaining("te");
   // list = empRepo.findByFirstNameLike("%te%");
   // list = empRepo.findByFirstNameContainingIgnoreCase("Te");
   // list = empRepo.findByFirstNameContainingIgnoreCaseOrderByFirstNameDesc("Te");
   // list = empRepo.findBySalaryGreaterThan(10_000);
   // list = empRepo.findBySalaryLessThan(10_000);
   // list = empRepo.findBySalaryBetween(10000, 12000);
   // list = empRepo.findByHireDateBefore(LocalDate.of(2003,1,1));
   // list = empRepo.findByHireDateAfter(LocalDate.parse("2007-12-31"));
   // list = empRepo.findByHireDateBetween(LocalDate.parse("2006-12-31"),LocalDate.parse("2007-12-31"));
   // list = empRepo.findByDepartmentDepartmentName("IT");
   // list = empRepo.findByDepartmentLocationCity("Seattle");
   // list = empRepo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("te", "te");
   // list = empRepo.findByName("TE", "te");
   // list = empRepo.findByName2("te");
   // list = empRepo.findByDeptName("IT");
   // list = empRepo.findByCity("Seattle");
   list = empRepo.findByCountry("Canada");

       list.forEach(System.out::println);
    }
}
