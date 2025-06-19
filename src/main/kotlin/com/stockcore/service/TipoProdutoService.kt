package com.stockcore.service

import com.stockcore.dto.TipoProdutoCreateDTO
import com.stockcore.dto.TipoProdutoDTO
import com.stockcore.dto.TipoProdutoUpdateDTO
import com.stockcore.model.TipoProduto
import com.stockcore.repository.TipoProdutoRepository
import org.springframework.stereotype.Service

@Service
class TipoProdutoService(
    private val tipoProdutoRepository: TipoProdutoRepository
) {

    private fun toDTO(tipoProduto: TipoProduto): TipoProdutoDTO {
        return TipoProdutoDTO(
            id = tipoProduto.idTipoProduto,
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

    fun criarTipo(dto: TipoProdutoCreateDTO): TipoProdutoDTO {
        val tipo = TipoProduto(nomeTipo = dto.nomeTipo)
        return toDTO(tipoProdutoRepository.save(tipo))
    }

    fun atualizarTipo(id: Long, dto: TipoProdutoUpdateDTO): TipoProdutoDTO {
        val existente = tipoProdutoRepository.findById(id)
            .orElseThrow { RuntimeException("TipoProduto não encontrado") }

        val tipoAtualizado = existente.copy(nomeTipo = dto.nomeTipo)
        return toDTO(tipoProdutoRepository.save(tipoAtualizado))
    }


    fun deletarTipo(id: Long) {
        tipoProdutoRepository.deleteById(id)
    }
}
