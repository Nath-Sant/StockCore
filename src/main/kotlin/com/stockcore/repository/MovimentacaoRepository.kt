package com.stockcore.repository

import com.stockcore.model.Movimentacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovimentacaoRepository : JpaRepository<Movimentacao, Long> {
    fun findByProduto_IdProduto(produtoId: Long): List<Movimentacao>
}
