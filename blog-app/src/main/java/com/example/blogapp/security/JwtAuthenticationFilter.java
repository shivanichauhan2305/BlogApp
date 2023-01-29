package com.example.blogapp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//1) get token from request
		String requestToken = request.getHeader("Authorization");
			
		//token will be start with Bearer like "Bearer 23443436547"
		System.out.println(requestToken);
		
		String username = null;
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			 token = requestToken.substring(7);
			 try {
			 username = this.jwtTokenHelper.getUsernameFromToken(token);
			 }
			 catch(IllegalArgumentException ex) {
				 System.out.println("Unable to get Jwt token");
			 }
			 catch(ExpiredJwtException ex) {
				 System.out.println("Expired Jwt token");
			 }
			 catch(MalformedJwtException ex) {
				 System.out.println("Invalid Jwt token");
			 }
			 catch(Exception ex) {
				 System.out.println("Unable to get Jwt token.. Dont know.. exception name");
			 }
		}
		else {
			System.out.println("Jwt Token doesn't starts with Bearer");
		}
		
		
		if(username!=null && SecurityContextHolder.getContext()==null) {
			UserDetails user = this.userDetailsService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validateToken(token, user)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());;
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);;
			}
			else {
				System.out.println("Invalid JWT token");
			}
		}
		else {
			System.out.println("Jwt Token doesn't have username.. or context is not null...Invalid JWT token");
		}
		
		//if everything goes well it will go with setted context security otherwise ...
		filterChain.doFilter(request, response);
		
	}

}
