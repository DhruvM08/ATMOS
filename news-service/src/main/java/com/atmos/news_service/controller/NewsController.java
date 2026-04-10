package com.atmos.news_service.controller;

import com.atmos.news_service.model.NewsObject.*;
import com.atmos.news_service.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/getAtmosphericNews")
    public ResponseEntity<List<NewsDTO>> getAtmosphericNews() {
        return ResponseEntity.ok(newsService.getLatestNews());
    }
}
