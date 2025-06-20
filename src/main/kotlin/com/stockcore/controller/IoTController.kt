package com.stockcore.controller

import com.stockcore.dto.MovimentacaoCreateDTO
import com.stockcore.dto.MovimentacaoDTO
import com.stockcore.service.MovimentacaoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class IoTMovimentacaoRequest(
    val produtoId: Int,
    val quantidade: Int,
    val tipoMovimentacao: String
)

@RestController
@RequestMapping("/iot")
class IoTController(
    private val movimentacaoService: MovimentacaoService
) {

    @PostMapping("/movimentacao")
    fun receberMovimentacao(@RequestBody req: IoTMovimentacaoRequest): ResponseEntity<MovimentacaoDTO> {
        val movimentacaoCreateDTO = MovimentacaoCreateDTO(
            produtoId = req.produtoId,
            quantidade = req.quantidade,
            tipoMovimentacao = req.tipoMovimentacao.uppercase(),
            origem = "IoT"
        )
        val movimentacaoDTO = movimentacaoService.criarMovimentacao(movimentacaoCreateDTO)

        return ResponseEntity.status(HttpStatus.CREATED).body(movimentacaoDTO)
    }
}