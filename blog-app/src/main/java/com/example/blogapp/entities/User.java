package com.example.blogapp.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private int id;
	
	@Column(name="user_name", nullable=false,length=100)
	private String name;

	private String email;
	
	private String password;
	
	private String about;
	
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> post;
	
//	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
//	private List<Comment> comments;
	


}
