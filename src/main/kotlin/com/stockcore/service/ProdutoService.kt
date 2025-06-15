package com.stockcore.service

import com.stockcore.dto.ProdutoDTO
import com.stockcore.model.Produto
import com.stockcore.repository.ProdutoRepository
import org.springframework.stereotype.Service

@Service
class ProdutoService(
    private val produtoRepository: ProdutoRepository
) {

    private fun toDTO(produto: Produto): ProdutoDTO {
        return ProdutoDTO(
            id = produto.idProduto,
            nome = produto.nome,
            tipo = produto.tipo.nomeTipo,
            quantidade = produto.quantidade,
            descricao = produto.descricao,
        )
    }

    fun listarTodos(): List<ProdutoDTO> {
        return produtoRepository.findAll().map { toDTO(it) }
    }

    fun buscarPorId(id: Long): ProdutoDTO {
        val produto = produtoRepository.findById(id)
            .orElseThrow { RuntimeException("Produto não encontrado") }
        return toDTO(produto)
    }

    fun buscarPorTipo(tipoNome: String): List<ProdutoDTO> {
        return produtoRepository.findByTipoNomeTipo(tipoNome).map { toDTO(it) }
    }

    fun criarProduto(produto: Produto): ProdutoDTO {
        return toDTO(produtoRepository.save(produto))
    }

    fun atualizarProduto(id: Long, produto: Produto): ProdutoDTO {
        val existente = produtoRepository.findById(id)
            .orElseThrow { RuntimeException("Produto não encontrado") }

        val produtoAtualizado = produto.copy(idProduto = existente.idProduto)
        return toDTO(produtoRepository.save(produtoAtualizado))
    }

    fun deletarProduto(id: Long) {
        produtoRepository.deleteById(id)
    }
}
