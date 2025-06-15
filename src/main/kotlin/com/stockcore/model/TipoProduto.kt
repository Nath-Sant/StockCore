package com.stockcore.model

import jakarta.persistence.*

@Entity
@Table(name = "tipoProduto")
data class TipoProduto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoProduto")
    val idTipoProduto: Long = 0,

    @Column(nullable = false, unique = true)
    val nomeTipo: String
)
