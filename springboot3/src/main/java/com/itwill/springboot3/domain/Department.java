package com.itwill.springboot3.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor 
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 생성자를 Private로 감추어서 외부에서 생성자를 호출할 수 없게 한다. 
@Builder
@Getter @ToString @EqualsAndHashCode
@Entity @Table(name = "DEPARTMENTS")
public class Department {

    @Id @Column(name = "DEPARTMENT_ID")
    private Integer id;

    private String departmentName;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY) // 한명의 매니저가 한명만 맡기 때문에.
    @JoinColumn(name = "MANAGER_ID")
    private Employee manager;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

}
