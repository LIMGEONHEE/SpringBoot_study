package com.itwill.springboot5.dto;

import lombok.Data;

@Data
public class CommentCreateDto {

    private Long id;
    private String ctext;
    private String writer;
    
}
