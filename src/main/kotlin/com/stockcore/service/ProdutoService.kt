package com.stockcore.service

import com.stockcore.dto.ProdutoCreateDTO
import com.stockcore.dto.ProdutoDTO
import com.stockcore.dto.ProdutoUpdateDTO
import com.stockcore.model.Produto
import com.stockcore.repository.ProdutoRepository
import com.stockcore.repository.TipoProdutoRepository
import org.springframework.stereotype.Service

@Service
class ProdutoService(
    private val produtoRepository: ProdutoRepository,
    private val tipoProdutoRepository: TipoProdutoRepository
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

    fun buscarPorNome(nome: String): List<ProdutoDTO> {
        return produtoRepository.findByNomeContainingIgnoreCase(nome).map { toDTO(it) }
    }

    fun criarProduto(dto: ProdutoCreateDTO): ProdutoDTO {
        val tipo = tipoProdutoRepository.findById(dto.tipoId.toLong())
            .orElseThrow { RuntimeException("TipoProduto com id ${dto.tipoId} não encontrado") }

        val produto = Produto(
            nome = dto.nome,
            tipo = tipo,
            quantidade = dto.quantidade,
            descricao = dto.descricao
        )

        return toDTO(produtoRepository.save(produto))
    }

    fun atualizarProduto(id: Long, dto: ProdutoUpdateDTO): ProdutoDTO {
        val produtoExistente = produtoRepository.findById(id)
            .orElseThrow { RuntimeException("Produto não encontrado") }

        val tipo = tipoProdutoRepository.findById(dto.tipoId.toLong())
            .orElseThrow { RuntimeException("TipoProduto com id ${dto.tipoId} não encontrado") }

        val produtoAtualizado = produtoExistente.copy(
            nome = dto.nome,
            tipo = tipo,
            quantidade = dto.quantidade,
            descricao = dto.descricao
        )

        return toDTO(produtoRepository.save(produtoAtualizado))
    }


    fun deletarProduto(id: Long) {
        produtoRepository.deleteById(id)
    }
}
