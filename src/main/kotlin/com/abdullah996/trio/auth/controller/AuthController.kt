package com.abdullah996.trio.auth.controller

import com.abdullah996.trio.auth.model.LoginRequest
import com.abdullah996.trio.auth.model.LoginResponse
import com.abdullah996.trio.auth.service.AuthService
import com.abdullah996.trio.generic.GenericResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
class AuthController(private val authService: AuthService) {

    @GetMapping("/test1")
    fun test1(): GenericResponse<String> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val username: String = authentication.name
        return GenericResponse<String>(data = "Welcome, $username!", status = HttpStatus.OK.reasonPhrase, message = "Welcome, $username!")

    }


    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): GenericResponse<LoginResponse> {
        return GenericResponse<LoginResponse>(data = LoginResponse(authService.createToken(loginRequest)), status = HttpStatus.OK.reasonPhrase, message = null)
    }


    @PostMapping("/register")
    fun register(@ModelAttribute loginRequest: LoginRequest): GenericResponse<Any> {
        authService.registerUser(loginRequest.username, loginRequest.password)
        return GenericResponse<Any>( status = HttpStatus.OK.reasonPhrase, message = "user register successfully")

    }


}