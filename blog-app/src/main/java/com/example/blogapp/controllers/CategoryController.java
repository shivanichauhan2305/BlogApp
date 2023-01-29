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
import com.example.blogapp.payloads.CategoryDto;
import com.example.blogapp.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apis/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;

	//POST - Create category
	@PostMapping("/create/Category")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto category){
		
		CategoryDto createdCategory = this.categoryService.createCategory(category);
		return new ResponseEntity<CategoryDto>(createdCategory, HttpStatus.CREATED);
		
	}
	
	//GET - find by id category
	@GetMapping("/find/Category/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Integer cid){
		
		CategoryDto category = this.categoryService.getCategory(cid);
		return ResponseEntity.ok(category);
		
	}
	
	//GET - find all category
		@GetMapping("/find/Categories")
		public ResponseEntity<List<CategoryDto>> getAllCategory(){
			
			List<CategoryDto> category = this.categoryService.getAllCategories();
			return ResponseEntity.ok(category);
			
		}
	
	//PUT - Update category
	@PutMapping("/update/Category/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto category,@PathVariable("categoryId") Integer cid){
		
		CategoryDto updatedCategory = this.categoryService.updateCategory(category, cid);
		return ResponseEntity.ok(updatedCategory);
		
	}
	
	//DELETE - delete category
	@DeleteMapping("/delete/Category/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer cid){
		
		this.categoryService.deleteCategory(cid);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true),HttpStatus.OK);
	}

}
