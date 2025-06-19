package com.stockcore.model

import jakarta.persistence.*

@Entity
@Table(name = "usuario")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    val idUsuario: Long = 0,

    @Column(nullable = false, unique = true)
    val nome: String,

    @Column(nullable = false)
    val senha: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role
)
