package com.demo.amit.apps.rental.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.amit.apps.rental.model.RentalAgrement;


public interface RentalRepository extends MongoRepository<RentalAgrement, String>{

}