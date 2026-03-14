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
import com.botiga.com_botiga.mapper.Mapper;
import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.model.ProductCondition;
import com.botiga.com_botiga.repository.ProductRepository;


@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    Mapper mapper;

    public List<ProductRequesteDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        
        return mapper.toDtoList(products);
    }
    //Devuelve un producto segun su id
    public ProductRequesteDto getProductById(long id) throws Exception {
        Optional<Product> p = productRepository.findById(id);

        if (p.isPresent()) {
            Product product = p.get();
            return mapper.toDto(product);
        }

        return null;
    }

    public ProductRequesteDto postroduct(Product product){
       Product p = productRepository.save(product);
       return mapper.toDto(p);
    }

    public ProductRequesteDto patchProduct(Long id, Product productNou){
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
        Product p =  productRepository.save(product);
        return mapper.toDto(p);
        }

        return null;
    }


    public ProductRequesteDto patchEstoc(Long id, Integer stock){
        Optional<Product> existe = productRepository.findById(id);
        if(existe.isPresent()){ // para saber si existe ese producto 
            Product product = existe.get();
            product.setStock(stock);
            productRepository.save(product);
            return mapper.toDto(product);
        }
        // Si no lo encontramos pues devolvemos un null
        return null;
    }

    public ProductRequesteDto patchPrice(Long id, BigDecimal price){
        Optional<Product> existe = productRepository.findById(id);
        if(existe.isPresent()){
            Product product = existe.get();
            product.setPrice(price);
            productRepository.save(product);
            return mapper.toDto(product);
        }
        return null;
    }


    public ProductRequesteDto patchStatus(Long id, Boolean status){

        Optional<Product> existe = productRepository.findById(id);

        if(existe.isPresent()){
            Product product = existe.get();
            product.setStatus(status);
            productRepository.save(product);
            return mapper.toDto(product);
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

    public List<ProductRequesteDto> searchByCondition(ProductCondition condition){
        List<Product> products = productRepository.findByCondition(condition);
        return mapper.toDtoList(products);
    }

    public List<ProductRequesteDto> order(String camp, String order){

        if (camp.equals("price")){
            if (order.equals("desc")){
                List<Product> p = productRepository.findAllByOrderByPriceDesc();
                return mapper.toDtoList(p);
            }
            List<Product> p = productRepository.findAllByOrderByPriceAsc();
            return mapper.toDtoList(p);
            
        }else if(camp.equals("rating")){

            if(order.equalsIgnoreCase("desc")){
                List<Product> p = productRepository.findAllByOrderByRatingDesc();
                return mapper.toDtoList(p);
            }
            List<Product> p = productRepository.findAllByOrderByRatingAsc();
            return mapper.toDtoList(p);
        }
        return null;
    }


    public List<ProductRequesteDto> searchProducts(BigDecimal min, BigDecimal max, String camp, String order, int limit, String prefix) throws Exception {
        if (!camp.equalsIgnoreCase("price") && !camp.equalsIgnoreCase("rating")) {
            throw new IllegalArgumentException("Campo inválido para ordenar: " + camp);
        }

        if(camp.equals("price")){
            if(order.equals("desc")){
                Pageable pageable = PageRequest.of(0, limit);
                List<Product> product = productRepository.findProductsByPriceDesc(min, max, prefix, pageable);
                return mapper.toDtoList(product);
            }else{
                Pageable pageable = PageRequest.of(0, limit);
                List<Product> product = productRepository.findProductsByPriceAsc(min, max, prefix, pageable);
                return mapper.toDtoList(product);

            }
        }else if(camp.equals("rating")){
            if(order.equals("desc")){
                Pageable pageable = PageRequest.of(0, limit);
                List<Product> product = productRepository.findProductsByRatingDesc(min, max, prefix, pageable);
                return mapper.toDtoList(product);
            }else{
                Pageable pageable = PageRequest.of(0, limit);
                List<Product> product = productRepository.findProductsByRatingAsc(min, max, prefix, pageable);
                return mapper.toDtoList(product);
            }
        }else{
            return null;
        }

    }



    public List<ProductRequesteDto> searchQualityPrice(){

        List<Product> products = productRepository.findTop5BestQualityPrice();

        List<ProductRequesteDto> dtos = mapper.toDtoList(products);

        return dtos;
    }


    public List<ProductRequesteDto> bestNews(){
        List<Product> products = productRepository.findTop10NewProductsByRating();

        List<ProductRequesteDto> dtos = mapper.toDtoList(products);
        return dtos;
    }


    public List<ProductRequesteDto> searchInBlock(int page){

        Pageable pageable = PageRequest.of(page, 5);
        List<Product> products = productRepository.findProductsInBlockList(pageable);

        List<ProductRequesteDto> dtos = mapper.toDtoList(products);
        return dtos;
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
