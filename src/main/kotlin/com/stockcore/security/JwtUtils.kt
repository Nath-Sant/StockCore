package com.stockcore.security

import com.stockcore.model.Usuario
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import java.util.Base64

@Component
class JwtUtils (
    @Value("\${stockcore.jwt.secret}") // Injeta a chave do application.properties
    private val secretString: String // String da chave Base64
){
    private val SECRET_KEY: SecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretString))

    fun generateToken(user: Usuario): String {
        val claims = mapOf(
            "role" to user.role.name
        )

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.nome)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas de validade
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Use a chave gerada
            .compact()
    }

    fun extractUsername(token: String): String {
        return getClaims(token).subject
    }

    fun extractRole(token: String): String {
        return getClaims(token).get("role", String::class.java)
    }

    fun validateToken(token: String, user: Usuario): Boolean {
        val username = extractUsername(token)
        return (username == user.nome && !isTokenExpired(token))
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder() // Use parserBuilder() para a API mais recente
            .setSigningKey(SECRET_KEY) // Use a chave gerada
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getClaims(token).expiration
        return expiration.before(Date())
    }
}