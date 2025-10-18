package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.EventoGuiaEnderecoDTO
import com.techbridge.techbridge.dto.EventoRequestDTO
import com.techbridge.techbridge.dto.EventoResponseDTO
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.repository.EnderecoRepository
import com.techbridge.techbridge.repository.EventoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.multipart.MultipartFile
import java.awt.PageAttributes
import java.util.Optional

@Service
class GuiaService {

    @Autowired
    lateinit var eventoRepository: EventoRepository

    @Autowired
    lateinit var enderecoRepository: EnderecoRepository

    fun postEvento(novoEvento: EventoRequestDTO, img_evento: MultipartFile?): Evento {
        val enderecoDto = novoEvento.endereco ?: throw IllegalArgumentException("Endereço é obrigatório")

        val enderecoSalvo = enderecoRepository.save(enderecoDto.toEntity())

        val evento = novoEvento.toEntity(enderecoSalvo.id_endereco)

        evento.img_evento = img_evento?.bytes

        return eventoRepository.save(evento)
    }


    fun getEventos(): List<Map<String, Any>> {
        val eventosEncontrados = eventoRepository.listarEventosComResponsavelERua()

        if (eventosEncontrados.isEmpty()) {
            throw RuntimeException("Nenhum evento encontrado")
        }

        return eventosEncontrados
    }

    fun getEventoId(id: Long): Optional<Evento>{
        val eventoEncontrado = eventoRepository.findById(id)

        return eventoEncontrado;
    }

    fun getImagemEvento(id: Long): ResponseEntity<ByteArray> {
        val evento = eventoRepository.findById(id)
            .orElseThrow { RuntimeException("Evento não encontrado") }

        return if (evento.img_evento != null) {
            ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(evento.img_evento)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    fun getEventoPorGuia(id:Long): List<Map<String, Any>>{
        val eventosEncotrados = eventoRepository.buscarEventoPorGuia(id)

        if(eventosEncotrados.isEmpty()){
            throw RuntimeException("Nenhum evento encontrado")
        }

        return eventosEncotrados
    }

    fun getEventoAtivoPorGuia(id:Long): List<Map<String, Any>>{
        val eventosEncotrados = eventoRepository.buscarEventoPorGuia(id)

        if(eventosEncotrados.isEmpty()){
            throw RuntimeException("Nenhum evento encontrado")
        }

        return eventosEncotrados
    }

    fun buscarEventoAtivoPorGuia(idGuia: Long): List<Map<String, Any>> {
        val eventos = eventoRepository.buscarEventoAtivoPorGuia(idGuia)
        return eventos
    }

}
