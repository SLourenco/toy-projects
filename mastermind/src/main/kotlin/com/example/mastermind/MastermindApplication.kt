package com.example.mastermind

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MastermindApplication

fun main(args: Array<String>) {
	runApplication<MastermindApplication>(*args)
}
