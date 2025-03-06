package com.techbridge.techbridge

import com.techbridge.techbridge.Usuario
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

class UsuarioController {

    val usuario = mutableListOf<Usuario>()

    @PostMapping("/cadastrar")
    fun cadastrar(@RequestBody novoUsuario: Usuario):ResponseEntity<Usuario> {
        usuario.add(novoUsuario)
        return ResponseEntity.status(201).body(novoUsuario)
    }

    @PutMapping("/editar-informacoes/{id}")
    fun editarUsuario(@PathVariable id:Int, @RequestBody aventureiroEditado: Usuario):ResponseEntity<Usuario>{
        usuario[id] = aventureiroEditado;
        return ResponseEntity.status(201).body(aventureiroEditado);
    }

    @DeleteMapping("/deletar/{id}")
    fun deletarUsuario(@PathVariable id: Int): ResponseEntity<Unit>{
        usuario.removeAt(id)
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/editar-senha/{id}")
    fun editarSenha(@PathVariable id:Int, @RequestBody novaSenha: String): ResponseEntity<Unit> {
        usuario[id].setSenha(novaSenha)
        return ResponseEntity.status(200).build();
    }

}