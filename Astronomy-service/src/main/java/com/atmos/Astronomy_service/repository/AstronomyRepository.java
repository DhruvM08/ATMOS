package com.atmos.Astronomy_service.repository;

import com.atmos.Astronomy_service.model.AstronomyData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AstronomyRepository extends MongoRepository<AstronomyData, String> {
}
