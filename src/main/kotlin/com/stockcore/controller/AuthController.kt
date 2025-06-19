package com.stockcore.controller

import com.stockcore.security.JwtUtil
import com.stockcore.security.LoginRequest
import com.stockcore.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UsuarioRepository,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.username, request.password)
            )

            val user = userRepository.findByNome(request.username)
                ?: return ResponseEntity.badRequest().body("Usuário não encontrado")

            val token = jwtUtil.generateToken(user)

            ResponseEntity.ok(mapOf("token" to token))

        } catch (ex: Exception) {
            ResponseEntity.status(401).body("Credenciais inválidas")
        }
    }
}
