package com.urlshortner.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortURLResponse {
    private String shortUrl;
    private String message;
}
