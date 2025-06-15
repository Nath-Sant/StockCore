package com.stockcore.service

import com.stockcore.dto.TipoProdutoDTO
import com.stockcore.model.TipoProduto
import com.stockcore.repository.TipoProdutoRepository
import org.springframework.stereotype.Service

@Service
class TipoProdutoService(
    private val tipoProdutoRepository: TipoProdutoRepository
) {

    private fun toDTO(tipoProduto: TipoProduto): TipoProdutoDTO {
        return TipoProdutoDTO(
            id = tipoProduto.id,
            nomeTipo = tipoProduto.nomeTipo
        )
    }

    fun listarTodos(): List<TipoProdutoDTO> {
        return tipoProdutoRepository.findAll().map { toDTO(it) }
    }

    fun buscarPorId(id: Long): TipoProdutoDTO {
        val tipo = tipoProdutoRepository.findById(id)
            .orElseThrow { RuntimeException("TipoProduto não encontrado") }
        return toDTO(tipo)
    }

    fun criarTipo(tipoProduto: TipoProduto): TipoProdutoDTO {
        return toDTO(tipoProdutoRepository.save(tipoProduto))
    }

    fun atualizarTipo(id: Long, tipoProduto: TipoProduto): TipoProdutoDTO {
        val existente = tipoProdutoRepository.findById(id)
            .orElseThrow { RuntimeException("TipoProduto não encontrado") }

        val tipoAtualizado = tipoProduto.copy(id = existente.id)
        return toDTO(tipoProdutoRepository.save(tipoAtualizado))
    }

    fun deletarTipo(id: Long) {
        tipoProdutoRepository.deleteById(id)
    }
}
