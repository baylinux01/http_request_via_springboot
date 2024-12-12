package com.baylinux.httpRequestDemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baylinux.httpRequestDemo.entities.AppUser;





public interface AppUserRepository extends JpaRepository<AppUser,Long>{

	AppUser findByUsername(String username);
	
	

}
