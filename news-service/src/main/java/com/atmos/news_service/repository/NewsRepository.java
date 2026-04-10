package com.atmos.news_service.repository;

import com.atmos.news_service.model.NewsHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends MongoRepository<NewsHistory, String> {
}
