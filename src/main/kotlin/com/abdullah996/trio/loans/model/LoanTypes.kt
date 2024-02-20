package com.abdullah996.trio.loans.model

enum class LoanTypes(min:Float,max:Float) {
    SMALL(min = 1000f, max = 10000f),
    MICRO(min = 100f, max = 1000f),
    MID(min = 10000f, max = 100000f)
}