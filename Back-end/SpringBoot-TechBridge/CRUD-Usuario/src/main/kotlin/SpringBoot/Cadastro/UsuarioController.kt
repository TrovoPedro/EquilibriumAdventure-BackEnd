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
    fun cadastrar(@RequestBody novoUsuario: Usuario):ResponseEntity<Usuario> {
        usuario.add(novoUsuario)
        return ResponseEntity.status(201).body(novoUsuario)
    }

/*  @GetMapping("/exibir")
    fun exibir():Usuario {
        return usuario
    }
*/

    /*
    // Aqui vai retornar uma lista de JSONs
    /*
    Aqui usamos um recurso chamado Request Param ou Query Param
    Normalmente usamos ele para filtrar resultados.
    O primeiro Request Param sempre é precedido de ?
    Caso existam mais de 1, entre eles colocamos &

    Importante: Se há pelo menos 1 request param,
    dizemos que a URL tem uma Query String

    O nome do parâmetro na Query String, por padrão, é o mesmo nome do parâmtro da função
     */
    @GetMapping  //   /herois ou /herois?classe=A ou /herois?classe=C&forcaMinima=7000 ou /herois?forcaMinima=7000&classe=C ou /herois?forcaMinima=7000
    fun lista(@RequestParam(required = false) classe:String?,
              @RequestParam(required = false) forcaMinima:Int?): ResponseEntity<List<Heroi>> {
        if (classe == null && forcaMinima == null) {
            if (herois.isEmpty()) {
                return ResponseEntity.status(204).build()
            }
            return ResponseEntity.status(200).body(herois)
        }

        val filtraClasse = classe != null
        val filtraForca = forcaMinima != null

        val listaFiltrada = mutableListOf<Heroi>()

        if (filtraClasse && filtraForca) {
            listaFiltrada.addAll(herois.filter {
                it.classe == classe && it.forca!! >= forcaMinima!!
            })
        }
        else if (filtraClasse) {
            listaFiltrada.addAll(herois.filter{ it.classe == classe })
        } else {
            listaFiltrada.addAll(
                herois.filter{ it.forca!! >= forcaMinima!! }
            )
        }

        if (listaFiltrada.isEmpty()) {
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(listaFiltrada)
    }

    /*@PostMapping("/{apelido}/{classe}/{forca}")
    fun criar(@PathVariable apelido:String, @PathVariable classe: String, @PathVariable forca: Int):String {
        val novoHeroi = Heroi(apelido, classe, forca)
        herois.add(novoHeroi)
        return "Herói cadastrado com sucesso"
    }*/

    /*
    Aqui criamos um EndPoint que só pode ser chamado pelo método POST do HTTP.
    Ele precisa que seja enviado um corpo de requisição JSON cujos campos sejam compatíveis com os da classe Herói
     */
    @PostMapping
    fun criar(@RequestBody novoHeroi: Heroi):ResponseEntity<Heroi> {
        herois.add(novoHeroi)
        return ResponseEntity.status(201).body(novoHeroi)
    }

/*
Aqui criamos um EndPoint que só pode ser chamado pelo método DELETE do HTTP.
 */
    @DeleteMapping("/{id}")
    fun excluir(@PathVariable id:Int):String {
        herois.removeAt(id)
        return "Herói excluído com sucesso"
    }

    @GetMapping("/{id}")
    fun recuperar(@PathVariable id:Int):ResponseEntity<Heroi> {
        if (id in 0..herois.size-1) {
            return ResponseEntity.status(200).body(herois[id])
        }
        return ResponseEntity.status(404).build()
    }

/*
Aqui criamos um EndPoint que só pode ser chamado pelo método PUT do HTTP.
Ele precisa que seja enviado um "id" como path param
Ele também precisa que seja enviado um corpo de requisição JSON cujos campos sejam compatíveis com os da classe Herói
 */
    @PutMapping("/{id}")
    fun atualizar(@PathVariable id:Int, @RequestBody heroiAtualizado: Heroi):String {
        herois[id] = heroiAtualizado
        return "Herói atualizado com sucesso!"
    }

    /*
    Aqui criamos um EndPoint que só pode ser chamado pelo método PATCH do HTTP.
     */
    @PatchMapping("/{id}/{novaClasse}")
    fun mudarClasse(@PathVariable id:Int, @PathVariable novaClasse:String): String {
        herois[id].classe = novaClasse
        return "Classe atualizada com sucesso"
    }*/
}