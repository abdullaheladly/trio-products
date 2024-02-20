package com.abdullah996.trio.loans.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
data class LoanEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id:Int =0,
        val name:String ="",
        val type :LoanTypes= LoanTypes.SMALL,
        val providerName:String= ""

)
