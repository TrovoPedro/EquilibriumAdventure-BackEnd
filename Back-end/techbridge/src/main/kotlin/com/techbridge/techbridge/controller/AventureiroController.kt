package com.techbridge.techbridge.controller

import com.techbridge.techbridge.entity.Aventureiro
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/aventureiros")
class AventureiroController {

    val aventureiro = mutableListOf<Aventureiro>()

    @PostMapping("/cadastrar")
    fun cadastrar(@RequestBody novoAventureiro: Aventureiro):ResponseEntity<Aventureiro> {
        aventureiro.add(novoAventureiro)
        return ResponseEntity.status(201).body(novoAventureiro)
    }

    @PutMapping("/editar-informacoes/{id}")
    fun editarUsuario(@PathVariable id:Int, @RequestBody aventureiroEditado: Aventureiro):ResponseEntity<Aventureiro>{
        aventureiro[id] = aventureiroEditado;
        return ResponseEntity.status(201).body(aventureiroEditado);
    }

    @DeleteMapping("/deletar/{id}")
    fun deletarUsuario(@PathVariable id: Int): ResponseEntity<Unit>{
        aventureiro.removeAt(id)
        return ResponseEntity.status(204).build();
    }

}