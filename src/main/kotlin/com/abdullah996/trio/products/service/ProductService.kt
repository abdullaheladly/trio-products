package com.abdullah996.trio.products.service

import com.abdullah996.trio.categories.service.CategoryService
import com.abdullah996.trio.loans.repo.LoansRepo
import com.abdullah996.trio.products.model.Product
import com.abdullah996.trio.products.repo.ProductRepo
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class ProductService(private val productRepo: ProductRepo,private val categoryService: CategoryService,private val loansRepo: LoansRepo) {

    fun getAllProducts():List<Product>{
       return productRepo.findAll()
    }

    fun createNewProduct(product: Product){
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val newProduct=product.copy(providerName = authentication.name, category = product.category?.id?.let { categoryService.getCategoryById(it).get() })
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

    fun addLoanToProduct(productId:Int,loanId:Int){
       val loan= loansRepo.getReferenceById(loanId)
        val product=getProductById(productId)
        val loansList=product.loans
        println(loansList.toList())
        println(loan)
        if (loansList.contains(loan)){
            throw Exception("Loan already exist")
        }else{
            loansList.add(loan)

        }
        productRepo.save(product.copy(loans = loansList))

    }
}