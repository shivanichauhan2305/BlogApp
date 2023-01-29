package com.example.blogapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.payloads.ApiResponse;
import com.example.blogapp.payloads.CommentDto;
import com.example.blogapp.services.CommentService;

@RestController
@RequestMapping("/apis/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/create/comment/{commentId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,@PathVariable Integer commentId){
		CommentDto cmt = this.commentService.createComment(comment, commentId);
		return new ResponseEntity<CommentDto>(cmt,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment is successfully deleted !!", true),HttpStatus.OK);
	}

}
