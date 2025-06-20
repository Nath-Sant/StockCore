package com.stockcore.dto

import java.time.LocalDateTime

data class MovimentacaoCreateDTO(
    val produtoId: Int,
    val quantidade: Int,
    val tipoMovimentacao: String,
    val dataHora: LocalDateTime,
    val origem: String?
)
