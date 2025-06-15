package com.stockcore.model

import jakarta.persistence.*

@Entity
data class Produto(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val nome: String,
    val quantidade: Int
)
