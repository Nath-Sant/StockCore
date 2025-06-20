package com.stockcore.repository

import com.stockcore.model.Produto
import com.stockcore.model.TipoProduto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TipoProdutoRepository : JpaRepository<TipoProduto, Long> {
    fun findByNome(nome: String): TipoProduto?
}
