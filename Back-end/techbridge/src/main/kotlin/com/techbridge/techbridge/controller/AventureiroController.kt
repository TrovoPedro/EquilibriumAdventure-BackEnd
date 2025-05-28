package com.techbridge.techbridge.controller

import com.techbridge.techbridge.entity.Usuario
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/Usuarios")
class UsuarioController {

    val usuario = mutableListOf<Usuario>()

    @PostMapping("/cadastrar")
    fun cadastrar(@RequestBody novoUsuario: Usuario):ResponseEntity<Usuario> {
        usuario.add(novoUsuario)
        return ResponseEntity.status(201).body(novoUsuario)
    }

    @PutMapping("/editar-informacoes/{id}")
    fun editarUsuario(@PathVariable id:Int, @RequestBody UsuarioEditado: Usuario):ResponseEntity<Usuario>{
        usuario[id] = UsuarioEditado;
        return ResponseEntity.status(201).body(UsuarioEditado);
    }

    @DeleteMapping("/deletar/{id}")
    fun deletarUsuario(@PathVariable id: Int): ResponseEntity<Unit>{
        usuario.removeAt(id)
        return ResponseEntity.status(204).build();
    }

}