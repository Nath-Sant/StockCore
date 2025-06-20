package com.stockcore.service

import com.stockcore.dto.MovimentacaoCreateDTO
import com.stockcore.dto.MovimentacaoDTO
import com.stockcore.model.Movimentacao
import com.stockcore.model.TipoMovimentacao
import com.stockcore.repository.MovimentacaoRepository
import com.stockcore.repository.ProdutoRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import com.stockcore.exception.ResourceNotFoundException
import com.stockcore.exception.BadRequestException

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
            .orElseThrow { ResourceNotFoundException("Movimentação com ID $id não encontrada") }
        return toDTO(movimentacao)
    }

    fun criarMovimentacao(dto: MovimentacaoCreateDTO): MovimentacaoDTO {
        val produto = produtoRepository.findById(dto.produtoId.toLong())
            .orElseThrow { ResourceNotFoundException("Produto com ID ${dto.produtoId} não encontrado") }

        val novaQuantidade = when (dto.tipoMovimentacao.toString()) {
            "ENTRADA" -> produto.quantidade + dto.quantidade
            "SAIDA" -> produto.quantidade - dto.quantidade
            else -> throw BadRequestException("Tipo de movimentação inválido: ${dto.tipoMovimentacao}. Use 'ENTRADA' ou 'SAIDA'.") // Usa BadRequestException
        }

        if (novaQuantidade < 0) throw BadRequestException("Estoque insuficiente para saída. Quantidade disponível: ${produto.quantidade}.")

        produto.quantidade = novaQuantidade
        produtoRepository.save(produto)

        val movimentacao = Movimentacao(
            produto = produto,
            quantidade = dto.quantidade,
            tipoMovimentacao = TipoMovimentacao.valueOf(dto.tipoMovimentacao.toString()),
            dataHora = LocalDateTime.now(),
            origem = dto.origem
        )

        return toDTO(movimentacaoRepository.save(movimentacao))
    }

    fun deletarMovimentacao(id: Long) {
        if (!movimentacaoRepository.existsById(id)) {
            throw ResourceNotFoundException("Movimentação com ID $id não encontrada para exclusão.")
        }
        movimentacaoRepository.deleteById(id)
    }
}