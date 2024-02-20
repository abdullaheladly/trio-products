package com.abdullah996.trio.categories.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


@Entity
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id:Int = 0 ,
        @field:Size(min = 3)
        val name:String = "",
        @field:NotEmpty
        val image:String = ""
)
