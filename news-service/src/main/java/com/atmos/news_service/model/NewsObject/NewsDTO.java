package com.atmos.news_service.model.NewsObject;

import lombok.Data;

@Data
public class NewsDTO {
    private String title;
    private String time;
    private String image;
    private String tag;
    private String type; // standard, text-only
    private String description;
    private String url;
}
