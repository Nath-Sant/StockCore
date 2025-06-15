package com.stockcore.service

import com.stockcore.dto.MovimentacaoCreateDTO
import com.stockcore.dto.MovimentacaoDTO
import com.stockcore.model.Movimentacao
import com.stockcore.model.TipoMovimentacao
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
            id = movimentacao.idMovimentacao,
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
        return movimentacaoRepository.findByProduto_IdProduto(produtoId).map { toDTO(it) }
    }

    fun buscarPorId(id: Long): MovimentacaoDTO {
        val movimentacao = movimentacaoRepository.findById(id)
            .orElseThrow { RuntimeException("Movimentação não encontrada") }
        return toDTO(movimentacao)
    }

    fun criarMovimentacao(dto: MovimentacaoCreateDTO): MovimentacaoDTO {
        val produto = produtoRepository.findById(dto.produtoId)
            .orElseThrow { RuntimeException("Produto com id ${dto.produtoId} não encontrado") }

        val novaQuantidade = when (dto.tipoMovimentacao.toString()) {
            "ENTRADA" -> produto.quantidade + dto.quantidade
            "SAIDA" -> produto.quantidade - dto.quantidade
            else -> throw RuntimeException("Tipo de movimentação inválido: ${dto.tipoMovimentacao}")
        }

        if (novaQuantidade < 0) throw RuntimeException("Estoque insuficiente para saída")

        produto.quantidade = novaQuantidade
        produtoRepository.save(produto)

        val movimentacao = Movimentacao(
            produto = produto,
            quantidade = dto.quantidade,
            tipoMovimentacao = TipoMovimentacao.valueOf(dto.tipoMovimentacao.toString()),
            dataHora = dto.dataHora,
            origem = dto.origem
        )

        return toDTO(movimentacaoRepository.save(movimentacao))
    }

    fun atualizarMovimentacao(id: Long, movimentacao: Movimentacao): MovimentacaoDTO {
        val existente = movimentacaoRepository.findById(id)
            .orElseThrow { RuntimeException("Movimentação não encontrada") }

        return toDTO(movimentacaoRepository.save(movimentacao.copy(idMovimentacao = existente.idMovimentacao)))
    }

    fun deletarMovimentacao(id: Long) {
        movimentacaoRepository.deleteById(id)
    }
}
