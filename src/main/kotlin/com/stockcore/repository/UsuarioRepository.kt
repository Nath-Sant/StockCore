package com.stockcore.repository

import com.stockcore.model.*
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository : JpaRepository<Usuario, Long> {
    fun findByNomeContainingIgnoreCase(nome: String): List<Usuario>
    fun findByNome(nome: String): Usuario?
}


