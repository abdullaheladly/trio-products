package com.abdullah996.trio.products.model

import com.abdullah996.trio.categories.model.Category
import com.abdullah996.trio.loans.model.LoanEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.security.core.userdetails.UserDetails


@Entity
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id:Int = 0,
        @field:NotEmpty
        val name:String= "",
        @field:Size(min = 2)
        val description:String = "",
        val image:String = "",
        val providerName:String ="",
        @ManyToOne(fetch = FetchType.LAZY)
        val category: Category? =null,
        @ManyToMany
        val loans:MutableList<LoanEntity> = mutableListOf()
)
