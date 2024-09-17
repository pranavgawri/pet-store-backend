// src/main/kotlin/com/example/petstore/model/Pet.kt
package com.example.petstore.model

data class Pet(
    val id: Long,
    val name: String,
    val type: String,
    val status: String
)