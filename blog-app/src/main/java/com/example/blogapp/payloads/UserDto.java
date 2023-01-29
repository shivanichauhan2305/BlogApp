package com.example.blogapp.payloads;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto { 
// direct expose data to apis so we don't need to directly expose our actual entities connected to db
	
	private int id;
	
	@NotEmpty
	@Size(min = 4, message ="Username must be minimum 4 character long..!!!")
	private String name;
	
	@Email(message ="Email address is not valid...!!!")
	private String email;
	
	@NotNull
	@Size(min = 3,max = 10, message ="password must be min of 3 chars and max of 10 chars..!!")
	private String password;
	
	@NotEmpty
	private String about;

}
