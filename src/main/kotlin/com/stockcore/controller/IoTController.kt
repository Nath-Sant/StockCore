package com.stockcore.controller

import com.stockcore.model.Movimentacao
import com.stockcore.model.Produto
import com.stockcore.repository.MovimentacaoRepository
import com.stockcore.repository.ProdutoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

data class IoTMovimentacaoRequest(val produtoId: Long, val quantidade: Int, val tipo: String)

@RestController
@RequestMapping("/iot")
class IoTController(
    val produtoRepository: ProdutoRepository,
    val movimentacaoRepository: MovimentacaoRepository
) {
    @PostMapping("/movimentacao")
    fun receberMovimentacao(@RequestBody req: IoTMovimentacaoRequest): ResponseEntity<Any> {
        val produto = produtoRepository.findById(req.produtoId).orElse(null)
            ?: return ResponseEntity.badRequest().body("Produto não encontrado")

        // Atualizar estoque
        val novaQuantidade = when (req.tipo.uppercase()) {
            "ENTRADA" -> produto.quantidade + req.quantidade
            "SAIDA" -> produto.quantidade - req.quantidade
            else -> return ResponseEntity.badRequest().body("Tipo inválido (ENTRADA ou SAIDA)")
        }
        produtoRepository.save(produto.copy(quantidade = novaQuantidade))

        // Registrar movimentação
        val mov = Movimentacao(
            produto = produto,
            quantidade = req.quantidade,
            tipo = req.tipo.uppercase(),
            dataHora = LocalDateTime.now()
        )
        movimentacaoRepository.save(mov)

        return ResponseEntity.ok("Movimentação registrada com sucesso")
    }
}
