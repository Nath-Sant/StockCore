package com.stockcore.dto

import java.time.LocalDateTime

data class MovimentacaoDTO(
    val id: Int,
    val produtoNome: String,
    val quantidade: Int,
    val tipoMovimentacao: String,
    val origem: String?
)
