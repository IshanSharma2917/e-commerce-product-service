package com.ProgramTech.ProductService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ProgramTech.ProductService.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
