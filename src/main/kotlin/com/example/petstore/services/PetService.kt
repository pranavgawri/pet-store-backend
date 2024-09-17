// src/main/kotlin/com/example/petstore/service/PetService.kt
package com.example.petstore.service

import com.example.petstore.model.Pet
import org.springframework.stereotype.Service

@Service
class PetService {
    private val pets = mutableMapOf(
        1L to Pet(1, "Scooby", "Golden Retriever", "Adopted")
    )

    fun getPetById(id: Long): Pet? = pets[id]
}