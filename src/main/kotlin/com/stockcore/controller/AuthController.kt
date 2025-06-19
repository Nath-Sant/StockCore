package com.stockcore.controller

import com.stockcore.repository.UsuarioRepository
import com.stockcore.security.JwtUtils
import com.stockcore.security.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepository: UsuarioRepository,
    private val jwtUtils: JwtUtils
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        val user = userRepository.findAll().firstOrNull { it.nome.equals(request.username, ignoreCase = true) }
            ?: return ResponseEntity.status(401).body("Usuário não encontrado")

        // Aqui se você quiser, pode fazer a validação de senha criptografada no futuro
        if (user.senha != request.password) {
            return ResponseEntity.status(401).body("Senha incorreta")
        }

        val token = jwtUtils.generateToken(user)
        return ResponseEntity.ok(mapOf("token" to token))
    }
}
