package com.stockcore.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class JWTAuthenticationFilter(private val jwtUtil: JWTUtil) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val authHeader = httpRequest.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            try {
                val claims: Claims = Jwts.parser()
                    .setSigningKey(jwtUtil.secret)
                    .parseClaimsJws(token)
                    .body

                val username = claims.subject
                if (username != null) {
                    val auth = UsernamePasswordAuthenticationToken(username, null, emptyList())
                    auth.details = WebAuthenticationDetailsSource().buildDetails(httpRequest)
                    SecurityContextHolder.getContext().authentication = auth
                }

            } catch (e: Exception) {
                println("Token inválido: ${e.message}")
            }
        }
        chain.doFilter(request, response)
    }
}
