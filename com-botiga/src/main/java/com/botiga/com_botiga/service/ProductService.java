package com.botiga.com_botiga.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.service.annotation.PutExchange;

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

    public Product patchEstoc(Long id, Integer stock){
        Optional<Product> existe = productRepository.findById(id);
        if(existe.isPresent()){ // para saber si existe ese producto 
            Product product = existe.get();
            product.setStock(stock);
            return productRepository.save(product);
        }
        // Si no lo encontramos pues devolvemos un null
        return null;
    }
    

}
