package com.itwill.springboot5.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.domain.QPost;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostQuerydslImpl extends QuerydslRepositorySupport 
    implements PostQuerydsl{
    

    public PostQuerydslImpl() {
        super(Post.class);
    }

    @Override
    public Post searchById(Long id) {
        log.info("searchById(id={})", id);

        QPost post = QPost.post;
        JPQLQuery<Post> query = from(post); // select p from Post p
        query.where(post.id.eq(id)); // query + where id = ?
        Post entity = query.fetchOne();
       
        return entity;
    } // 단점: 사용하기 어렵다. 장점: 매우 다양한 쿼리를 사용할 수 있다, 
    
}
