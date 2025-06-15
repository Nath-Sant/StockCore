package com.stockcore.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "movimentacao")
data class Movimentacao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    val produto: Produto,

    @Column(nullable = false)
    val quantidade: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val tipoMovimentacao: TipoMovimentacao,

    @Column(nullable = false)
    val dataHora: LocalDateTime = LocalDateTime.now(),

    val origem: String? = null
)
