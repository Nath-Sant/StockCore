package com.stockcore.model

import jakarta.persistence.*

@Entity
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val nome: String,
    val email: String,

    var senha: String,  // Salva criptografada!

    val role: String = "FUNCIONARIO"  // Exemplo de role
)
