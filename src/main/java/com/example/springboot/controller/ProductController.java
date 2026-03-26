package com.example.springboot.controller;

import com.example.springboot.dto.ProductRecordDTO;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDTO, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

//    @GetMapping("/products/{id}")
//    public ResponseEntity<ProductModel> getProductById(@PathVariable(value="id") UUID id){
//        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findById(id).orElseThrow());
//    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
    }
        return ResponseEntity.status(HttpStatus.OK).body(productOptional.get());
    }

    @PutMapping("/products/{id}")
    public HttpEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDTO productRecordDTO){
        Optional<ProductModel> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        var productModel = productOptional.get();
        BeanUtils.copyProperties(productRecordDTO, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable(value = "id") UUID id) {
        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }




}
