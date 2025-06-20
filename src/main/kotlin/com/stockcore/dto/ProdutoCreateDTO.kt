package com.stockcore.dto

data class ProdutoCreateDTO(
    val nome: String,
    val tipoId: Int,
    val quantidade: Int,
    val descricao: String?
)
