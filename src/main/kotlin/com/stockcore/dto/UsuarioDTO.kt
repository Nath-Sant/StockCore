package com.stockcore.dto

import com.stockcore.model.Role

data class UsuarioDTO(
    val id: Int,
    val nome: String,
    val role: Role
)
