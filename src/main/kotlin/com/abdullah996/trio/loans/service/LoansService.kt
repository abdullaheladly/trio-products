package com.abdullah996.trio.loans.service

import com.abdullah996.trio.loans.model.Loan
import com.abdullah996.trio.loans.model.LoanEntity
import com.abdullah996.trio.loans.model.LoanTypes
import com.abdullah996.trio.loans.repo.LoansRepo
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service


@Service
class LoansService(private val loansRepo: LoansRepo) {

    fun getAllLoans():List<LoanEntity>{
       return loansRepo.findAll()
    }

    fun findLoanById(id:Int):LoanEntity{
        return loansRepo.getReferenceById(id)
    }

    fun deleteLoan(id: Int) = loansRepo.deleteById(id)

    fun updateLoan(loan: LoanEntity) = loansRepo.save(loan)

    fun addLoan(loan: Loan) {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication

        try {
            val loanType = LoanTypes.valueOf(loan.type)
            loansRepo.save(LoanEntity(name = loan.name, type = loanType, providerName = authentication.name))
        } catch (e: IllegalArgumentException) {
            throw Exception("please provide a valid loan type name")
        }
    }
}