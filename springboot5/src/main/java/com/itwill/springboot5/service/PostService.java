package com.itwill.springboot5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.dto.PostCreateDto;
import com.itwill.springboot5.dto.PostListItemDto;
import com.itwill.springboot5.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    
    private final PostRepository postRepo;

    public List<PostListItemDto> read() {
        log.info("read()");
        // 영속석(persistence/repository) 계층의 메서드를 호출해서 엔터티들의 리스트를 가져옴.
        List<Post> list = postRepo.findAll();
        log.info("list.size = {}", list.size());

        // List<Post>를 List<PostListItemDto> 타입으로 변환.
        List<PostListItemDto> posts = list.stream()
            .map(PostListItemDto::fromEntity)
            .toList();

        return posts;
    }

    public PostListItemDto create(PostCreateDto dto) {
        log.info("create(dto={})", dto);
        Post post = Post.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .author(dto.getAuthor())
                        .build();
        
        post = postRepo.save(post);
        log.info("post Save ={}", post);

        return PostListItemDto.fromEntity(post);
    }


}
