package com.itwill.springboot5.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.dto.PostCreateDto;
import com.itwill.springboot5.dto.PostListItemDto;
import com.itwill.springboot5.dto.PostUpdateDto;
import com.itwill.springboot5.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    
    private final PostRepository postRepo;

    @Transactional(readOnly = true) // 읽기 전용
    public Page<PostListItemDto> read(int pageNo, Sort sort) {
        log.info("read(page={}, sort={})",pageNo, sort);

        // pageable 객체 생성
        Pageable pageable = PageRequest.of(pageNo, 10, sort); // pageSize= 한페이지에 몇개를 보여줄 건지.

        // 영속석(persistence/repository) 계층의 메서드를 호출해서 엔터티들의 리스트를 가져옴.
        Page<Post> list = postRepo.findAll(pageable);
        log.info("page.totalPages", list.getTotalPages()); // 전체 페이지 개수
        log.info("list.size = {}", list.getNumber()); // 현재 페이지 번호
        log.info("page.hasPrevious", list.hasPrevious()); // 이전 페이지가 있는 지 여부
        log.info("page.hasNext", list.hasNext()); // 다음 페이지가 있는 지 여부

        // Page<Post>를 Page<PostListItemDto> 타입으로 변환.
        // (x) -> PostListItemDto.fromEntity(x)
        Page<PostListItemDto> posts = list.map(PostListItemDto::fromEntity); 
        // List<>는 stream으로 보내야하지만 Page<>는 바로 map으로 시작해돈된다.

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

    @Transactional(readOnly = true) // 읽기 전용
    public Post readById(Long id) {
        log.info("readById(id={})", id);

        Post entity =  postRepo.findById(id).orElseThrow();
        log.info("entity = {}", entity);

        return entity;
    }

    public void deleteById(Long id) {
        log.info("deleteById(id={})", id);
        
        postRepo.deleteById(id);
    }

    public void update(PostUpdateDto dto) {
        log.info("update(dto={})", dto);

        Post entity = postRepo.findById(dto.getId()).orElseThrow();

        entity.update(dto.getTitle(), dto.getContent());
        log.info("update 호출 = {}", entity);

        entity = postRepo.save(entity);
        log.info("save 호출 = {}", entity);
    }
    
}
