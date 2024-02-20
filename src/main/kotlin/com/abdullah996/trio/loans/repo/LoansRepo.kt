package com.abdullah996.trio.loans.repo

import com.abdullah996.trio.loans.model.LoanEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoansRepo : JpaRepository<LoanEntity,Int> {
}