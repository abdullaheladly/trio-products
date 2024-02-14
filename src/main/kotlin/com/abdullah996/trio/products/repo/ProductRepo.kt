package com.abdullah996.trio.products.repo

import com.abdullah996.trio.products.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepo :JpaRepository<Product,Int> {
}