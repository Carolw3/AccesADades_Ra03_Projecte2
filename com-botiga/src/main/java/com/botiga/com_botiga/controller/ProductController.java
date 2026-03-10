package com.botiga.com_botiga.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.botiga.com_botiga.DTO.ProductRequesteDto;
import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.model.ProductCondition;
import com.botiga.com_botiga.service.ProductService;



@RequestMapping("/api")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    // ------------------ Endpoints de modificacion de la BDD ------------------------

    //Subida automatica de productos a la BDD
    @PostMapping("/products/csv")
    public ResponseEntity<String> addCsv(@RequestParam MultipartFile csv)throws IOException {
        String resultado = productService.uploadCsv(csv);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }

    //Guarda un producto
    @PostMapping("/product")
    public  ResponseEntity<Product> postPodcut(@RequestBody Product product) {
        Product resultado = productService.postroduct(product);
        
        if(resultado != null){
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //Modifica todo un producto
    @PatchMapping("/product/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable() long id, @RequestBody Product product){
        Product result = productService.patchProduct(id, product);
        
        if(result == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    //Modifica el stock de un producto
    @PatchMapping("/product/{id}/stock")
    public ResponseEntity<Product> patchEstoc(@PathVariable() long id, @RequestParam() Integer stock){
        Product resultado = productService.patchEstoc(id, stock);
        
        if(resultado == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }
    }

    //Modifica el precio de un producto
    @PatchMapping("/product/{id}/price")
    public ResponseEntity<Product> patchEstoc(@PathVariable() long id, @RequestParam() BigDecimal price){
        Product resultado = productService.patchPrice(id, price);
        
        if(resultado == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }
    }

    //Borrado logico de un producto, modifica el status pasandolo a false (modo de hivernación)
    @PatchMapping("/product/{id}/status")
    public ResponseEntity<Product> patchStatus(@PathVariable() long id, @RequestParam() Boolean status){
        Product resultado = productService.patchStatus(id, status);
        
        if(resultado == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }
    }
    
    //Borrado fisico de un producto
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){

        boolean eliminado = productService.deleteProduct(id);

        if(eliminado){
            return ResponseEntity.status(HttpStatus.OK).body("Producto eliminado correctamente");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }




 // ------------------ Endpoints de consulta a la BDD ------------------------

    // Es el endpoint para recurperar todos los Productos
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }
    }

    //Obtener un producto por su ID de producto
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        Optional<Product> prod = productService.getProductById(id);

        if(prod.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.ok(prod.get());
        }
    }

    //Obtener todos los productos que empiecen por la String facilitada y que tienen el estatus en true
    @GetMapping("/products/search/{name}")
    public ResponseEntity<List<ProductRequesteDto>> getProductsByName(@PathVariable String name) {
        List<Product> products = productService.getProductsByName(name);

        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }else{
            List<ProductRequesteDto> productsDto = products.stream().map(ProductRequesteDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(productsDto);
        }
    }

    @GetMapping("/products/search/condition")
    public ResponseEntity<List<ProductRequesteDto>> searchByCondition(@RequestParam ProductCondition condition){

        List<Product> products = productService.searchByCondition(condition);

        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<ProductRequesteDto> productsDto = products.stream().map(ProductRequesteDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(productsDto);
    }

    @GetMapping("/products/search/orderRating")
    public ResponseEntity<List<ProductRequesteDto>> orderByRating(@RequestParam String camp, @RequestParam String order){

        List<Product> products = productService.orderByRating(order);

        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<ProductRequesteDto> productsDto = products.stream().map(ProductRequesteDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(productsDto);
    }

}