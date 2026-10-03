package com.ProgramTech.ProductService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ProgramTech.ProductService.Repository.ProductRepository;
import com.ProgramTech.ProductService.dto.ProductRequest;
import com.ProgramTech.ProductService.dto.ProductResponse;
import com.ProgramTech.ProductService.model.Product;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository)
	{
		this.productRepository=productRepository;
	}
	
	

    public void createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        
        productRepository.save(product);
        
    }
    
    public List<ProductResponse> getallProducts()
    {
    	List<Product> products= productRepository.findAll();
    	
    	return products.stream()
    			.map(this::mapToProductResponse)
    			.toList();
    }
    
    private ProductResponse mapToProductResponse(Product product)
    {
    	ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());

        return productResponse;
    }
    
    
    
    
}
