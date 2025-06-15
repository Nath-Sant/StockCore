package com.stockcore.repository

import com.stockcore.model.*
import org.springframework.data.jpa.repository.JpaRepository

interface MovimentacaoRepository : JpaRepository<Movimentacao, Long>