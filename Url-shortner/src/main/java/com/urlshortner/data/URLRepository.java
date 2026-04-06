package com.urlshortner.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepository extends MongoRepository<URL,String> {
    URL findByshortUrl(String shortUrl);
}
