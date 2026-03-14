package com.botiga.com_botiga.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.botiga.com_botiga.DTO.ErrorDto;
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
    public  ResponseEntity<?> postPodcut(@RequestBody Product product) {
        Product resultado = productService.postroduct(product);
        
        if(resultado != null){
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Error al crear"));
        }
    }

    //Modifica todo un producto
    @PatchMapping("/product/{id}")
    public ResponseEntity<?> patchProduct(@PathVariable() long id, @RequestBody Product product){
        Product result = productService.patchProduct(id, product);
        
        if(result == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Error al actualizar"));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    //Modifica el stock de un producto
    @PatchMapping("/product/{id}/stock")
    public ResponseEntity<?> patchEstoc(@PathVariable() long id, @RequestParam() Integer stock){
        Product resultado = productService.patchEstoc(id, stock);
        
        if(resultado == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Error al actulizar estoc"));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }
    }

    //Modifica el precio de un producto
    @PatchMapping("/product/{id}/price")
    public ResponseEntity<?> patchPrice(@PathVariable() long id, @RequestParam() BigDecimal price){
        Product resultado = productService.patchPrice(id, price);
        
        if(resultado == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Error al actulizar price"));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }
    }

    //Borrado logico de un producto, modifica el status pasandolo a false (modo de hivernación)
    @PatchMapping("/product/{id}/status")
    public ResponseEntity<?> patchStatus(@PathVariable() long id, @RequestParam() Boolean status){
        Product resultado = productService.patchStatus(id, status);
        
        if(resultado == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Error al actualizar status"));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }
    }
    
    //Borrado fisico de un producto
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){

        boolean eliminado = productService.deleteProduct(id);

        if(eliminado){
            return ResponseEntity.status(HttpStatus.OK).body("Producto eliminado correctamente");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Error al borrar"));
    }




 // ------------------ Endpoints de consulta a la BDD ------------------------

    // Es el endpoint para recurperar todos los Productos
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(products);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }
    }

    //Obtener un producto por su ID de producto
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        Optional<Product> prod = productService.getProductById(id);

        if(prod.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Error al obtener producto por id:" + id));
        }else{
            return ResponseEntity.ok(prod.get());
        }
    }

    //Obtener todos los productos que empiecen por la String facilitada y que tienen el estatus en true
    @GetMapping("/products/search/{name}")
    public ResponseEntity<List<ProductRequesteDto>> getProductsByName(@PathVariable String name) {
        List<Product> products = productService.getProductsByName(name);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else{
            List<ProductRequesteDto> productsDto = products.stream().map(ProductRequesteDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(productsDto);
        }
    }
    

    @GetMapping("/products/search/condition")
    public ResponseEntity<List<ProductRequesteDto>> searchByCondition(@RequestParam ProductCondition condition){

        List<Product> products = productService.searchByCondition(condition);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<ProductRequesteDto> productsDto = products.stream().map(ProductRequesteDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(productsDto);
    }

    @GetMapping("/products/search/order")//order?camp=rating&order=desc
    public ResponseEntity<List<ProductRequesteDto>> order(@RequestParam String camp,@RequestParam String order){

        List<Product> products = productService.order(camp, order);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        //Esta linea hace la conversion entity-dto, la usamos porque la buscamos como medida temporal antes de que se explicara en classe el mapping
        List<ProductRequesteDto> productsDto = products.stream().map(ProductRequesteDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(productsDto);
    }


    @GetMapping("/products/search/searchOrder")
    public ResponseEntity<List<ProductRequesteDto>> searchProducts(@RequestParam BigDecimal min,@RequestParam BigDecimal max,@RequestParam String camp,@RequestParam(defaultValue = "asc") String order,@RequestParam(defaultValue = "10") int limit,@RequestParam(required = false) String prefix) throws Exception {
        List<ProductRequesteDto> productRequesteDtos = productService.searchProducts(min, max, camp, order, limit, prefix);

        if(productRequesteDtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(productRequesteDtos);
        }
        
    }

    @GetMapping("/products/search/qualityPrice")
    public ResponseEntity<List<ProductRequesteDto>> qualityPrice(){

        List<ProductRequesteDto> request = productService.searchQualityPrice();

        if(request.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(request);
        }

    }

    @GetMapping("/products/search/bestNews")
    public ResponseEntity<List<ProductRequesteDto>> bestNews(){
        List<ProductRequesteDto> dtos = productService.bestNews();

        if(dtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(dtos);
        }
    }

    @GetMapping("/productes/search/pages")
    public ResponseEntity<List<ProductRequesteDto>> getPages(@RequestParam int page) {
        List<ProductRequesteDto> dtos = productService.searchInBlock(page);
        if(dtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(dtos);
        }
    }
    

}