package com.atmos.news_service.model.NewsObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class NewsItem {

    private long id;
    private String title;
    private String text;
    private String summary;
    private String url;
    private String image;

    @JsonProperty("publish_date")
    private String publishDate;

    private String author;
    private List<String> authors;
    private String language;
    private String category;

    @JsonProperty("source_country")
    private String sourceCountry;

    private Double sentiment;
}
