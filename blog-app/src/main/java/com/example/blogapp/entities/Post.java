package com.example.blogapp.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;

	@Column(name = "post_title", length = 100, nullable = false)
	private String title;

	@Column(length = 1000000000)
	private String content;

	private String imageName;

	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	//if we remove parent but child stays so that functionality will be implemented by lazy fetch type
	//cascade means if parent do then child also do, if we change parent then child change, if we add in parent then child added
	@ManyToOne() 
	@JoinColumn(name ="category_id")
	private Category category;
	
	@OneToMany(mappedBy="post",cascade=CascadeType.ALL)
	private List<Comment> comments;
}
