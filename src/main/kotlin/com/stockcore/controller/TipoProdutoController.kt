package com.stockcore.controller

import com.stockcore.dto.TipoProdutoCreateDTO
import com.stockcore.dto.TipoProdutoDTO
import com.stockcore.dto.TipoProdutoUpdateDTO
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
    fun criarTipo(@RequestBody dto: TipoProdutoCreateDTO): ResponseEntity<TipoProdutoDTO> {
        val novoTipo = tipoProdutoService.criarTipo(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTipo)
    }

    @PutMapping("/{id}")
    fun atualizarTipo(@PathVariable id: Long, @RequestBody dto: TipoProdutoUpdateDTO): ResponseEntity<TipoProdutoDTO> {
        val tipoAtualizado = tipoProdutoService.atualizarTipo(id, dto)
        return ResponseEntity.ok(tipoAtualizado)
    }


    @DeleteMapping("/{id}")
    fun deletarTipoProduto(@PathVariable id: Long): ResponseEntity<Void> {
        tipoProdutoService.deletarTipo(id)
        return ResponseEntity.noContent().build()
    }
}
