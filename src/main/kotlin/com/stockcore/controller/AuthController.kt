package com.stockcore.controller

import com.stockcore.dto.LoginRequest
import com.stockcore.security.JWTUtil
import com.stockcore.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@RestController
@RequestMapping("/auth")
class AuthController(
    val usuarioRepository: UsuarioRepository,
    val jwtUtil: JWTUtil
) {
    val passwordEncoder = BCryptPasswordEncoder()

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val usuario = usuarioRepository.findByEmail(loginRequest.email)
            ?: return ResponseEntity.badRequest().body("Usuário não encontrado")

        return if (passwordEncoder.matches(loginRequest.senha, usuario.senha)) {
            val token = jwtUtil.generateToken(usuario.email)
            ResponseEntity.ok(mapOf("token" to token))
        } else {
            ResponseEntity.status(401).body("Senha incorreta")
        }
    }
}
