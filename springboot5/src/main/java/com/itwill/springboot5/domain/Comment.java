package com.itwill.springboot5.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Builder
@Getter @ToString(callSuper = true) // 부모 클래스도 toString으로 해준다.
@EqualsAndHashCode(callSuper = true)
@Entity @Table(name = "COMMENTS")
public class Comment extends BaseTimeEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude // toString 메서드를 만들 때 제외시킴.
    @ManyToOne(fetch = FetchType.LAZY) // 하나의 post에 여러 댓글들을 달 수 있다. 기준이 comments이다.
    @JoinColumn(name = "POST_ID") // FK 제약조건이 있는 테이블의 컬럼 이름.
    private Post post;

    @Basic(optional = false)
    private String ctext;

    @Basic(optional = false)
    private String writer; 

    public Comment update(String ctext){
        this.ctext = ctext;
        return this;
    }

}
