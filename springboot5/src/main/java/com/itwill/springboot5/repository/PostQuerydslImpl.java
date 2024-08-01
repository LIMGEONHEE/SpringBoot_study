package com.itwill.springboot5.repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.domain.QPost;
import com.itwill.springboot5.dto.PostSearchRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostQuerydslImpl extends QuerydslRepositorySupport implements PostQuerydsl{
    // extends QuerydslRepositorySupport를 상속 받을때 주의할 점은 기본 생성자가 없어야 한다.
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

    @Override
    public List<Post> searchByTitle(String keyword) {
        log.info("searchByTitle(keyword={})", keyword);

        QPost post = QPost.post;
        JPQLQuery<Post> query = from(post); // select * from Post
        query.where(post.title.containsIgnoreCase(keyword)); // where 절
        query.orderBy(post.id.desc()); // order by 절
        // query.where(post.title.containsIgnoreCase(keyword)).orderBy(post.id.desc()); 으로 써도 된다.
        // JPQLQuery<Post> query = from(post).query.where(post.title.containsIgnoreCase(keyword)).orderBy(post.id.desc()); 도 가능

        List<Post> result = query.fetch();

        return result;
    }

    @Override
    public List<Post> searchByContent(String keyword) {
        log.info("searchByContent(keyword={})", keyword);

        QPost post = QPost.post;
        JPQLQuery<Post> query = from(post);
        query.where(post.content.containsIgnoreCase(keyword));
        query.orderBy(post.id.desc());
        List<Post> result = query.fetch();

        return result;
    }

    @Override
    public List<Post> searchByTitleOrContent(String keyword) {
        log.info("searchByTitleOrContent(keyword={})", keyword);

        QPost post = QPost.post;
        JPQLQuery<Post> query = from(post);
        query.where(post.title.containsIgnoreCase(keyword)
                .or(post.content.containsIgnoreCase(keyword)))
                    .orderBy(post.id.desc());
        List<Post> result = query.fetch();

        return result;
    }

    @Override
    public List<Post> searchByModifiedTime(LocalDateTime from, LocalDateTime to) {
        log.info("searchByModifiedTime(from={}, to={})", from, to);

        QPost post = QPost.post;
        JPQLQuery<Post> query = from(post);
        query.where(post.modifiedTime.between(from, to)).orderBy(post.modifiedTime.desc());
        List<Post> result = query.fetch();

        return result;
    }

    @Override
    public List<Post> searchByAuthorAndTitle(String author, String title) {
        log.info("searchByAuthorAndTitle(author={}, title={})", author, title);

        QPost post = QPost.post;
        JPQLQuery<Post> query = from(post);
        query.where(post.author.eq(author).and(post.title.containsIgnoreCase(title))).orderBy(post.id.desc());
        List<Post> result = query.fetch();

        return result;
    }
    
    @Override
    public List<Post> searchByCategory(PostSearchRequestDto dto) {
        log.info("searchByCategory()dto={}", dto);
        String category = dto.getCategory();
        String keyword = dto.getKeyword();

        QPost post = QPost.post;
        JPQLQuery<Post> query = from(post);

        // where() 메서드의 아규먼트인 BooleanExpression 객체를 생성할 수 있는 객체
        BooleanBuilder builder = new BooleanBuilder();

        switch (category) {
            case "t":
                builder.and(post.title.containsIgnoreCase(keyword));
                break;
            case "c":
                builder.and(post.content.containsIgnoreCase(keyword));
                break;
            case "tc":
                builder.and(post.title.containsIgnoreCase(keyword))
                    .or(post.content.containsIgnoreCase(keyword));
                break;
            case "a":
                builder.and(post.author.containsIgnoreCase(keyword));
                break;
        }
        query.where(builder).orderBy(post.id.desc());

        return query.fetch();
    }

    @Override
    public List<Post> searchByKeywords(String[] keywords) { // 배열을 출력하려고 하면 @eoqie28u이런 식으로 나온다.
        log.info("seachByKeywords(keywords={})", Arrays.asList(keywords));
        
        QPost post = QPost.post;
        JPQLQuery<Post> query = from(post);
        BooleanBuilder builder = new BooleanBuilder();
        for (String k : keywords) {
            builder.or(post.title.containsIgnoreCase(k) // 배열에 있는 첫번째 단어에 대해서 제목에 포함되어 있는지
                    .or(post.content.containsIgnoreCase(k))); // 내용에 포함되어 있는지
        }
        query.where(builder).orderBy(post.id.desc());

        return query.fetch();
    }
}
