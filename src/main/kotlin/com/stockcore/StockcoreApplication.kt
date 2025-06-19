package com.stockcore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.stockcore"])
class StockcoreApplication

fun main(args: Array<String>) {
	runApplication<StockcoreApplication>(*args)
}
