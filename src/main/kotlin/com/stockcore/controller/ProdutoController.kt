package com.stockcore.controller

import com.stockcore.dto.ProdutoCreateDTO
import com.stockcore.dto.ProdutoDTO
import com.stockcore.dto.ProdutoUpdateDTO
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

    @GetMapping("/{nome}")
    fun buscarPorNome(@PathVariable nome: String): ResponseEntity<List<ProdutoDTO>> {
        val produtos = produtoService.buscarPorNome(nome)
        return ResponseEntity.ok(produtos)
    }

    @PostMapping
    fun criarProduto(@RequestBody dto: ProdutoCreateDTO): ResponseEntity<ProdutoDTO> {
        val novoProduto = produtoService.criarProduto(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto)
    }

    @PutMapping("/{id}")
    fun atualizarProduto(@PathVariable id: Long, @RequestBody dto: ProdutoUpdateDTO): ResponseEntity<ProdutoDTO> {
        val produtoAtualizado = produtoService.atualizarProduto(id, dto)
        return ResponseEntity.ok(produtoAtualizado)
    }


    @DeleteMapping("/{id}")
    fun deletarProduto(@PathVariable id: Long): ResponseEntity<Void> {
        produtoService.deletarProduto(id)
        return ResponseEntity.noContent().build()
    }
}
