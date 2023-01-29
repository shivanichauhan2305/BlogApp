package com.example.blogapp.payloads;

import com.example.blogapp.entities.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

	private Integer id;
	
	private String content;
	
}
