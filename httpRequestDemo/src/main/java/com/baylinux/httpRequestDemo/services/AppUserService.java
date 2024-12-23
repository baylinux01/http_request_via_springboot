package com.baylinux.httpRequestDemo.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baylinux.httpRequestDemo.entities.AppUser;
import com.baylinux.httpRequestDemo.repositories.AppUserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AppUserService {
	
	AppUserRepository appUserRepository;
	AuthenticationManager authenticationManager;
	PasswordEncoder passwordEncoder;
	
	@Autowired
	public AppUserService(AppUserRepository appUserRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		super();
		this.appUserRepository = appUserRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
	}

	public AppUser getAppUserByUsername(String username) {
		// TODO Auto-generated method stub
		return appUserRepository.findByUsername(username);
	}

	public AppUser getAppUserById(long id) {
		// TODO Auto-generated method stub
		return appUserRepository.findById(id).orElse(null);
	}

	public List<AppUser> getAllAppUsers() {
		// TODO Auto-generated method stub
		return appUserRepository.findAll();
	}

	public String createAppUser(String username, String password) {
		// TODO Auto-generated method stub
		List<AppUser> appUsers=getAllAppUsers();
		int i=0;
		while(i<appUsers.size())
		{
			if(appUsers.get(i).getUsername().equals(username))
				return "appuser with this username already exists";
			i++;
		}
		AppUser appUser=new AppUser();
		appUser.setUsername(username);
		appUser.setPassword(passwordEncoder.encode(password));
		appUser.setRoles("USER");
		appUserRepository.save(appUser);
		return "success";
	}

	public AppUser login(String username, String password) {
		// TODO Auto-generated method stub
		Authentication authentication =authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username,password));
		if(authentication.isAuthenticated()) 
		{
			
			AppUser appUser=(AppUser) appUserRepository.findByUsername(username);
			if(appUser!=null)
			{
				
				return appUser;
			}return null;
			
		}
		else return null;
	}

	public String updateAppUserPassword(HttpServletRequest request, String newPassword) {
		// TODO Auto-generated method stub
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		if(username!=null)
		{
		AppUser user=appUserRepository.findByUsername(username);
		user.setPassword(passwordEncoder.encode(newPassword));
		appUserRepository.save(user);
		return "success";
		}
		else return "fail";
		
	}

	public String updateAppUserUsername(HttpServletRequest request, String newUsername) {
		// TODO Auto-generated method stub
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		if(username!=null)
		{
			List<AppUser> users=appUserRepository.findAll();
			for(AppUser u:users)
			{
				if(u.getUsername().equals(newUsername)) return "username already exists";
			}
			AppUser user=appUserRepository.findByUsername(username);
			user.setUsername(newUsername);
			appUserRepository.save(user);
			return "success";
		}
		else return "fail";
	}

	

	
	
	

}
