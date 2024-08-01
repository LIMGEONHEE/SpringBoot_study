package com.itwill.springboot5.repository;

import org.attoparser.dom.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    
}
