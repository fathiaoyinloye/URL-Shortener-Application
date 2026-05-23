package com.urlshortner.service;

import com.urlshortner.data.URL;
import com.urlshortner.data.URLRepository;
import com.urlshortner.dtos.ShortURLRequest;
import com.urlshortner.dtos.ShortURLResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;



@Service
public class ShortURLService {
    @Autowired
    private URLRepository repository;

    @Value("${app.base.url}")
    private String baseUrl;

    public ShortURLResponse createShortenUrl(ShortURLRequest request){
        URL url = new URL();
        url.setLongUrl(request.getLongUrl());
        url.setShortUrl(request.getName());
        repository.save(url);
        ShortURLResponse response = new ShortURLResponse();
        response.setMessage("Short URL Successfully created");
        response.setShortUrl(baseUrl + url.getShortUrl());
        return response;
    }

    @Cacheable(value = "urls", key = "#shortUrl")
    public String getLongUrl(String shortUrl){
            return findURL(shortUrl).getLongUrl();
    }

    private URL findURL(String shortUrl){
        URL url = repository.findByshortUrl(shortUrl);
        if(url == null) throw  new RuntimeException("Short Url Does not exist");
        return repository.findByshortUrl(shortUrl);
    }




}
