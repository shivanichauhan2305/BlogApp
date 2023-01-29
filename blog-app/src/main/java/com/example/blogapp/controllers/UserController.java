package com.example.blogapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.payloads.ApiResponse;
import com.example.blogapp.payloads.UserDto;
import com.example.blogapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apis/users")
public class UserController {
	
	@Autowired
	UserService userService;

	//POST - Create user
	@PostMapping("/create/User")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user){
		
		UserDto createdUser = this.userService.createUser(user);
		return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);
		
	}
	
	//GET - find by id user
	@GetMapping("/find/User/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer uid){
		
		UserDto user = this.userService.getUserById(uid);
		return ResponseEntity.ok(user);
		
	}
	
	//GET - find all user
		@GetMapping("/find/Users")
		public ResponseEntity<List<UserDto>> getAllUser(){
			
			List<UserDto> users = this.userService.getAllUsers();
			return ResponseEntity.ok(users);
			
		}
	
	//PUT - Update user
	@PutMapping("/update/User/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto user,@PathVariable("userId") Integer uid){
		
		UserDto updatedUser = this.userService.updateUser(user,uid);
		return ResponseEntity.ok(updatedUser);
		
	}
	
	//DELETE - delete user
	@DeleteMapping("/delete/User/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		
		this.userService.deleteUser(uid);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true),HttpStatus.OK);
	}
}
