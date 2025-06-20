package com.stockcore.dto

data class MovimentacaoCreateDTO(
    val produtoId: Int,
    val quantidade: Int,
    val tipoMovimentacao: String,
    val origem: String?
)
