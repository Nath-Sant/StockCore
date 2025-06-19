package com.stockcore.controller

import com.stockcore.dto.UsuarioCreateDTO
import com.stockcore.dto.UsuarioDTO
import com.stockcore.dto.UsuarioUpdateDTO
import com.stockcore.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController(
    private val usuarioService: UsuarioService
) {

    @GetMapping
    fun listarTodos(): ResponseEntity<List<UsuarioDTO>> {
        val usuarios = usuarioService.listarTodos()
        return ResponseEntity.ok(usuarios)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<UsuarioDTO> {
        val usuario = usuarioService.buscarPorId(id)
        return ResponseEntity.ok(usuario)
    }

    @GetMapping("/nome/{nome}")
    fun buscarPorNome(@PathVariable nome: String): ResponseEntity<List<UsuarioDTO>> {
        val usuarios = usuarioService.buscarPorNome(nome)
        return ResponseEntity.ok(usuarios)
    }

    @PostMapping
    fun criarUsuario(@RequestBody dto: UsuarioCreateDTO): ResponseEntity<UsuarioDTO> {
        val novoUsuario = usuarioService.criarUsuario(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario)
    }

    @PutMapping("/{id}")
    fun atualizarUsuario(@PathVariable id: Long, @RequestBody dto: UsuarioUpdateDTO): ResponseEntity<UsuarioDTO> {
        val usuarioAtualizado = usuarioService.atualizarUsuario(id, dto)
        return ResponseEntity.ok(usuarioAtualizado)
    }

    @DeleteMapping("/{id}")
    fun deletarUsuario(@PathVariable id: Long): ResponseEntity<Void> {
        usuarioService.deletarUsuario(id)
        return ResponseEntity.noContent().build()
    }
}
