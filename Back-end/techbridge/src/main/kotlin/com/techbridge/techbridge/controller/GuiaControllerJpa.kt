package com.techbridge.techbridge.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.techbridge.techbridge.dto.EventoRequestDTO
import com.techbridge.techbridge.entity.AgendaResponsavel
import com.techbridge.techbridge.entity.Comentario
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.enums.TipoUsuario
import com.techbridge.techbridge.repository.*
import com.techbridge.techbridge.service.GuiaService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/guia")
class GuiaControllerJpa(
    val repositorioAventureiro: AventureiroRepository,
    val repositorioInformacoes: InformacoesPessoaisRepository,
    val repositorioConvite: ConviteRepository,
    private val repositorioEvento: EventoRepository,
    val repositorioInscricao: InscricaoRepository,
    val repositorioAtivacaoEvento: AtivacaoEventoRepository,
    val repositorioComentario: ComentarioRepository,
    val repositorioAgendaResponsavel: AgendaResponsavelRepository,
    val repositorioAgendaAnamnese: AgendamentoAnamneseRepository
) {

    @Autowired
    private lateinit var guiaService: GuiaService

    @Autowired
    private lateinit var guiaRepository: GuiaRepository

    @Autowired
    lateinit var eventoService: GuiaService

    @PostMapping("/cadastrar", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun cadastrarEvento(
        @RequestPart("evento") eventoJson: String,
        @RequestPart("imagem", required = false) img_evento: MultipartFile?
    ): ResponseEntity<Any> {
        return try {
            val objectMapper = ObjectMapper()
            val novoEvento = objectMapper.readValue(eventoJson, EventoRequestDTO::class.java)

            val eventoSalvo = eventoService.postEvento(novoEvento, img_evento)

            ResponseEntity.ok(mapOf("success" to true, "evento" to eventoSalvo))
        } catch (e: Exception) {
            ResponseEntity.status(400).body(
                mapOf("success" to false, "message" to "Erro ao processar requisição: ${e.message}")
            )
        }
    }

    @GetMapping("/{id}/imagem")
    fun getImagem(@PathVariable id: Long): ResponseEntity<ByteArray> {
        val evento = eventoService.getEventoId(id)
            .orElseThrow { RuntimeException("Evento não encontrado") }

        return if (evento.img_evento != null) {
            ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(evento.img_evento)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/buscar-eventos")
    fun getAllEventos(): ResponseEntity<Any>{
        val eventosEncontrados: List<Map<String, Any>> = eventoService.getEventos();
        if (eventosEncontrados.isEmpty()) {
        return ResponseEntity.status(204).build()
        }

            return ResponseEntity.status(200).body(eventosEncontrados)
    }

        @GetMapping("/buscar-eventos/{id}")
        fun getEventoPorGuia(@PathVariable id:Long): ResponseEntity<Any>{
    
                val eventosEncontrados: List<Map<String, Any>> = eventoService.getEventoPorGuia(id);
            if (eventosEncontrados.isEmpty()) {
                return ResponseEntity.status(204).build()
            }
            return ResponseEntity.status(200).body(eventosEncontrados)
        }



        @GetMapping("/buscar-evento-especifico/{id}")
        fun getEventoEspecifico(@PathVariable id: Long): ResponseEntity<Any> {
            return try {
                val response = guiaService.getAtivacoesPorEvento(id)
                ResponseEntity.ok(response)
            } catch (e: Exception) {
                ResponseEntity.status(404).body(mapOf("erro" to e.message))
            }
        }

    @GetMapping("/buscar-evento-ativo-especifico/{id}")
    fun getEventoAtivoEspecifico(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            val response = guiaService.getDetalheEventoAtivoPorGuia(id)
            ResponseEntity.ok(response)
        } catch (e: RuntimeException) {
            ResponseEntity.status(404).body(mapOf("erro" to e.message))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("erro" to "Erro interno: ${e.message}"))
        }
    }

    @GetMapping("/{id}/gpx")
    fun getGpxEvento(@PathVariable id: Long): ResponseEntity<ByteArray> {
        val evento = eventoService.getEventoPorId(id)
        val gpx = evento.caminho_arquivo_evento ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("application/gpx+xml"))
            .body(gpx.toByteArray(Charsets.UTF_8))
    }



    @GetMapping("Adicionar-comentario/{idEvento}/{idAventureiro}")
    fun postComentario(
        @PathVariable idEvento: Long,
        @PathVariable idAventureiro: Int,
        @RequestBody comentario: String
    ): ResponseEntity<String> {
        val evento = repositorioAtivacaoEvento.findById(idEvento)
        val aventureiro = repositorioAventureiro.findByIdAndTipo(idAventureiro, TipoUsuario.AVENTUREIRO)

        if (evento.isPresent && aventureiro !== null) {
            val comentario = Comentario(
                texto = comentario,
                usuario = aventureiro,
                ativacaoEvento = evento.get()
            )
            repositorioComentario.save(comentario)

            return ResponseEntity.status(200).body("Comentário adicionado com sucesso!")
        } else {
            return ResponseEntity.status(404).body("Evento ou aventureiro não encontrado.")
        }
    }

    @DeleteMapping("/deletar-comentario/{idComentario}")
    fun deleteComentario(@PathVariable idComentario: Int): ResponseEntity<Void> {
        val comentario = repositorioComentario.findById(idComentario)
        if (comentario.isPresent) {
            repositorioComentario.deleteById(idComentario)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

    @PutMapping("/editar-evento/{idEvento}/{idEndereco}")
    fun putEvento(@PathVariable idEvento: Long, @PathVariable idEndereco: Long,
          @RequestPart("evento") eventoJson: String,
          @RequestPart("imagem", required = false) img_evento: MultipartFile?
            ): ResponseEntity<Any> {
        return try {
            val objectMapper = ObjectMapper()
            val novoEvento = objectMapper.readValue(eventoJson, EventoRequestDTO::class.java)

            val eventoSalvo = eventoService.putEvento(idEvento,idEndereco,novoEvento, img_evento)


            ResponseEntity.ok(mapOf("success" to true, "data" to eventoSalvo))
        } catch (e: Exception) {
            ResponseEntity.status(400).body(
                mapOf("success" to false, "message" to "Erro ao processar requisição: ${e.message}")
            )
        }
    }

    @DeleteMapping("/deletar-evento/{id}")
    fun deleteEvento(@PathVariable id: Long): ResponseEntity<Void> {
        if (repositorioEvento.existsById(id)) {
            repositorioEvento.deleteById(id)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

    @GetMapping("buscar-eventos-ativos")
    fun getEventosAtivos(): ResponseEntity<MutableList<Evento>> {
        val eventosAtivos = repositorioAtivacaoEvento.findAll()
        return (if (eventosAtivos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(eventosAtivos)
        }) as ResponseEntity<MutableList<Evento>>
    }

    @GetMapping("buscar-eventos-base")
    fun getEventosBase(): ResponseEntity<MutableList<Evento>> {
        val eventosAtivos = repositorioEvento.findAll()
        return if (eventosAtivos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(eventosAtivos)
        }
    }

    @DeleteMapping("/deletar-inscricao-evento/{eventoSelecionado}/{usuarioSelecionado}")
    fun deleteInscricao(@PathVariable eventoSelecionado: Long,@PathVariable usuarioSelecionado: Long):ResponseEntity<Void>{
        if (repositorioAtivacaoEvento.existsById(eventoSelecionado)) {
            val inscricao = repositorioInscricao.findByAventureiroAndEvento(usuarioSelecionado, eventoSelecionado)
            if (inscricao != null) {
                repositorioInscricao.delete(inscricao)
            } else {
                return ResponseEntity.status(202).build()
            }
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(404).build()
    }

    @PostMapping("/adicionar-agenda-disponivel")
    fun postAgenda(@RequestBody dataAgenda: AgendaResponsavel?): ResponseEntity<Void> {
        if (dataAgenda == null) {
            return ResponseEntity.status(400).build()
        } else {
            val agendaSalva = repositorioAgendaResponsavel.save(dataAgenda)

            return ResponseEntity.status(200).build()
        }
    }

    @GetMapping("/buscar-historico-agenda/{usuarioSelecionado}")
    fun getHistoricoAgenda(@PathVariable usuarioSelecionado: Int): ResponseEntity<out List<Any>?> {
        val agenda = repositorioAgendaAnamnese.listarHistoricoGuia(usuarioSelecionado)

        if (agenda.isEmpty()) {
            return ResponseEntity.status(204).build()
        } else {
            return ResponseEntity.status(200).body(agenda)
        }
    }

    @GetMapping("/buscar-datas-disponiveis/{usuarioSelecionado}")
    fun getDatasDisponiveis(@PathVariable usuarioSelecionado: Long): ResponseEntity<out List<Any>?> {
        val datasDisponiveis = repositorioAgendaResponsavel.findByFkresponsavelAndDataDisponivel(usuarioSelecionado)
        if (datasDisponiveis.isEmpty()) {
            return ResponseEntity.status(204).build()
        } else {
            return ResponseEntity.status(200).body(datasDisponiveis)
        }
    }

    @GetMapping("/buscar-informacoes-pessoais/{usuarioSelecionado}")
    fun getInformacoesPessoas(@PathVariable usuarioSelecionado: Long): ResponseEntity<out Any?> {
        val informacoes = repositorioInformacoes.buscarInformacoes(usuarioSelecionado)
        if (informacoes == null) {
            return  ResponseEntity.status(204).build()
        } else {
            return ResponseEntity.status(200).body(informacoes)
        }
    }

    @GetMapping("/ativos/guia/{idGuia}")
    fun buscarEventoAtivoPorGuia(@PathVariable idGuia: Long): ResponseEntity<Any> {
        return try {
            val eventos = eventoService.buscarEventoAtivoPorGuia(idGuia)
            ResponseEntity.ok(eventos)
        } catch (ex: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("erro" to ex.message))
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("erro" to "Erro ao buscar eventos ativos: ${ex.message}"))
        }
    }
}
