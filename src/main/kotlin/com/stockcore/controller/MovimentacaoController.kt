package com.stockcore.controller

import com.stockcore.dto.MovimentacaoCreateDTO
import com.stockcore.dto.MovimentacaoDTO
import com.stockcore.model.Movimentacao
import com.stockcore.service.MovimentacaoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/movimentacoes")
class MovimentacaoController(
    private val movimentacaoService: MovimentacaoService
) {

    @GetMapping
    fun listarMovimentacoes(): ResponseEntity<List<MovimentacaoDTO>> {
        val movimentacoes = movimentacaoService.listarTodas()
        return ResponseEntity.ok(movimentacoes)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<MovimentacaoDTO> {
        val movimentacao = movimentacaoService.buscarPorId(id)
        return ResponseEntity.ok(movimentacao)
    }
    @GetMapping("/produto/{produtoId}")
    fun buscarMovimentacoesPorProduto(@PathVariable produtoId: Long): ResponseEntity<List<MovimentacaoDTO>> {
        val movimentacoes = movimentacaoService.listarPorProduto(produtoId)
        return ResponseEntity.ok(movimentacoes)
    }

    @PostMapping
    fun criarMovimentacao(@RequestBody dto: MovimentacaoCreateDTO): ResponseEntity<MovimentacaoDTO> {
        val novaMovimentacao = movimentacaoService.criarMovimentacao(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMovimentacao)
    }

    @DeleteMapping("/{id}")
    fun deletarMovimentacao(@PathVariable id: Long): ResponseEntity<Void> {
        movimentacaoService.deletarMovimentacao(id)
        return ResponseEntity.noContent().build()
    }
}
