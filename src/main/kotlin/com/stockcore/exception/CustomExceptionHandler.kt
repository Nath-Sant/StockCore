package com.stockcore.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice // Indica que esta classe lida com exceções em todos os controllers
class CustomExceptionHandler {

    // Manipula ResourceNotFoundException (retorna 404 Not Found)
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.NOT_FOUND.value(),
            error = "Not Found",
            message = ex.message ?: "Recurso não encontrado" // Usa a mensagem da exceção ou uma padrão
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    // Manipula BadRequestException (retorna 400 Bad Request)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Bad Request",
            message = ex.message ?: "Requisição inválida"
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // Opcional: Manipulador genérico para outras RuntimeException não tratadas explicitamente
    @ExceptionHandler(RuntimeException::class)
    fun handleGenericRuntimeException(ex: RuntimeException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = ex.message ?: "Ocorreu um erro inesperado no servidor"
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    // DTO para padronizar a resposta de erro
    data class ErrorResponse(
        val timestamp: LocalDateTime,
        val status: Int,
        val error: String,
        val message: String
    )
}