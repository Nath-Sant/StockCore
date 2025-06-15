package com.stockcore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockcoreApplication

fun main(args: Array<String>) {
	runApplication<StockcoreApplication>(*args)
}
