package com.abdullah996.trio.auth.controller

import com.abdullah996.trio.auth.model.LoginRequest
import com.abdullah996.trio.auth.model.LoginResponse
import com.abdullah996.trio.auth.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
class AuthController(private val authService: AuthService) {

    @GetMapping("/test1")
    fun test1(): String {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val username: String = authentication.name
        return "Welcome, $username!"

    }


    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): LoginResponse {
        return LoginResponse(authService.createToken(loginRequest))
    }


    @PostMapping("/register")
    fun register(@ModelAttribute loginRequest: LoginRequest): ResponseEntity<Any> {

        authService.registerUser(loginRequest.username, loginRequest.password)

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}