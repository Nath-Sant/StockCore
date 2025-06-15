package com.stockcore.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Movimentacao(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    val produto: Produto,

    val quantidade: Int,

    val tipo: String,  // ENTRADA ou SAIDA

    val dataHora: LocalDateTime = LocalDateTime.now()
)
