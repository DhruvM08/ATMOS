package com.atmos.news_service.model.NewsObject;

import lombok.Data;
import java.util.List;

@Data
public class NewsResponse {
    private int offset;
    private int number;
    private int available;
    private List<NewsItem> news;
}
