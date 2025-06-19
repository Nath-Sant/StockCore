package com.stockcore.service

import com.stockcore.dto.UsuarioCreateDTO
import com.stockcore.dto.UsuarioDTO
import com.stockcore.dto.UsuarioUpdateDTO
import com.stockcore.model.Usuario
import com.stockcore.repository.UsuarioRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    private fun toDTO(usuario: Usuario): UsuarioDTO {
        return UsuarioDTO(
            id = usuario.idUsuario,
            nome = usuario.nome,
            role = usuario.role
        )
    }

    fun listarTodos(): List<UsuarioDTO> {
        return usuarioRepository.findAll().map { toDTO(it) }
    }

    fun buscarPorId(id: Long): UsuarioDTO {
        val usuario = usuarioRepository.findById(id)
            .orElseThrow { RuntimeException("Usuário não encontrado") }
        return toDTO(usuario)
    }

    fun buscarPorNome(nome: String): List<UsuarioDTO> {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome)
            .map { toDTO(it) }
    }

    fun criarUsuario(dto: UsuarioCreateDTO): UsuarioDTO {
        val senhaCriptografada = passwordEncoder.encode(dto.senha)
        val novoUsuario = Usuario(
            nome = dto.nome,
            senha = senhaCriptografada,
            role = dto.role
        )
        return toDTO(usuarioRepository.save(novoUsuario))
    }

    fun atualizarUsuario(id: Long, dto: UsuarioUpdateDTO): UsuarioDTO {
        val existente = usuarioRepository.findById(id)
            .orElseThrow { RuntimeException("Usuário não encontrado") }

        val senhaCriptografada = passwordEncoder.encode(dto.senha)

        val atualizado = existente.copy(
            nome = dto.nome,
            senha = senhaCriptografada,
            role = dto.role
        )
        return toDTO(usuarioRepository.save(atualizado))
    }

    fun deletarUsuario(id: Long) {
        usuarioRepository.deleteById(id)
    }
}
