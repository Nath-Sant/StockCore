package com.stockcore.dto

import com.stockcore.model.Role

data class UsuarioCreateDTO(
    val nome: String,
    val senha: String,
    val role: Role
)
