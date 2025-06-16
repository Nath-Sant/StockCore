package com.stockcore.controller

import com.stockcore.dto.MovimentacaoCreateDTO
import com.stockcore.dto.MovimentacaoDTO
import com.stockcore.model.Movimentacao
import com.stockcore.model.TipoMovimentacao
import com.stockcore.repository.ProdutoRepository
import com.stockcore.service.MovimentacaoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

data class IoTMovimentacaoRequest(val produtoId: Long, val quantidade: Int, val tipoMovimentacao: String)

@RestController
@RequestMapping("/iot")
class IoTController(
    private val produtoRepository: ProdutoRepository,
    private val movimentacaoService: MovimentacaoService
) {

    @PostMapping("/movimentacao")
    fun receberMovimentacao(@RequestBody req: IoTMovimentacaoRequest): ResponseEntity<MovimentacaoDTO> {
        val produto = produtoRepository.findById(req.produtoId).orElse(null)
            ?: return ResponseEntity.badRequest().body(null)

        val tipoMovimentacao = try {
            TipoMovimentacao.valueOf(req.tipoMovimentacao.uppercase())
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(null)
        }

        val quantidadeFinal = if (tipoMovimentacao == TipoMovimentacao.ENTRADA) req.quantidade else -req.quantidade

        val novaMovimentacaoDTO = MovimentacaoCreateDTO(
            produtoId = produto.idProduto,
            quantidade = req.quantidade,
            tipoMovimentacao = tipoMovimentacao.name,
            dataHora = LocalDateTime.now(),
            origem = "IOT"
        )

        val movimentacaoDTO = movimentacaoService.criarMovimentacao(novaMovimentacaoDTO)
        return ResponseEntity.ok(movimentacaoDTO)
    }
}
