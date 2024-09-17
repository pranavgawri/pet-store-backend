// src/main/kotlin/com/example/petstore/controller/PetController.kt
package com.example.petstore.controller

import com.example.petstore.model.Pet
import com.example.petstore.service.PetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pets")
class PetController(private val petService: PetService) {

    @GetMapping("/{petid}")
    fun getPetById(@PathVariable petid: Long): ResponseEntity<Pet> {
        val pet = petService.getPetById(petid)
        return if (pet != null) {
            ResponseEntity.ok(pet)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}