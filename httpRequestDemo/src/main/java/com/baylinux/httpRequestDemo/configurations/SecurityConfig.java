package com.baylinux.httpRequestDemo.configurations;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



//import baylinux01.securityImplementationDemo.filters.JWTFilter;
//import io.jsonwebtoken.lang.Arrays;



@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	private UserDetailsService userDetailsService;
	//private JWTFilter jWTFilter;
	
	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService)//,JWTFilter jWTFilter) 
	{
		super();
		this.userDetailsService = userDetailsService;
		//this.jWTFilter=jWTFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		return http
				.csrf(customizer->customizer.disable())
				.authorizeHttpRequests(req->req
						.requestMatchers("/*","/appUser/createAppUser","/appUser/login").permitAll()
						//.requestMatchers("/appUser/**").hasRole("USER")
						.anyRequest().authenticated()
						)
			  //.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				//.addFilterBefore(jWTFilter, UsernamePasswordAuthenticationFilter.class)
				.cors(httpSecurityCorsConfigurer->
				{
					CorsConfiguration configuration=new CorsConfiguration();
					configuration.setAllowedOrigins(List.of("*"));
					configuration.setAllowedMethods(List.of("*"));
					configuration.setAllowedHeaders(List.of("*"));
					UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
					source.registerCorsConfiguration("/**", configuration);
					httpSecurityCorsConfigurer.configurationSource(source);
				})
				.build();
				
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() 
	{
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
		
	}
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(12);
	}
	@Bean 
	public RestTemplate restTemplate() 
	{
		return new RestTemplate();
	}
//	@Bean
//	public UserDetailsService userDetailsService()
//	{
//		UserDetails inMemoryUser=User
//										.builder()
//										.username("inMemoryUser")
//										.password("$2a$12$RrtH5At9LG0UOipBkO2EiOnhRVSJ..6X01XJ0ZB4scs4CIHRfWKxO")
//										.roles("USER","ADMIN")
//										.build();
//		UserDetails inMemoryUser2=User
//				.builder()
//				.username("inMemoryUser2")
//				.password("$2a$12$RrtH5At9LG0UOipBkO2EiOnhRVSJ..6X01XJ0ZB4scs4CIHRfWKxO")
//				.roles("USER")
//				.build();	
//		return new InMemoryUserDetailsManager(inMemoryUser,inMemoryUser2);
//		
//	}

}
