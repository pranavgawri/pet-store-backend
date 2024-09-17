// src/main/kotlin/com/example/petstore/PetstoreApplication.kt
package com.example.petstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetstoreApplication

fun main(args: Array<String>) {
    runApplication<PetstoreApplication>(*args)
}