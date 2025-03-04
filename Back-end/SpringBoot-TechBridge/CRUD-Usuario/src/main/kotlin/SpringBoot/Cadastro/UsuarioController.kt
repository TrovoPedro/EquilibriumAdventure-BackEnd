package SpringBoot.Cadastro

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    val usuario = mutableListOf<Usuario>()

    @PostMapping("/cadastrar")
    fun cadastrar(@RequestBody novoUsuario: Usuario): ResponseEntity<Usuario> {
        usuario.add(novoUsuario)
        return ResponseEntity.status(201).body(novoUsuario)
    }

    @GetMapping("/exibir")
    fun exibir(): MutableList<Usuario> {
        return usuario
    }

    @GetMapping("/exibir/{id}")
    fun exibirPorPosicao(@PathVariable id: Int): Any {
        if (id < usuario.size) {
            return ResponseEntity.status(201).body(usuario[id])
        }
        return ResponseEntity.status(204)
    }
}