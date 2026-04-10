package com.atmos.news_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "news_history")
public class NewsHistory {

    @Id
    private String id;
    private String title;
    private String summary;
    private String url;
    private String publishDate;
    private String category;
    private LocalDateTime savedAt;

}
