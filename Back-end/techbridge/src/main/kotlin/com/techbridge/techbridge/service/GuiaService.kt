package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.EventoBaseRequestDTO
import com.techbridge.techbridge.dto.EventoGuiaEnderecoDTO
import com.techbridge.techbridge.dto.EventoRequestDTO
import com.techbridge.techbridge.dto.EventoResponseDTO
import com.techbridge.techbridge.dto.GuiaRequestDTO
import  com.techbridge.techbridge.repository.InformacoesPessoaisRepository
import com.techbridge.techbridge.dto.UsuarioRequestDTO
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.Guia
import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.repository.EnderecoRepository
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.GuiaRepository
import com.techbridge.techbridge.repository.UsuarioRepository
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
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var informacoesPessoaisRepository: InformacoesPessoaisRepository

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

    fun postGuia (novoGuia: GuiaRequestDTO , img_guia: MultipartFile?): Usuario {

        val guia = Usuario().apply {
            nome = novoGuia.nome
            email = novoGuia.email
            senha = novoGuia.senha
            descricao_guia = novoGuia.descricao_guia
            tipo_usuario = novoGuia.tipo_usuario
        }

        guia.img_usuario = img_guia?.bytes

        val guiaSalvo = usuarioRepository.save(guia)

        return guiaSalvo
    }

    fun putEvento(idEvento: Long,idEndereco: Long ,novoEvento: EventoRequestDTO, img_evento: MultipartFile?): Evento {
        val enderecoDto = novoEvento.endereco ?: throw IllegalArgumentException("Endereço é obrigatório")

        val enderecoExistente = enderecoRepository.findById(idEndereco)
            .orElseThrow { RuntimeException("Endereço não encontrado") }

        val enderecoAtualizado = enderecoExistente.copy(
            rua = enderecoDto.rua,
            numero = enderecoDto.numero,
            cidade = enderecoDto.cidade,
            complemento = enderecoDto.complemento,
            estado = enderecoDto.estado,
            cep = enderecoDto.cep
        )
        enderecoRepository.save(enderecoAtualizado)

        val evento = novoEvento.toEntity(enderecoAtualizado.id_endereco)

        evento.id_evento = idEvento
        println(img_evento)
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

        if (eventos.isEmpty()) {
            throw NoSuchElementException("Nenhum evento ativo encontrado para o guia com ID $idGuia.")
        }

        return eventos
    }

}
