package com.ProgramTech.ProductService;

import com.ProgramTech.ProductService.Repository.ProductRepository;
import com.ProgramTech.ProductService.dto.ProductRequest;
import com.ProgramTech.ProductService.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
    
    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());

        Assertions.assertEquals(1, productRepository.findAll().size());
    }

    private ProductRequest getProductRequest() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("iPhone 13");
        productRequest.setDescription("iPhone 13");
        productRequest.setPrice(BigDecimal.valueOf(12000));
        return productRequest;
    }
    
    @Test
    void shouldGetAllproducts() throws Exception {
    	Product product1 = new Product();
    	product1.setName("i phone 13");
    	product1.setDescription("i phone 13");
    	product1.setPrice(BigDecimal.valueOf(120000));
    	
    	Product product2 = new Product();
    	product2.setName("i phone 13");
    	product2.setDescription("i phone 13");
    	product2.setPrice(BigDecimal.valueOf(120000));
    	
    	productRepository.save(product1);
    	productRepository.save(product2);
    	
    	mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
    	       .andExpect(status().isOk());
    	
    }
    
}