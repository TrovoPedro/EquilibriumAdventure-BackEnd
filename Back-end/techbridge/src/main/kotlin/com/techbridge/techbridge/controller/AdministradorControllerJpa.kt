package com.techbridge.techbridge.controller

import com.techbridge.techbridge.enums.TipoUsuario
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.Guia
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.repository.AdministradorRepository
<<<<<<< HEAD
=======
import com.techbridge.techbridge.repository.EventoRepository
>>>>>>> 7a62b4ac169e0d4ea9b3b42752a3d3ecfafd038f
import com.techbridge.techbridge.repository.GuiaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/administrador")
class AdministradorControllerJpa(
    val repositorio: AdministradorRepository,
    val repositorioGuia: GuiaRepository,
<<<<<<< HEAD
    val repositorioEventoAtivo: EventoAtivoRepository
=======
    val repositorioEventoAtivo: E,
    val repositorioEvento: EventoRepository
>>>>>>> 7a62b4ac169e0d4ea9b3b42752a3d3ecfafd038f
) {

    @PostMapping("/cadastrar-evento")
    fun postEvento(@RequestBody novoEvento: Evento): ResponseEntity<Evento> {
        val eventoSalvo = repositorioEvento.save(novoEvento)
        return ResponseEntity.status(201).body(eventoSalvo)
    }

    @PostMapping("/cadastrar-evento-ativo")
    fun postEventoAtivo(@RequestBody novoEvento: Evento): ResponseEntity<Evento> {
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
<<<<<<< HEAD
    fun getEventoEspecifico(@PathVariable id: Int): ResponseEntity<Evento> {
        val eventoOptional = repositorio.findById(id)
=======
    fun getEventoEspecifico(@PathVariable id: Long): ResponseEntity<Evento> {
        val eventoOptional = repositorioEvento.findById(id)
>>>>>>> 7a62b4ac169e0d4ea9b3b42752a3d3ecfafd038f

        return if (eventoOptional.isPresent) {
            ResponseEntity.ok(eventoOptional.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/buscar-todos-eventos-ativo")
    fun getAllEventosAtivo(): ResponseEntity<MutableList<Evento>> {
        val eventos = repositorioEventoAtivo.findAll()
        return if (eventos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(eventos)
        }
    }

    @GetMapping("/buscar-evento-ativo-especifico/{id}")
<<<<<<< HEAD
    fun getEventoEspecificoAtivo(@PathVariable id: Int): ResponseEntity<Evento> {
        val eventoOptional = repositorioEventoAtivo.findById(id)

        return if (eventoOptional.isPresent) {
            ResponseEntity.ok(eventoOptional.get())
        } else {
            ResponseEntity.notFound().build()
=======
    fun getEventoEspecificoAtivo(@PathVariable id: Long): ResponseEntity<Evento> {
        val eventoOptional = repositorioEvento.findById(id)

        if (eventoOptional.isPresent) {
           return ResponseEntity.ok(eventoOptional.get())
        } else {
           return ResponseEntity.notFound().build()
>>>>>>> 7a62b4ac169e0d4ea9b3b42752a3d3ecfafd038f
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

    companion object {
        const val tipoGuia = 2
    }

    @PostMapping("/cadastrar-guia")
    fun postGuia(@RequestBody novoGuia: Usuario): ResponseEntity<Usuario> {
        novoGuia.fk_tipo_usuario = TipoUsuario.GUIA
        val guiaSalvo = repositorioGuia.save(novoGuia)
        return ResponseEntity.status(201).body(guiaSalvo)
    }

    @GetMapping("/buscar-guias")
    fun getAllGuias(): ResponseEntity<List<Usuario>> {
        val guias = repositorioGuia.findByFk_tipo_usuario(tipoGuia)

        return if (guias.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(guias)
        }
    }

    @GetMapping("/buscar-guia-especifico/{id}")
    fun getGuiaEspecifico(@PathVariable id: Int): ResponseEntity<Usuario> {
        val guiaOptional = repositorioGuia.findById(id)

        return if (guiaOptional.isPresent) {
            val guia = guiaOptional.get()
            if (guia.fk_tipo_usuario == TipoUsuario.GUIA) {
                ResponseEntity.ok(guia)
            } else {
                ResponseEntity.status(403).build()
            }
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/editar-guia/{id}")
    fun putGuia(@PathVariable id: Long, @RequestBody guiaAtualizado: Usuario): ResponseEntity<Usuario> {
        val guiaOptional = repositorioGuia.findById(id)

<<<<<<< HEAD
        return if (guiaOptional.isPresent) {
            val guiaExistente = guiaOptional.get()
=======
        return if (guiaOptional !== null) {
            val guiaExistente = guiaOptional
>>>>>>> 7a62b4ac169e0d4ea9b3b42752a3d3ecfafd038f
            if (guiaExistente.fk_tipo_usuario == TipoUsuario.GUIA) {
                guiaAtualizado.id_usuario = id
                guiaAtualizado.fk_tipo_usuario = TipoUsuario.GUIA
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
        val guiaOptional = repositorioGuia.findById(id)

        return if (guiaOptional.isPresent) {
            val guiaExistente = guiaOptional.get()
            if (guiaExistente.fk_tipo_usuario == TipoUsuario.GUIA) {
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


