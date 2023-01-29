package com.example.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogapp.entities.User;
import com.example.blogapp.exceptions.ResourceNotFoundException;
import com.example.blogapp.payloads.UserDto;
import com.example.blogapp.repositories.UserRepository;
import com.example.blogapp.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		
		User savedUser = this.userRepository.save(user);
		
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		
		User u = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","UserID",userId));
		
		u.setAbout(userDto.getAbout());
		u.setEmail(userDto.getEmail());
		u.setName(userDto.getName());
		u.setPassword(userDto.getPassword());
		
		User updatedUser = this.userRepository.save(u);
		
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User u = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","UserID",userId));
		
		
		return this.userToDto(u);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> listOfUsers = this.userRepository.findAll();
		return listOfUsers.stream().map(x -> userToDto(x)).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User u = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","UserID",userId));
		this.userRepository.delete(u);
		
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		/*
		 * User user = new User(); user.setName(userDto.getName());
		 * user.setEmail(userDto.getEmail()); user.setPassword(userDto.getPassword());
		 * user.setAbout(userDto.getAbout());
		 */
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
		/*
		 * UserDto userDto = new UserDto(); userDto.setName(user.getName());
		 * userDto.setEmail(user.getEmail()); userDto.setPassword(user.getPassword());
		 * userDto.setAbout(user.getAbout());
		 */
		return userDto;
		
	}
}
