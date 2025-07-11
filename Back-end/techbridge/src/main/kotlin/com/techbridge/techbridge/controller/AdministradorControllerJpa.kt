package com.techbridge.techbridge.controller

import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.enums.TipoUsuario
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


import com.techbridge.techbridge.dto.UsuarioRequestDTO
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.repository.AdministradorRepository
import com.techbridge.techbridge.repository.AtivacaoEventoRepository
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.GuiaRepository
import com.techbridge.techbridge.service.AdministradorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/administrador")
class AdministradorControllerJpa(
    val repositorio: AdministradorRepository,
    val repositorioGuia: GuiaRepository,
    val repositorioEventoAtivo: AtivacaoEventoRepository,
    val repositorioEvento: EventoRepository

) {

    @PostMapping("/cadastrar-evento")
    fun postEvento(@RequestBody novoEvento: Evento): ResponseEntity<Evento> {
        val eventoSalvo = repositorioEvento.save(novoEvento)
        return ResponseEntity.status(201).body(eventoSalvo)
    }
    /*@Autowired
    lateinit var administradorService: AdministradorService

    @PostMapping("/cadastrar-evento")
    fun postEvento(@RequestBody novoEvento: Evento): Any? {
        val eventoSalvo = administradorService.salvarEvento(novoEvento)
        try {
            return ResponseEntity.status(201).body(novoEvento)
        }catch (e:RuntimeException){
            return e.message
        }
*/
    @PostMapping("/cadastrar-evento-ativo")
    fun postEventoAtivo(@RequestBody novoEvento: AtivacaoEvento): ResponseEntity<AtivacaoEvento> {
        val eventoSalvo = repositorioEventoAtivo.save(novoEvento)
        return ResponseEntity.status(201).body(eventoSalvo)
    }

    @GetMapping("/buscar-todos-eventos-base")
    fun getAllEventos(): ResponseEntity<MutableList<Evento>> {
        val eventos = repositorioEvento.findAll()
        return if (eventos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(eventos)
        }
    }

    @GetMapping("/buscar-evento-base-especifico/{id}")
    fun getEventoEspecifico(@PathVariable id: Long): ResponseEntity<Evento> {
        val eventoOptional = repositorioEvento.findById(id)


        return if (eventoOptional.isPresent) {
            ResponseEntity.ok(eventoOptional.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/buscar-todos-eventos-ativo")
    fun getAllEventosAtivo(): ResponseEntity<MutableList<AtivacaoEvento>> {
        val eventos = repositorioEventoAtivo.findAll()
        return if (eventos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(eventos)
        }
    }

    @GetMapping("/buscar-evento-ativo-especifico/{id}")
    fun getEventoEspecificoAtivo(@PathVariable id: Long): ResponseEntity<AtivacaoEvento> {
        val eventoOptional = repositorioEventoAtivo.findById(id)

        return if (eventoOptional.isPresent) {
            ResponseEntity.ok(eventoOptional.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }



    @PutMapping("/editar-evento/{id}")
    fun putEvento(@PathVariable id: Long, @RequestBody eventoAtualizado: Evento): ResponseEntity<Evento> {
        if (!repositorioEvento.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }

        if (eventoAtualizado.nome.isNullOrEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }

        eventoAtualizado.id_evento = id

        val evento = repositorioEvento.save(eventoAtualizado)

        return ResponseEntity.status(HttpStatus.OK).body(evento)
    }



    @DeleteMapping("/deletar-evento/{id}")
    fun deleteEvento(@PathVariable id: Int): ResponseEntity<Void> {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

    // GUIAS

    @PostMapping("/cadastrar-guia")
    fun postGuia(@RequestBody novoGuia: Usuario): ResponseEntity<Usuario> {
        novoGuia.tipo_usuario = TipoUsuario.GUIA
        val guiaSalvo = repositorioGuia.save(novoGuia)
        return ResponseEntity.status(201).body(guiaSalvo)
    }


        /*fun postGuia(@RequestBody novoGuia: UsuarioRequestDTO): Any? {
        val guiaSalvo = administradorService.salvarGuia(novoGuia)
        try {
            return ResponseEntity.status(201).body(novoGuia)
        }catch (e:RuntimeException){
            return e.message
        }
>>>>>>> e2e3fcb979e3c68a4ec32aa0475136c12ed82436
    }*/

    @GetMapping("/buscar-guias")
    fun getAllGuias(): ResponseEntity<List<Usuario>> {
        val guias = repositorioGuia.findByTipoUsuario(TipoUsuario.GUIA)

        return if (guias.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(guias)
        }
    }

    @GetMapping("/buscar-guia-especifico/{id}")
    fun getGuiaEspecifico(@PathVariable id: Int): ResponseEntity<Usuario> {
        val guiaOptional = repositorioGuia.findByIdAndTipo(id, TipoUsuario.GUIA)

        return if (guiaOptional !== null) {
            val guia = guiaOptional
            if (guia.tipo_usuario == TipoUsuario.GUIA) {
                ResponseEntity.ok(guia)
            } else {
                ResponseEntity.status(404).build()
            }
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/editar-guia/{id}")
    fun putGuia(@PathVariable id: Long, @RequestBody guiaAtualizado: Usuario): ResponseEntity<Usuario> {
        val guiaOptional = repositorioGuia.findByIdAndTipo(id.toInt(), TipoUsuario.GUIA)

        return if (guiaOptional !== null) {
            val guiaExistente = guiaOptional
            if (guiaExistente.tipo_usuario == TipoUsuario.GUIA) {
                guiaAtualizado.idUsuario = id
                guiaAtualizado.tipo_usuario = TipoUsuario.GUIA
                val guiaSalvo = repositorioGuia.save(guiaAtualizado)
                ResponseEntity.ok(guiaSalvo)
            } else {
                ResponseEntity.status(403).build()
            }
        } else {
            ResponseEntity.notFound().build()
        }
    }


    @DeleteMapping("/deletar-guia/{id}")
    fun deleteGuia(@PathVariable id: Int): ResponseEntity<Void> {
        val guiaOptional = repositorioGuia.findByIdAndTipo(id, TipoUsuario.GUIA)

        return if (guiaOptional !== null ) {
            val guiaExistente = guiaOptional
            if (guiaExistente.tipo_usuario == TipoUsuario.GUIA) {
                repositorioGuia.deleteById(id)
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.status(403).build()
            }
        } else {
            ResponseEntity.notFound().build()
        }
    }


}


