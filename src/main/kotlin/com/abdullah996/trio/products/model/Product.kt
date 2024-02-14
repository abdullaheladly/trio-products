package com.abdullah996.trio.products.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id:Int = 0,
        val name:String= "",
        val description:String = "",
        val image:String = "",
        val providerName:String =""
)
