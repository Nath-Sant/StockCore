package com.stockcore.controller

import com.stockcore.model.Produto
import com.stockcore.repository.ProdutoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/produtos")
class ProdutoController(val produtoRepository: ProdutoRepository) {

    @GetMapping
    fun listar(): List<Produto> = produtoRepository.findAll()

    @PostMapping
    fun criar(@RequestBody produto: Produto): Produto = produtoRepository.save(produto)

    @PutMapping("/{id}")
    fun editar(@PathVariable id: Long, @RequestBody produto: Produto): ResponseEntity<Produto> {
        return produtoRepository.findById(id).map {
            val atualizado = it.copy(nome = produto.nome, quantidade = produto.quantidade)
            ResponseEntity.ok(produtoRepository.save(atualizado))
        }.orElse(ResponseEntity.notFound().build())
    }
}
