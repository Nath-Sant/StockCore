package com.stockcore.dto

import com.stockcore.model.Role

data class UsuarioUpdateDTO(
    val nome: String,
    val senha: String,
    val role: Role
)
