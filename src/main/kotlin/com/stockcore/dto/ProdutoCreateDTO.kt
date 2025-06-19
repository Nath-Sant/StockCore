package com.stockcore.dto

data class ProdutoCreateDTO(
    val nome: String,
    val tipoId: Long,
    val quantidade: Int,
    val descricao: String?
)
