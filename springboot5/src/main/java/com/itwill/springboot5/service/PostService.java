package com.itwill.springboot5.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true) // 읽기 전용
    public List<PostListItemDto> read() {
        log.info("read()");
        // 영속석(persistence/repository) 계층의 메서드를 호출해서 엔터티들의 리스트를 가져옴.
        List<Post> list = postRepo.findAllByOrderByIdDesc();
        log.info("list.size = {}", list.size());

        // List<Post>를 List<PostListItemDto> 타입으로 변환.
        List<PostListItemDto> posts = list.stream()
            .map(PostListItemDto::fromEntity)
            .toList();

        return posts;
    }

    @Transactional
    public Long create(PostCreateDto dto) { 
    // 타입을 Post로 해도 되고 void로 해도 된다. insert 할때 생성된 primary key인 id의 값을 받기위해 Long으로 함
        log.info("create(dto={})", dto);

        // 영속성 계층의 메서드를 호출해서 DB insert 쿼리를 실행.
        Post entity = postRepo.save(dto.toEntity());
        log.info("entity = {}", entity);

        return entity.getId(); // DB에 insert된 레코드의 PK(id)를 리턴.
    }

    // @Transactional
    // public PostListItemDto create(PostCreateDto dto) {
    //     log.info("create(dto={})", dto);
    //     Post post = Post.builder()
    //                     .title(dto.getTitle())
    //                     .content(dto.getContent())
    //                     .author(dto.getAuthor())
    //                     .build();
        
    //     post = postRepo.save(post);
    //     log.info("post Save ={}", post);

    //     return PostListItemDto.fromEntity(post);
    // }


}
