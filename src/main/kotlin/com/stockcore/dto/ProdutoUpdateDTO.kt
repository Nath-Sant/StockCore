package com.stockcore.dto

data class ProdutoUpdateDTO(
    val nome: String,
    val tipoId: Long,
    val quantidade: Int,
    val descricao: String?
)
