package com.botiga.com_botiga.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return products;
    }
    //Devuelve un producto segun su id
    public Optional<Product> getProductById(long id){
        return productRepository.findById(id);
    }

    public Product postroduct(Product product){
        return productRepository.save(product);
    }

    

}
