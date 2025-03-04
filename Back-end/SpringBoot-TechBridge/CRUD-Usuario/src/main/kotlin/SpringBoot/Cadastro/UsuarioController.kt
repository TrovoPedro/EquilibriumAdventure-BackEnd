package SpringBoot.Cadastro

import Usuario
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

    @PostMapping("/cadastrar")
    fun cadastrar(@RequestBody novoUsuario: Usuario):ResponseEntity<Usuario> {
        usuario.add(novoUsuario)
        return ResponseEntity.status(201).body(novoUsuario)
    }

    @PutMapping("/editar-informacoes/{id}")
    fun editarUsuario(@PathVariable id:Int, @RequestBody usuarioEditado: Usuario):ResponseEntity<Usuario>{
        usuario[id] = usuarioEditado;
        return ResponseEntity.status(201).body(usuarioEditado);
    }

    @PatchMapping("/editar-senha/{id}")
    fun editarSenha(@PathVariable id:Int, @RequestBody novaSenha: String):ResponseEntity<Unit>{
        usuario[id].setSenha(novaSenha)
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/deletar/{id}")
    fun deletarUsuario(@PathVariable id: Int): ResponseEntity<Unit>{
        usuario.removeAt(id)
        return ResponseEntity.status(204).build();
    }

}