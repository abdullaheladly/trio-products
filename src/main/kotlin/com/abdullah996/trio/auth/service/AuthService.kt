package com.abdullah996.trio.auth.service

import com.abdullah996.trio.auth.model.LoginRequest
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.stereotype.Service
import java.time.Instant
import javax.sql.DataSource


@Service
class AuthService constructor(private val jwtEncoder: JwtEncoder, private val userDetailsService: UserDetailsService, private val passwordEncoder: PasswordEncoder,private val dataSource: DataSource) {


     fun createToken(authentication: LoginRequest): String {
        val user= userDetailsService.loadUserByUsername(authentication.username)
                ?: throw Exception("${authentication.username} not found")
        if (!passwordEncoder.matches(authentication.password, user.password)) {
            throw Exception("Invalid password for user ${authentication.username}")
        }
        val scopes = determineScopes(user)
        val claims= JwtClaimsSet.builder().issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60*30))
                .subject(authentication.username)
                .claim("scope",scopes)
                .build()
        val paramter= JwtEncoderParameters.from(claims)
        return jwtEncoder.encode(paramter).tokenValue

    }


    private fun determineScopes(userDetails: UserDetails): Set<String> {
        val roles = userDetails.authorities.map { it.authority }
        return when {
            roles.contains("ROLE_ADMIN") -> setOf("ADMIN")
            roles.contains("ROLE_USER") -> setOf("USER")
            else -> emptySet()
        }
    }

    fun registerUser(username:String,password:String){
        try {
            val existUser= userDetailsService.loadUserByUsername(username)
            if (existUser!=null){
                throw Exception("username already taken")
            }
        }catch (ex:UsernameNotFoundException){
            val user = User.withUsername(username)
                    .password(password)
                    .passwordEncoder{ pass-> passwordEncoder.encode(pass)}
                    .roles("USER")
                    .build()

            val jdbcUserDetailsManager= JdbcUserDetailsManager(dataSource)
            jdbcUserDetailsManager.createUser(user)
        }



    }
}