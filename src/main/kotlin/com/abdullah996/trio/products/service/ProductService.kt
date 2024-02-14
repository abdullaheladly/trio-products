package com.abdullah996.trio.products.service

import com.abdullah996.trio.products.model.Product
import com.abdullah996.trio.products.repo.ProductRepo
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service


@Service
class ProductService(private val productRepo: ProductRepo) {

    fun getAllProducts():List<Product>{
       return productRepo.findAll()
    }

    fun createNewProduct(product: Product){
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val newProduct=product.copy(providerName = authentication.name)
        println(newProduct)
        productRepo.save(newProduct)
    }

    fun deleteProduct(id:Int){
        productRepo.deleteById(id)
    }

    fun getProductById(id: Int):Product{
        return productRepo.getReferenceById(id)
    }

    fun updateProduct(product: Product){
        productRepo.save(product)
    }
}