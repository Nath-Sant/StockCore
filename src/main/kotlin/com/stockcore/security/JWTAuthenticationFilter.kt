package com.stockcore.security

import com.stockcore.repository.UsuarioRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTAuthenticationFilter(
    private val jwtUtils: JwtUtils,
    private val userRepository: UsuarioRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            val username = jwtUtils.extractUsername(token)

            if (SecurityContextHolder.getContext().authentication == null) {
                val user = userRepository.findByNome(username)

                if (user != null && jwtUtils.validateToken(token, user)) {
                    val authorities = listOf(RoleAuthority(user.role))

                    val auth = UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        authorities
                    )
                    auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = auth
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}
