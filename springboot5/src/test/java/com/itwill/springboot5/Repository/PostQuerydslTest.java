package com.itwill.springboot5.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PostQuerydslTest {
    
    @Autowired private PostRepository postRepo;

    @Test
    public void testSearchById() {
        Post entity = postRepo.searchById(2L);
        log.info("entity = {}", entity);
    }

} 
