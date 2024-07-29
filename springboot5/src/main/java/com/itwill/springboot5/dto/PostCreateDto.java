package com.itwill.springboot5.dto;

import com.itwill.springboot5.domain.Post;

import lombok.Data;

@Data
public class PostCreateDto {
    private String title;
    private String content;
    private String author;

    public Post toEntity() { // static이 아닌 이유: 이미 객체가 만들어져 있기 때문.
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
