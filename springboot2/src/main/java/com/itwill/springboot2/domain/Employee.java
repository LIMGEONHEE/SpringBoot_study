package com.itwill.springboot2.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// ORM(Objet Relation Mapping) -> JPA(Java Persistence API) -> Hibernate

@Getter @ToString @EqualsAndHashCode
@Entity // 데이터베이스 테이블과 매핑하는 자바 객체
@NoArgsConstructor
@Table(name = "EMP") // 클래스 이름과 실제 테이블 이름이 다를 때. 
public class Employee {
    @Id // Primary Key
    @Column(name = "EMPNO") // 필드 이름과 실제 컬럼 이름이 다를 때.
    private Integer id;
    
    private String ename;
    
    private String job;
    
    @Column(name = "MGR")
    private Integer manager;
    
    private LocalDate hiredate;
    
    @Column(name = "SAL")
    private Double salary;
    
    @Column(name = "COMM")
    private Double commission;
    
    @ToString.Exclude // toSting 메서드의 출력 문자열에서 제외.
    @ManyToOne(fetch = FetchType.LAZY) // EAGAR: 관계된 엔티티를 즉시 로드하기 위해서. LAZY: 관련된 엔티티가 필요할 때 로드(권장사항). 사용할때 @ToString.Exclude를 권장
    @JoinColumn(name = "DEPTNO") // EMP 테이블에서 DEPT 테이블과 join하는 컬럼 이름.
    private Department department; // 타입이 컬럼으로 사용하는 경우 무한 루프로 빠질 수 있기 때문에 @ToString.Exclude를 사용해야한다.
}
