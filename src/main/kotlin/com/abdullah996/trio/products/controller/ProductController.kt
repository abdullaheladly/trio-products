package com.abdullah996.trio.products.controller

import com.abdullah996.trio.products.model.Product
import com.abdullah996.trio.products.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class ProductController(private val productService: ProductService) {

    @GetMapping("/products")
    fun getAllProducts():List<Product>{
        return productService.getAllProducts()
    }

    @GetMapping("/product/{id}")
    fun getProductById(@PathVariable id:Int):Product{
        return productService.getProductById(id)
    }

    @PostMapping("/products")
    fun createNewProduct(@RequestBody product: Product){
        productService.createNewProduct(product)
    }
}