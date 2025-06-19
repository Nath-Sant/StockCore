package com.stockcore.repository

import com.stockcore.model.Produto
import com.stockcore.model.TipoProduto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProdutoRepository : JpaRepository<Produto, Long> {
    fun findByTipoNomeTipo(nomeTipo: String): List<Produto>
    fun findByNomeContainingIgnoreCase(nome: String): List<Produto>
}
