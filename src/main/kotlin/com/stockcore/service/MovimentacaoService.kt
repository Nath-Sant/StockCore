package com.stockcore.service

import com.stockcore.dto.MovimentacaoDTO
import com.stockcore.model.Movimentacao
import com.stockcore.repository.MovimentacaoRepository
import com.stockcore.repository.ProdutoRepository
import org.springframework.stereotype.Service

@Service
class MovimentacaoService(
    private val movimentacaoRepository: MovimentacaoRepository,
    private val produtoRepository: ProdutoRepository
) {

    private fun toDTO(movimentacao: Movimentacao): MovimentacaoDTO {
        return MovimentacaoDTO(
            id = movimentacao.id,
            produtoNome = movimentacao.produto.nome,
            quantidade = movimentacao.quantidade,
            tipoMovimentacao = movimentacao.tipoMovimentacao.name,
            dataHora = movimentacao.dataHora,
            origem = movimentacao.origem
        )
    }

    fun listarTodas(): List<MovimentacaoDTO> {
        return movimentacaoRepository.findAll().map { toDTO(it) }
    }

    fun listarPorProduto(produtoId: Long): List<MovimentacaoDTO> {
        return movimentacaoRepository.findByProdutoId(produtoId).map { toDTO(it) }
    }

    fun buscarPorId(id: Long): MovimentacaoDTO {
        val movimentacao = movimentacaoRepository.findById(id)
            .orElseThrow { RuntimeException("Movimentação não encontrada") }
        return toDTO(movimentacao)
    }

    fun criarMovimentacao(movimentacao: Movimentacao): MovimentacaoDTO {
        val produto = produtoRepository.findById(movimentacao.produto.id)
            .orElseThrow { RuntimeException("Produto com id ${movimentacao.produto.id} não encontrado") }

        val novaQuantidade = produto.quantidade + movimentacao.quantidade
        if (novaQuantidade < 0) throw RuntimeException("Estoque insuficiente para saída")

        produto.quantidade = novaQuantidade
        produtoRepository.save(produto)

        return toDTO(movimentacaoRepository.save(movimentacao))
    }

    fun atualizarMovimentacao(id: Long, movimentacao: Movimentacao): MovimentacaoDTO {
        val existente = movimentacaoRepository.findById(id)
            .orElseThrow { RuntimeException("Movimentação não encontrada") }

        return toDTO(movimentacaoRepository.save(movimentacao.copy(id = existente.id)))
    }

    fun deletarMovimentacao(id: Long) {
        movimentacaoRepository.deleteById(id)
    }
}
