package com.stockcore.security

import com.stockcore.model.Usuario
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    private val SECRET_KEY = "secreta123456"

    fun generateToken(user: Usuario): String {
        val claims = mapOf(
            "role" to user.role.name
        )

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.nome)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY.toByteArray())
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
        return Jwts.parser()
            .setSigningKey(SECRET_KEY.toByteArray())
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getClaims(token).expiration
        return expiration.before(Date())
    }
}
