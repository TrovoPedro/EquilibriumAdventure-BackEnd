package com.techbridge.techbridge.service

import AtivacaoEventoDTO
import com.techbridge.techbridge.dto.EventoGuiaEnderecoDTO
import com.techbridge.techbridge.dto.EventoRequestDTO
import com.techbridge.techbridge.dto.EventoResponseDTO
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.repository.EnderecoRepository
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.GuiaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.multipart.MultipartFile
import java.awt.PageAttributes
import java.util.Optional
import  com.techbridge.techbridge.dto.toDTO
import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.repository.AtivacaoEventoRepository

@Service
class GuiaService {

    @Autowired
    private lateinit var ativacaoEventoRepository: AtivacaoEventoRepository

    @Autowired
    private lateinit var guiaRepository: GuiaRepository

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

    fun getEventoId(id: Long): Optional<Evento> {
        val eventoEncontrado = eventoRepository.findById(id)

        return eventoEncontrado;
    }

    fun getEventoPorId(id: Long): Evento {
        return eventoRepository.findById(id)
            .orElseThrow { RuntimeException("Evento com ID $id não encontrado.") }
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

    fun getEventoPorGuia(id: Long): List<Map<String, Any>> {
        val eventosEncotrados = eventoRepository.buscarEventoPorGuia(id)

        if (eventosEncotrados.isEmpty()) {
            throw RuntimeException("Nenhum evento encontrado")
        }

        return eventosEncotrados
    }

    fun getDetalheEventoAtivoPorGuia(id: Long): List<AtivacaoEventoDTO> {
        val ativacoes = eventoRepository.findByAtivacaoId(id)

        if (ativacoes.isEmpty()) {
            throw RuntimeException("Nenhum evento encontrado")
        }

        // Converte todos para DTO
        return ativacoes.map { it.toDTO(enderecoRepository) }
    }


    fun buscarEventoAtivoPorGuia(idGuia: Long): List<Map<String, Any>> {
        val eventos = eventoRepository.buscarEventoAtivoPorGuia(idGuia)
        return eventos
    }


        fun getAtivacoesPorEvento(idAtivacao: Long): List<AtivacaoEventoDTO> {
            val ativacoes = guiaRepository.findByEventoId(idAtivacao)
            if (ativacoes.isEmpty()) {
                throw RuntimeException("Evento não encontrado")
            }
            return ativacoes.map { it.toDTO(enderecoRepository) } // passa o repo
        }

    fun getGpxPorAtivacao(ativacaoId: Long): ByteArray? {
        val ativacao = ativacaoEventoRepository.findById(ativacaoId)
            .orElseThrow { RuntimeException("Ativação não encontrada") }

        val gpx = ativacao.evento?.caminho_arquivo_evento
        return gpx?.toByteArray(Charsets.UTF_8)
    }

}
