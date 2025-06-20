package com.stockcore.dto

data class ProdutoUpdateDTO(
    val nome: String,
    val tipoId: Int,
    val quantidade: Int,
    val descricao: String?
)
