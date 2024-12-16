package com.baylinux.httpRequestDemo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.baylinux.httpRequestDemo.entities.Article;
import com.baylinux.httpRequestDemo.entities.MyHttpRequest;
import com.baylinux.httpRequestDemo.entities.MyHttpResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ExternalApiRequestService {

	RestTemplate restTemplate;
	
	
	@Autowired
	public ExternalApiRequestService(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}



	public MyHttpResponse doGetRequest(HttpServletRequest request) {
		//String url="https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=myApiKey";
		String url="https://httpbin.org/get";
		ResponseEntity<MyHttpResponse> response=restTemplate.getForEntity(url, MyHttpResponse.class);
		
		MyHttpResponse mhr=response.getBody();
//		System.out.println(mhr.getStatus());
//		for(Article art:mhr.getArticles())
//		{
//			System.out.println(art.getAuthor());
//		}
//		System.out.println(mhr.getArticles().get(0).getTitle());
		return mhr;
		
	}



	public MyHttpResponse doPostRequest(HttpServletRequest request) {
		
		//RestTemplate restTemplate=new RestTemplate(); //zaten containerda var gerek yok.
		
		HttpHeaders headers=new HttpHeaders();
		//headers.setBasicAuth(username, password);
		//headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		MyHttpRequest myHttpRequest=new MyHttpRequest();
		myHttpRequest.setName("Ali");
		myHttpRequest.setSurname("RastgeleSoyadı");
		myHttpRequest.setAge(35);
		HttpEntity<MyHttpRequest> entity=new HttpEntity<MyHttpRequest>(myHttpRequest,headers);
		
		String url="https://httpbin.org/post";
		
		ResponseEntity<MyHttpResponse> responseEntity
				=restTemplate.postForEntity(url,entity,MyHttpResponse.class);
		
		MyHttpResponse myResponse=responseEntity.getBody();
		
		System.out.println(myResponse.getData());
		System.out.println(myResponse.getOrigin());
		System.out.println(myResponse.getUrl());
		
		return myResponse;
		
		
	}

}
