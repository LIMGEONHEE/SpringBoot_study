package com.itwill.springboot5.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.springboot5.domain.Post;

// CRUD + Paging/Sorting
public interface PostRepository extends JpaRepository<Post, Long>, PostQuerydsl {
    List<Post> findAllByOrderByIdDesc(); // Id 내림차순 정렬.
    
    // JPA Query Method 작성:
    // 제목에 포함된 문자열 대소문자 구분없이 검색하기:
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable); // Containing: 문자열이 포함된, IgnoreCase: 대소문자 구분없이

    // 내용에 포함된 문자열 대소문자 구분없이 검색하기:
    Page<Post> findByContentContainingIgnoreCase(String keyword, Pageable pageable);

    // 작성자에 포함된 문자열 대소문자 구분없이 검색하기:
    Page<Post> findByAuthorContainingIgnoreCase(String keyword, Pageable pageable);

    // JPQL(Java Persistence Query Language): 객체지향 쿼리 언어.
    // 제목 또는 내용에 포함된 문자열 대소문자 구분없이 검색하기.
    // findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(args)
    // findByTitleContainingOrContentContainingAllIgnoreCase(args)
    @Query("select p from Post p "
            + "where upper(p.title) like upper('%' || :keyword || '%') "
            + "or upper(p.content) like upper('%' || :keyword || '%') ") // 테이블에 있는 것을 그대로 입력하는 것이 아니라 엔터티에 있는 이름과 필드이름을 가져와야한다.
    Page<Post> findByTitleOrContent(@Param("keyword") String keyword, Pageable pageable);
}
