package com.itwill.springboot5.service;

import org.springframework.stereotype.Service;

import com.itwill.springboot5.domain.Comment;
import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.dto.CommentRegisterDto;
import com.itwill.springboot5.repository.CommentRepository;
import com.itwill.springboot5.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자에 의한 의존성 주입을 하기 위한 애너테이션
@Service
public class CommentService {
    
    private final CommentRepository commentRepo;
    private final PostRepository postRepo;

    public Comment creat(CommentRegisterDto dto){
        log.info("create(dto={})", dto);

        // 댓글이 달릴 포스트 엔터티를 검색:
        Post post = postRepo.findById(dto.getPostId()).orElseThrow();

        // DB테이블에 저장할 Comment 타입의 엔터티를 생성:
        Comment entity = Comment.builder()
                            .post(post)
                            .ctext(dto.getCtext())
                            .writer(dto.getWriter())
                            .build();

        // DB에 저장(insert 쿼리 실행)
        commentRepo.save(entity);

        return entity;        
    }
}
