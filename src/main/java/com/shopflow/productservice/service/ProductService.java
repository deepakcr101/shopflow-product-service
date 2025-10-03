package com.shopflow.productservice.service;

import com.shopflow.productservice.model.Product;
import com.shopflow.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import com.shopflow.productservice.event.ProductCreatedEvent;
import com.shopflow.productservice.event.ProductDeletedEvent;
import com.shopflow.productservice.event.ProductUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Cacheable(value = "products", key = "#id")
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        eventPublisher.publishEvent(new ProductCreatedEvent(this, savedProduct));
        return savedProduct;
    }

    @CachePut(value = "products", key = "#id")
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        Product updatedProduct = productRepository.save(product);
        eventPublisher.publishEvent(new ProductUpdatedEvent(this, updatedProduct));
        return updatedProduct;
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        eventPublisher.publishEvent(new ProductDeletedEvent(this, id));
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContaining(name);
    }
}
