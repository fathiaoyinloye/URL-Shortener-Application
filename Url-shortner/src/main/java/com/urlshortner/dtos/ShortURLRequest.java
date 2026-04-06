package com.urlshortner.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortURLRequest {
    private String longUrl;
    private String name;
}
