package com.botiga.com_botiga.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.botiga.com_botiga.DTO.ProductRequesteDto;
import com.botiga.com_botiga.model.Product;

@Component
public class Mapper {
    public static ProductRequesteDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductRequesteDto(product);
    }

    public static Product toEntity(ProductRequesteDto dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();

        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());
        product.setPrice(dto.getPrice());
        product.setRating(dto.getRating());
        product.setCondition(dto.getCondition());

        return product;
    }

    public static List<ProductRequesteDto> toDtoList(List<Product> productList) {

    if (productList == null || productList.isEmpty()) {
        return new ArrayList<>();
    }

        List<ProductRequesteDto> dtoList = new ArrayList<>();

        for (Product product : productList) {

            ProductRequesteDto dto = new ProductRequesteDto();

            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setStock(product.getStock());
            dto.setPrice(product.getPrice());
            dto.setRating(product.getRating());
            dto.setCondition(product.getCondition());

            dtoList.add(dto);
        }

        return dtoList;
    }

    public static List<Product> toEntityList(List<ProductRequesteDto> dtoList) {

        if (dtoList == null || dtoList.isEmpty()) {
            return new ArrayList<>();
        }

        List<Product> products = new ArrayList<>();

        for (ProductRequesteDto dto : dtoList) {

            Product product = new Product();

            product.setId(dto.getId());
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setStock(dto.getStock());
            product.setPrice(dto.getPrice());
            product.setRating(dto.getRating());
            product.setCondition(dto.getCondition());

            products.add(product);
        }

        return products;
    }
}
