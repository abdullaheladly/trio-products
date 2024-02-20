package com.abdullah996.trio.loans.controller

import com.abdullah996.trio.categories.model.Category
import com.abdullah996.trio.generic.GenericResponse
import com.abdullah996.trio.loans.model.Loan
import com.abdullah996.trio.loans.model.LoanEntity
import com.abdullah996.trio.loans.service.LoansService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class LoansController(private val loansService: LoansService) {

    @GetMapping("/loans")
    fun retrieveAllLoans():GenericResponse<List<LoanEntity>>{
        return GenericResponse<List<LoanEntity>>( data = loansService.getAllLoans(),status = HttpStatus.OK.reasonPhrase)

    }

    @GetMapping("/loans/{id}")
    fun retrieveLoanById(@PathVariable id:Int):GenericResponse<LoanEntity>{
        return GenericResponse<LoanEntity>( data = loansService.findLoanById(id),status = HttpStatus.OK.reasonPhrase)
    }

    @DeleteMapping("/loans/{id}")
    fun deleteLoan(@PathVariable id:Int):GenericResponse<Any>{
        loansService.deleteLoan(id)
        return GenericResponse<Any>(status = HttpStatus.OK.reasonPhrase, message = "Loan Deleted successfully")

    }

    @PutMapping("/loans/{id}")
    fun updateLoan(@RequestBody loanEntity: LoanEntity, id: Int):GenericResponse<Any>{
        val updatedLoan=loanEntity.copy(id = id)
        loansService.updateLoan(updatedLoan)
        return GenericResponse<Any>(status = HttpStatus.OK.reasonPhrase, message = "Loan Updated Successfully")
    }

    @PostMapping("/loan")
    fun createLoan(@RequestBody loan: Loan):GenericResponse<Any>{
        loansService.addLoan(loan)
        return GenericResponse<Any>(status = HttpStatus.CREATED.reasonPhrase, message = "Loan Created Successfully")
    }


}