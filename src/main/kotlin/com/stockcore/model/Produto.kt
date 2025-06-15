package com.stockcore.model

import jakarta.persistence.*

@Entity
@Table(name = "produto")
data class Produto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduto")
    val idProduto: Long = 0,

    @Column(nullable = false)
    val nome: String,

    @ManyToOne
    @JoinColumn(name = "idTipoProduto", nullable = false)
    val tipo: TipoProduto,

    @Column(nullable = false)
    var quantidade: Int,

    @Column(columnDefinition = "TEXT")
    val descricao: String? = null,

)
