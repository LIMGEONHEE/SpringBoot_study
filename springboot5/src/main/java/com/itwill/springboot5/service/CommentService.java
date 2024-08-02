package com.itwill.springboot5.service;

import org.springframework.stereotype.Service;

import com.itwill.springboot5.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자에 의한 의존성 주입을 하기 위한 애너테이션
@Service
public class CommentService {
    
    private final CommentRepository commentRepo;
}
