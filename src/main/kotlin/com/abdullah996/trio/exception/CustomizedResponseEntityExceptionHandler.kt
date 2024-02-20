package com.abdullah996.trio.exception

import com.abdullah996.trio.categories.exception.CategoryNotFoundException
import com.abdullah996.trio.generic.GenericResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
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
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<GenericResponse<Any>> {
        val errorDetails = GenericResponse<Any>(
                status = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
                timestamp = LocalDateTime.now(),
                message = ex.message ?: "Internal Server Error",
                details = request.getDescription(false)
        )

        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }



    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUserNotFoundException(ex: Exception, request: WebRequest): ResponseEntity<GenericResponse<Any>> {
        val errorDetails = GenericResponse<Any>(
                status = HttpStatus.NOT_FOUND.reasonPhrase,
                timestamp = LocalDateTime.now(),
               message =  ex.message?:"User Not Found", details = request.getDescription(false)
        )

        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(CategoryNotFoundException::class)
    fun handleCategoryNotFoundException(ex: Exception, request: WebRequest): ResponseEntity<GenericResponse<Any>> {
        val errorDetails = GenericResponse<Any>(
                status = HttpStatus.NOT_FOUND.reasonPhrase,
                timestamp =  LocalDateTime.now(),
               message =  ex.message?:"User Not Found", details = request.getDescription(false)
        )

        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }
     override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest): ResponseEntity<Any> {
         val bindingResult = ex.bindingResult
         val fieldErrors = bindingResult.fieldErrors
         val missingParams = fieldErrors.map { it.field }
         val errorDetails = GenericResponse<Any>(
                 status = HttpStatus.BAD_REQUEST.reasonPhrase,
                timestamp = LocalDateTime.now(),
                message = missingParams.joinToString(", "), details = request.getDescription(false) ?: ""
         )
         return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

}