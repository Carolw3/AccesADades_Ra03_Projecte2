package com.botiga.com_botiga.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.botiga.com_botiga.DTO.ProductRequesteDto;
import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.model.ProductCondition;
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

    public Product patchProduct(Long id, Product productNou){
        Optional<Product> existe = productRepository.findById(id);

        if(existe.isPresent()){ // para saber si existe ese producto

            Product product = existe.get();

            if(productNou.getName() != null){
                product.setName(productNou.getName());
            }

            if(productNou.getDescription() != null){
                product.setDescription(productNou.getDescription());
            }

            if(productNou.getStock() != null){
                product.setStock(productNou.getStock());
            }

            if(productNou.getPrice() != null){
                product.setPrice(productNou.getPrice());
            }

            if(productNou.getRating() != null){
                product.setRating(productNou.getRating());
            }

            if(productNou.getCondition() != null){
                product.setCondition(productNou.getCondition());
            }

            if(productNou.getStatus() != null){
                product.setStatus(productNou.getStatus());
            }

        return productRepository.save(product);
        }

        return null;
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

    public Product patchPrice(Long id, BigDecimal price){
        Optional<Product> existe = productRepository.findById(id);
        if(existe.isPresent()){
            Product product = existe.get();
            product.setPrice(price);
            return productRepository.save(product);
        }
        return null;
    }


    public Product patchStatus(Long id, Boolean status){

        Optional<Product> existe = productRepository.findById(id);

        if(existe.isPresent()){
            Product product = existe.get();
            product.setStatus(status);

            return productRepository.save(product);
        }
        return null;
    }


    public boolean deleteProduct(Long id){

        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return true;
        }

        return false;
    }
    
    
    public String uploadCsv (MultipartFile csv) throws IOException{
        List<String> noAceptados = new ArrayList<>();
        
        int inserciones = 0;

        try(BufferedReader br = new BufferedReader(new InputStreamReader(csv.getInputStream()))){
            // sacamos la capcelera
            String linea = br.readLine();
            
            if(linea == null){
                return null;
            }
            
            while((linea = br.readLine()) != null){
                String[] elemento = linea.split(",");
                if(elemento.length != 7){
                    noAceptados.add(elemento[0]);
                    continue;
                }
                String name = elemento[0].trim();
                String description = elemento[1].trim();
                Integer stock = Integer.parseInt(elemento[2].trim());
                BigDecimal price = new BigDecimal(elemento[3].trim());
                BigDecimal rating = new BigDecimal(elemento[4].trim());
                ProductCondition condition = ProductCondition.fromValue(elemento[5].trim());
                Boolean status = Boolean.parseBoolean(elemento[6].trim());

                Product product = new Product(name, description, stock, price, rating, condition,status, LocalDateTime.now(), LocalDateTime.now());
                Product confirmacion = productRepository.save(product);
                if(confirmacion != null){
                    inserciones++;
                }

            }
        }

        return "Inserciones hechas: " + inserciones + " Y no hechas " + noAceptados.size();
    }
    
    public List<Product> getProductsByName(String name){
        return productRepository.findByNameStartingWithIgnoreCase(name);
    }

    public List<Product> searchByName(String prefix){
        return productRepository.findByNameContainingIgnoreCase(prefix);
    }

    public List<Product> searchByCondition(ProductCondition condition){
        return productRepository.findByCondition(condition);
    }

    public List<Product> order(String camp, String order){

        if (camp.equals("price")){
            if (order.equals("desc")){
                return productRepository.findAllByOrderByPriceDesc();
            }
            return productRepository.findAllByOrderByPriceAsc();
            
        }else if(camp.equals("rating")){

            if(order.equalsIgnoreCase("desc")){
                return productRepository.findAllByOrderByRatingDesc();
            }
            return productRepository.findAllByOrderByRatingAsc();
        }
        return null;
    }


    public List<ProductRequesteDto> searchProducts(Double min, Double max, String camp, String order, int limit, String prefix) {
        if (!camp.equalsIgnoreCase("price") && !camp.equalsIgnoreCase("rating")) {
            throw new IllegalArgumentException("Campo inválido para ordenar: " + camp);
        }

        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(camp).descending()
                : Sort.by(camp).ascending();
        Pageable pageable = PageRequest.of(0, limit, sort);

        Page<Product> products;

        if (camp.equalsIgnoreCase("price")) {
            BigDecimal minPrice = (min != null) ? BigDecimal.valueOf(min) : BigDecimal.ZERO;
            BigDecimal maxPrice = (max != null) ? BigDecimal.valueOf(max) : BigDecimal.valueOf(Double.MAX_VALUE);

            products = productRepository.findProductsByPriceRange(minPrice, maxPrice, prefix, pageable);
        } else {
            BigDecimal minRating = (min != null) ? BigDecimal.valueOf(min) : BigDecimal.ZERO;
            BigDecimal maxRating = (max != null) ? BigDecimal.valueOf(max) : BigDecimal.valueOf(5); // rating máximo asumido 5

            products = productRepository.findProductsByRatingRange(minRating, maxRating, prefix, pageable);
        }

        return products.stream()
                .map(ProductRequesteDto::new)
                .toList();
    }










    /*
    //¡FALTA EL LIMIT, PREGUNTAR A ORIOL!
    public List<Product> filter(String camp, String order, int min, int max, int limit, String prefix) {
        if (camp.equals("price")) {

            if (order.equalsIgnoreCase("desc")) {
                return productRepository.findProductsByPriceDesc(min, max, prefix, limit);
            } else {
                return productRepository.findProductsByPriceAsc(min, max, prefix, limit);
            }

        } else if (camp.equals("rating")) {

            if (order.equalsIgnoreCase("desc")) {
                return productRepository.findProductsByRatingDesc(min, max, prefix, limit);
            } else {
                return productRepository.findProductsByRatingAsc(min, max, prefix, limit);
            }
        }

        return null;
    }
*/
}
