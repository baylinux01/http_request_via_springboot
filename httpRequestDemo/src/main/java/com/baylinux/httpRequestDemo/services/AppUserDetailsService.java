package com.baylinux.httpRequestDemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baylinux.httpRequestDemo.entities.AppUser;
import com.baylinux.httpRequestDemo.entities.AppUserDetails;
import com.baylinux.httpRequestDemo.repositories.AppUserRepository;


@Service
public class AppUserDetailsService implements UserDetailsService{
	
	AppUserRepository appUserRepository;
	
	
	@Autowired
	public AppUserDetailsService(AppUserRepository appUserRepository) {
		super();
		this.appUserRepository = appUserRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		AppUser appUser= appUserRepository.findByUsername(username);
		if(appUser==null) throw new UsernameNotFoundException("User not found");
		else
		{
			return new AppUserDetails(appUser);
		}
	}

}
