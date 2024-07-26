package com.itwill.springboot4.domain;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary Key 자동 생성 만들어줌.(generated as identity 같은 역할)
    private String id;

    @NaturalId // unique 제약 조건
    @Basic(optional = false) // not null 제약 조건 
    private String username;

    @Basic(optional = false)
    private String password;

    @Enumerated(EnumType.STRING) // check (gender between 0 and 2), ORDINAL: number String: varchar2
    private Gender gender;

    @Column(length = 1000)
    private String memo;
    
    @Embedded // @Embeddable로 선언된 객체를 포함. 생략 가능.
    private Address address;
    
}
