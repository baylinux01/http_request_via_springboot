package com.baylinux.httpRequestDemo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baylinux.httpRequestDemo.entities.MyHttpResponse;
import com.baylinux.httpRequestDemo.services.ExternalApiRequestService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/externalApiRequest")
public class ExternalApiRequestController {
	
	ExternalApiRequestService externalApiRequestService;
	
	
	@Autowired
	public ExternalApiRequestController(ExternalApiRequestService externalApiRequestService) {
		super();
		this.externalApiRequestService = externalApiRequestService;
	}



	@GetMapping("/doGetRequest")
	public MyHttpResponse doGetRequest(HttpServletRequest request)
	{
		return externalApiRequestService.doGetRequest(request);
	}

}
