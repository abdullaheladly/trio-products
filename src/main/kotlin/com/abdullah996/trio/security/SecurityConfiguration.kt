package com.abdullah996.trio.security

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPublicKey
import java.util.*
import javax.sql.DataSource


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class JwtConfiguration {

    val excludedPaths = arrayOf("/login","/register")


    @Bean
    fun securityFilterChain(http:HttpSecurity):SecurityFilterChain{
        http.authorizeHttpRequests {
            it.requestMatchers("/login").permitAll()
            it.requestMatchers("/register").permitAll()
            it.anyRequest().authenticated()
        }
        http.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        http.httpBasic()
        http.csrf().disable()
        http.headers().frameOptions().sameOrigin()
        http.oauth2ResourceServer().jwt()
        return http.build()
    }





    @Bean
    fun datasource() :DataSource{
        return EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScripts(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION).build()
    }


    @Bean
    fun userDetailsService(dataSource: DataSource): UserDetailsService{
        val user =User.withUsername("abdullah")
                // .password("{noop}1234")
                .password("1234")
                .passwordEncoder{ pass-> passwordEncoder().encode(pass)}
                .roles("USER")
                .build()

        val admin =User.withUsername("admin")
                // .password("{noop}1234")
                .password("1234")
                .passwordEncoder{ pass-> passwordEncoder().encode(pass)}
                .roles("ADMIN")
                .build()

        val jdbcUserDetailsManager=JdbcUserDetailsManager(dataSource)
        jdbcUserDetailsManager.createUser(user)
        jdbcUserDetailsManager.createUser(admin)

        return jdbcUserDetailsManager
    }

    @Bean
    fun passwordEncoder():BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }



    @Bean
    fun keypair():KeyPair{
        val keyPairGenerator=KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        return keyPairGenerator.genKeyPair()
    }

    @Bean
    fun rsaKey(keyPair: KeyPair):RSAKey{
        val rsaKey=RSAKey.Builder(keyPair.public as RSAPublicKey).privateKey(keyPair.private).keyID(UUID.randomUUID().toString()).build()
        return rsaKey
    }

    @Bean
    fun rsaJwkSource(rsaKey: RSAKey): JWKSource<SecurityContext> {
        val jwkSet = JWKSet(rsaKey)
        return JWKSource { jwkSelector, c ->  jwkSelector.select(jwkSet)}
    }

    @Bean
    fun jwtDecoder(rsaKey: RSAKey):JwtDecoder{
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build()
    }

    @Bean
    fun jwtEncoder(jwkSource:JWKSource<SecurityContext>):JwtEncoder{
        return NimbusJwtEncoder(jwkSource)
    }

}