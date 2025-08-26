package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.AlterarSenhaDTO
import com.techbridge.techbridge.dto.EditarInformacoesDTO
import com.techbridge.techbridge.dto.UsuarioRequestDTO
import com.techbridge.techbridge.dto.UsuarioResponseDTO
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.entity.UsuarioLogin
import com.techbridge.techbridge.repository.UsuarioRepository
import com.techbridge.techbridge.service.UsuarioService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("usuarios")
class UsuarioController(val repositorioUsuario: UsuarioRepository) {

    @Autowired
    lateinit var usuarioService: UsuarioService;

    @PostMapping("/cadastrar")
    fun postUsuario(@Valid @RequestBody novoUsuario: UsuarioRequestDTO): ResponseEntity<Any> {
        return try {
            val usuarioSalvo = usuarioService.postUsuario(novoUsuario)
            ResponseEntity.status(201).body(usuarioSalvo)
        } catch (e: RuntimeException) {
            ResponseEntity.status(400).body(mapOf("erro" to e.message))
        }
    }

    @GetMapping("/buscar/{id}")
    fun getUsuario(@PathVariable id: Long): ResponseEntity<UsuarioResponseDTO> {
        return try {
            val usuarioDTO = usuarioService.getUsuario(id)
            ResponseEntity.ok(usuarioDTO)
        } catch (e: RuntimeException) {
            ResponseEntity.status(404).build()
        }
    }

    @PutMapping("/editar/{id}")
    fun editarInformacoes(@PathVariable id: Long, @RequestBody informacoesNova: EditarInformacoesDTO): ResponseEntity<Usuario> {
        return try {
            val usuarioAtualizado = usuarioService.putUsuario(id, informacoesNova)
            ResponseEntity.ok(usuarioAtualizado)
        } catch (e: RuntimeException) {
            ResponseEntity.status(404).build()
        }
    }

    @DeleteMapping("/deletar/{id}")
    fun deleteUsuario(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            usuarioService.deleteUsuario(id);
            ResponseEntity.ok().build();
        }catch (e: RuntimeException){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/editar-senha/{id}")
    fun editarSenha(@PathVariable id: Long, @RequestBody novaSenha: AlterarSenhaDTO): ResponseEntity<Void> {
        return try {
            usuarioService.patchSenha(novaSenha, id)
            ResponseEntity.ok().build()
        } catch (e: RuntimeException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/login")
    fun getUsuarioLogin(@RequestBody credenciais:UsuarioLogin): ResponseEntity<UsuarioLogin> {
        var consultaUsuarioExistente = repositorioUsuario.findByEmail(credenciais.email)

        var usuarioLogado = credenciais

        if (usuarioLogado.autenticado == false) {

            if (usuarioLogado.senha == consultaUsuarioExistente?.senha || usuarioLogado.email == consultaUsuarioExistente?.email) {
                usuarioLogado.autenticado = true
                return ResponseEntity.status(200).body(usuarioLogado)
            }
        }




        return ResponseEntity.status(401).build() // Retorna erro 401 se as credenciais estiverem incorretas


    }

    @GetMapping("/logoff")
    fun getUsuarioLogoff(@RequestBody credenciais: UsuarioLogin): ResponseEntity<UsuarioLogin> {
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
