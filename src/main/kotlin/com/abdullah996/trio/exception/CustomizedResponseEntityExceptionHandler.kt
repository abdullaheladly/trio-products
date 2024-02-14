package com.abdullah996.trio.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class CustomizedResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {


    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                LocalDateTime.now(),
                ex.message ?: "Internal Server Error",
                request.getDescription(false)
        )

        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }



    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUserNotFoundException(ex: Exception, request: WebRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                LocalDateTime.now(),
                ex.message?:"User Not Found", request.getDescription(false)
        )

        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }


     override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest): ResponseEntity<Any>? {
         val bindingResult = ex.bindingResult
         val fieldErrors = bindingResult.fieldErrors

         val missingParams = fieldErrors.map { it.field }

        val errorDetails = ErrorDetails(
                LocalDateTime.now(),
                missingParams.joinToString(", "), request.getDescription(false) ?: ""
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

}