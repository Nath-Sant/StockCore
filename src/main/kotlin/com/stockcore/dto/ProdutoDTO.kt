package com.stockcore.dto

data class ProdutoDTO(
    val id: Long,
    val nome: String,
    val tipo: String,
    val quantidade: Int,
    val descricao: String?,
)
