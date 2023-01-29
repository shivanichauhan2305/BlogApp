package com.example.blogapp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	String resourceName;
	String fieldName;
	long fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldName, Integer userId) {
		super(String.format("%s not found with %s : %o", resourceName,fieldName,userId));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
	
	}
	
	public ResourceNotFoundException(String resourceName, String fieldName, String username) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,username));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
	
	}
	
	

}
