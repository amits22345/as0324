package com.demo.amit.apps.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.amit.apps.model.Tools;

public interface ToolsRepository extends MongoRepository<Tools, String>{

}
