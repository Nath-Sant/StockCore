package com.stockcore.controller

import com.stockcore.dto.TipoProdutoDTO
import com.stockcore.model.TipoProduto
import com.stockcore.service.TipoProdutoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tipos-produto")
class TipoProdutoController(
    private val tipoProdutoService: TipoProdutoService
) {

    @GetMapping
    fun listarTipos(): ResponseEntity<List<TipoProdutoDTO>> {
        val tipos = tipoProdutoService.listarTodos()
        return ResponseEntity.ok(tipos)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<TipoProdutoDTO> {
        val tipoProduto = tipoProdutoService.buscarPorId(id)
        return ResponseEntity.ok(tipoProduto)
    }

    @PostMapping
    fun criarTipoProduto(@RequestBody tipoProduto: TipoProduto): ResponseEntity<TipoProdutoDTO> {
        val novoTipoProduto = tipoProdutoService.criarTipo(tipoProduto)
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTipoProduto)
    }

    @PutMapping("/{id}")
    fun atualizarTipoProduto(@PathVariable id: Long, @RequestBody tipoProduto: TipoProduto): ResponseEntity<TipoProdutoDTO> {
        val tipoProdutoAtualizado = tipoProdutoService.atualizarTipo(id, tipoProduto)
        return ResponseEntity.ok(tipoProdutoAtualizado)
    }

    @DeleteMapping("/{id}")
    fun deletarTipoProduto(@PathVariable id: Long): ResponseEntity<Void> {
        tipoProdutoService.deletarTipo(id)
        return ResponseEntity.noContent().build()
    }
}
