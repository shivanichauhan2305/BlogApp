package com.example.blogapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.blogapp.security.CustomUserDetailService;
import com.example.blogapp.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Autowired
	AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	 @Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.csrf()
	        .disable()
	        .authorizeHttpRequests()
	        .requestMatchers("/api/v1/auth/login")
	        .permitAll()
	        .anyRequest()
	        .authenticated()
	        .and()
	        .exceptionHandling()
	        .authenticationEntryPoint(this.authenticationEntryPoint)
	        .and()
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        
	        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	     
	        DefaultSecurityFilterChain build = http.build();
	        
	        return build;
	 }
	 
	 @Bean
	 public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception { 
		 //return authentication manager
		 return configuration.getAuthenticationManager();
	 }
	 
	 @Bean
		PasswordEncoder passwordEncoder(){
			return new BCryptPasswordEncoder();
		}
	 
	 @Bean
	 public DaoAuthenticationProvider daoAuthenticationProvider() {
		 DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		 daoAuthenticationProvider.setUserDetailsService(this.customUserDetailService);
		 daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		 return daoAuthenticationProvider;
	 }
}


/*
 * simple security config class as per new version  -----------------version 1
 * 
 * 
@Configuration
public class SecurityConfig {

	  @Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.csrf()
	        .disable()
	        .authorizeHttpRequests()
	        .anyRequest()
	        .authenticated()
	        .and()
	        .httpBasic();
	        
	        http.authenticationProvider(daoAuthenticationProvider());
	        
	        return http.build();  
	        
	        return http.build();  
	        
	 }
}*/


/*
 * @Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Autowired
	AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	 @Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	         
	         http.csrf()
	        .disable()
	        .authorizeHttpRequests()
	        .anyRequest()
	        .authenticated()
	        .and()
	        .httpBasic();
		        
	        http.authenticationProvider(daoAuthenticationProvider());
	        
	        return http.build();  
	        
	 }
	 
	 @Bean
	 public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception { 
		 //return authentication manager
		 return configuration.getAuthenticationManager();
	 }
	 
	 @Bean
		PasswordEncoder passwordEncoder(){
			return new BCryptPasswordEncoder();
		}
	 
	 @Bean
	 public DaoAuthenticationProvider daoAuthenticationProvider() {
		 DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		 daoAuthenticationProvider.setUserDetailsService(this.customUserDetailService);
		 daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		 return daoAuthenticationProvider;
	 }
}
 */
