package com.itwill.springboot5.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot5.domain.Comment;

import com.itwill.springboot5.repository.CommentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CommentRepositoyTest {
    // CommentRepositoy의 CRUD 기능을 단위 테스트.
    @Autowired
    private CommentRepository comRepo;

    // @Test
    public void testFindAll() {
        List<Comment> list = comRepo.findAll();
        assertThat(list.size()).isEqualTo(0);

        list.forEach((x)-> {System.out.println(x);});
    }

    // @Test 
    public void testUpdate() {
        Comment entity = comRepo.findById(1L).orElseThrow(null);
        log.info("findById = {}", entity);

        entity.update("업데이트1");
        log.info("update = {}", entity);

        entity = comRepo.save(entity);
        log.info("save = {]", entity);
    }

    // @Test
    public void testDelete() {
        comRepo.deleteById(1L);
    }

    

}
