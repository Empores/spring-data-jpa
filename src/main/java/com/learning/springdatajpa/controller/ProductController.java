package com.learning.springdatajpa.controller;

import com.learning.springdatajpa.entity.Product;
import com.learning.springdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public Product addProduct(@RequestBody Product product){
        return service.saveProduct(product);
    }

    @GetMapping
    public List<Product> getProducts(){
        return service.findAll();
    }

    @GetMapping("/byid/{id}")
    public Product getProductById(@PathVariable int id){
        return service.findById(id);
    }

    /*
    @GetMapping("/byname/{name}")
    public Product getProductByName(@PathVariable String name){
        return service.findByName(name);
    }

    @GetMapping("/bytype/{productType}")
    public List<Product> getProductByType(@PathVariable  String productType){
        return service.findByProductType(productType);
    }

    @GetMapping("/bypricetype/{price}/{productType}")
    public List<Product> findByPriceAndProductType(@PathVariable double price, @PathVariable String productType){
        return service.findByPriceAndProductType(price,productType);
    }
*/
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id,@RequestBody Product product){
        System.out.println(product.getProductType());
        return service.updateProduct(id,product);
    }

    @DeleteMapping("/{id}")
    public long deleteProduct(@PathVariable int id){
        return service.deleteProduct(id);
    }

    //operator scenario
/*
    @PostMapping("/search")
    public List<Product> getProductByMultiplePriceValue(@RequestBody List<Double> prices){
        return service.getProductByMultiplePriceValue(prices);
    }

    @GetMapping("/min/{minPrice}/max/{maxPrice}")
    public List<Product> getProductWithInPriceRange(@PathVariable double minPrice, @PathVariable double maxPrice){
        return service.getProductWithInPriceRange(minPrice,maxPrice);
    }

    @GetMapping("/greater/{price}")
    public List<Product> getProductWithPriceGreaterThan(@PathVariable double price){
        return service.getProductWithPriceGreaterThan(price);
    }

    @GetMapping("/less/{price}")
    public List<Product> getProductWithPriceLessThan(@PathVariable double price){
        return service.getProductWithPriceLessThan(price);
    }

    @GetMapping("/like/{name}")
    public List<Product> getProductsWithLike(@PathVariable String name){
        return  service.getProductsWithLike(name);
    }

    //sorting
    @GetMapping("/sort/{fieldName}")
    public List<Product> getProductsWithSorting(@PathVariable  String fieldName){
        return service.getProductsWithSorting(fieldName);
    }

    //pagination
    @GetMapping("/page/{offset}/{limit}")
    public Page<Product> getProductWithPagination(@PathVariable int offset, @PathVariable int limit){
        return service.getProductWithPagination(offset,limit);
    }

    //sorting and pagination
    @GetMapping("/page-sort/{fieldName}/{offset}/{limit}")
    public Page<Product> getProductsWithSortingPagination(@PathVariable String fieldName,
                                                          @PathVariable int offset,
                                                          @PathVariable int limit){
        return service.getProductsWithSortingPagination(fieldName,offset,limit);
    }
*/

}
