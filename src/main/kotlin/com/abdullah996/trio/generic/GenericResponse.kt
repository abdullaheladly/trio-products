package com.abdullah996.trio.generic

import java.time.LocalDateTime

data class GenericResponse<T>(
        val data: T? =null,
        val message: String? = null,
        val status: String?=null,
        val timestamp: LocalDateTime?=null,
        val details:String?=null

        )