package com.abdullah996.trio.products.controller

import com.abdullah996.trio.generic.GenericResponse
import com.abdullah996.trio.products.model.AddLoanPayload
import com.abdullah996.trio.products.model.Product
import com.abdullah996.trio.products.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@Validated
class ProductController(private val productService: ProductService) {

    @GetMapping("/products")
    fun getAllProducts():GenericResponse<List<Product>>{

        return GenericResponse<List<Product>>(data = productService.getAllProducts(),status = HttpStatus.OK.reasonPhrase)
    }

    @GetMapping("/product/{id}")
    fun getProductById(@PathVariable id:Int):GenericResponse<Product>{
        return GenericResponse<Product>(data = productService.getProductById(id),status = HttpStatus.OK.reasonPhrase)
    }

    @PostMapping("/products")
    fun createNewProduct(@Valid @RequestBody product: Product):GenericResponse<Any>{
        productService.createNewProduct(product)
        return GenericResponse<Any>(status = HttpStatus.CREATED.reasonPhrase, message = "Product Created Successfully")
    }

    @PostMapping("/products/addLoan")
    fun addLoanToExistingProduct(@RequestBody addLoanPayload: AddLoanPayload): GenericResponse<Any> {
        productService.addLoanToProduct(addLoanPayload.productId, addLoanPayload.loadId)
        return GenericResponse<Any>(status = HttpStatus.OK.reasonPhrase, message = "Loan Created Successfully")
    }
}