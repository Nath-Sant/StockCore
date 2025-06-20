package com.stockcore.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND) // Mapeia para 404 Not Found
class ResourceNotFoundException(message: String) : RuntimeException(message)