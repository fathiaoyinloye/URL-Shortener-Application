package com.urlshortner.controller;

import com.urlshortner.dtos.ShortURLRequest;
import com.urlshortner.service.ShortURLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;
@Slf4j
@RestController
@RequestMapping("")
public class URLController {
    @Autowired
    private ShortURLService shortURLService;
//
    @PostMapping("/short-url")
    public ResponseEntity<?> shortenUrl(@RequestBody ShortURLRequest request) {
            log.info("Received request: {}", request.getLongUrl());
            var response = shortURLService.createShortenUrl(request);
            log.info("url as been processed: {}", response.getShortUrl());
            return ResponseEntity.status(CREATED).body(response);


    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable String shortUrl) {
        String longUrl = shortURLService.getLongUrl(shortUrl);
        try{
        return ResponseEntity.status(MOVED_PERMANENTLY)
                .location(URI.create(longUrl))
                .build();
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

}
