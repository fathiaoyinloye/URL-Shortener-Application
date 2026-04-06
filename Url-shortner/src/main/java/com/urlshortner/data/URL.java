package com.urlshortner.data;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class URL {
    @Id
    private String id;
    private String shortUrl;
    private String longUrl;
}
