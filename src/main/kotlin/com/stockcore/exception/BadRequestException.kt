package com.stockcore.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST) // Mapeia para 400 Bad Request
class BadRequestException(message: String) : RuntimeException(message)