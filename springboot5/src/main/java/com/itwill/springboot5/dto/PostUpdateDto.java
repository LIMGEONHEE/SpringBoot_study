package com.itwill.springboot5.dto;

import com.itwill.springboot5.domain.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Data 하나만 있어도 된다.
@NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PostUpdateDto {

    private Long id;
    private String title;
    private String content;

    public Post toEntity() {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }


    
}
