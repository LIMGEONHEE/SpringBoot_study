package com.itwill.springboot5.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.dto.PostSearchRequestDto;
import com.itwill.springboot5.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PostQuerydslTest {
    
    @Autowired private PostRepository postRepo;

//    @Test
    public void testSearchById() {
        Post entity = postRepo.searchById(2L);
        log.info("entity = {}", entity);
    }

    @Test
    public void test() {
        List<Post> result = null;
        // result = postRepo.searchByTitle("Dumm");
        // result = postRepo.searchByContent("dumm");
        // result = postRepo.searchByTitleOrContent("jpa");

        // LocalDateTime from = LocalDateTime.of(2024, 7, 30, 11, 30, 45);
        // LocalDateTime to = LocalDateTime.of(2024, 7, 30, 11, 30, 45);
        // result = postRepo.searchByModifiedTime(from, to);

        // result = postRepo.searchByAuthorAndTitle("admin", "test");

        // PostSearchRequestDto dto = new PostSearchRequestDto();
        // dto.setCategory("a");
        // dto.setKeyword("ES");
        // result = postRepo.searchByCategory(dto);

        String[] keywords = "jpa 테스".split(" "); // {"dum", "title"};
        // result = postRepo.searchByKeywords(keywords);

        Pageable pageable = PageRequest.of(1, 5, Sort.by("id").descending());
        Page<Post> page = postRepo.searchByKeywords(keywords, pageable);
        page.forEach(System.out::println);    
        // result.forEach(System.out::println);
        // result.forEach((x)->{System.out.println(x);});
        
        // 5개가 안되면 오류가 뜬다.
        // for (int i = 0; i < 5; i++) {
        //     log.info("{}", result.get(i));
        // }
    }

} 
