package com.baylinux.httpRequestDemo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.baylinux.httpRequestDemo.entities.Article;
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
		String url="https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=myApiKey";
		
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

}
