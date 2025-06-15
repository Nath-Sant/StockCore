package com.stockcore.controller

import com.stockcore.dto.ProdutoDTO
import com.stockcore.model.Produto
import com.stockcore.service.ProdutoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/produtos")
class ProdutoController(
    private val produtoService: ProdutoService
) {

    @GetMapping
    fun listarTodos(): ResponseEntity<List<ProdutoDTO>> {
        val produtos = produtoService.listarTodos()
        return ResponseEntity.ok(produtos)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<ProdutoDTO> {
        val produto = produtoService.buscarPorId(id)
        return ResponseEntity.ok(produto)
    }

    @GetMapping("/tipo/{tipoNome}")
    fun buscarPorTipo(@PathVariable tipoNome: String): ResponseEntity<List<ProdutoDTO>> {
        val produtos = produtoService.buscarPorTipo(tipoNome)
        return ResponseEntity.ok(produtos)
    }

    @PostMapping
    fun criarProduto(@RequestBody produto: Produto): ResponseEntity<ProdutoDTO> {
        val novoProduto = produtoService.criarProduto(produto)
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto)
    }

    @PutMapping("/{id}")
    fun atualizarProduto(@PathVariable id: Long, @RequestBody produto: Produto): ResponseEntity<ProdutoDTO> {
        val produtoAtualizado = produtoService.atualizarProduto(id, produto)
        return ResponseEntity.ok(produtoAtualizado)
    }

    @DeleteMapping("/{id}")
    fun deletarProduto(@PathVariable id: Long): ResponseEntity<Void> {
        produtoService.deletarProduto(id)
        return ResponseEntity.noContent().build()
    }
}
