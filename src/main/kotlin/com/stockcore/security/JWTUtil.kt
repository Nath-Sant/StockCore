package com.stockcore.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil(
    @Value("\${stockcore.jwt.secret}") val secret: String,
    @Value("\${stockcore.jwt.expiration}") private val expiration: Long
) {
    fun generateToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration)
        return Jwts.builder()

            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }
}
