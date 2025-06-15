package com.stockcore.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "movimentacao")
data class Movimentacao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMovimentacao")
    val idMovimentacao: Long = 0,

    @ManyToOne
    @JoinColumn(name = "idProduto", nullable = false)
    val produto: Produto,

    @Column(nullable = false)
    val quantidade: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val tipoMovimentacao: TipoMovimentacao,

    @Column(name = "dataHora", nullable = false)
    val dataHora: LocalDateTime = LocalDateTime.now(),

    val origem: String? = null
)
