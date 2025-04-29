package com.techbridge.techbridge.controller

import com.techbridge.techbridge.entity.Credenciais
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.entity.UsuarioLogin
import com.techbridge.techbridge.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("usuarios")
class UsuarioControllerJpa(val repositorioUsuario: UsuarioRepository) {

    @PostMapping("/cadastrar")
    fun postUsuario(@RequestBody novoUsuario: Usuario): ResponseEntity<Usuario> {
        repositorioUsuario.save(novoUsuario)
        return ResponseEntity.status(201).body(novoUsuario)
    }

    @PutMapping("/editar-informacoes/{id}")
    fun putUsuario(@PathVariable id: Int, @RequestBody usuarioEditado: Usuario): ResponseEntity<Usuario> {
        val usuarioExistente = repositorioUsuario.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()

        usuarioExistente.nome = usuarioEditado.nome
        usuarioExistente.email = usuarioEditado.email
        usuarioExistente.telefone_contato = usuarioEditado.telefone_contato

        if (usuarioExistente.nome.isNullOrBlank() || usuarioExistente.email.isNullOrBlank()) {
            return ResponseEntity.badRequest().build() // Retorna erro 400 se algum campo obrigatório for inválido
        }

        val usuarioAtualizado = repositorioUsuario.save(usuarioExistente)

        return ResponseEntity.ok(usuarioAtualizado)
    }

    @DeleteMapping("/deletar/{id}")
    fun deleteUsuario(@PathVariable id: Int): ResponseEntity<Void> {
        return if (repositorioUsuario.existsById(id)) {
            repositorioUsuario.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PatchMapping("/editar-senha/{id}")
    fun editarSenha(@PathVariable id:Int, @RequestBody novaSenha: String): ResponseEntity<Void> {
        val usuarioExistente = repositorioUsuario.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()

        usuarioExistente.senha = usuarioExistente.senha

        return ResponseEntity.ok().build()
    }

    @GetMapping("/login")
    fun getUsuarioLogin(@RequestBody credenciais:UsuarioLogin): ResponseEntity<UsuarioLogin> {
        var consultaUsuarioExistente = repositorioUsuario.findByEmail(credenciais.email)

        var usuarioLogado = credenciais

        if (usuarioLogado.autenticado == false) {

        if (usuarioLogado.senha == consultaUsuarioExistente.senha && usuarioLogado.email == consultaUsuarioExistente.email) {
             usuarioLogado.autenticado = true
            return ResponseEntity.status(200).body(usuarioLogado)
        }
        }
            return ResponseEntity.status(401).build() // Retorna erro 401 se as credenciais estiverem incorretas
    }

    @GetMapping("/logoff")
    fun getUsuarioLogoff(@RequestBody credenciais:UsuarioLogin): ResponseEntity<UsuarioLogin> {
        var usuarioLogado: UsuarioLogin = UsuarioLogin(
            email = credenciais.email,
            senha = credenciais.senha,
            autenticado = true
        )

        if (usuarioLogado.autenticado == true) {
            usuarioLogado.autenticado = false

            return ResponseEntity.status(200).body(usuarioLogado)


        }
            return ResponseEntity.noContent().build() // Retorna erro 401 se as credenciais estiverem incorretas


    }

}

