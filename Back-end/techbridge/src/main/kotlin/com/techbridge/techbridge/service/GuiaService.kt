package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.EventoGuiaEnderecoDTO
import com.techbridge.techbridge.dto.EventoRequestDTO
import com.techbridge.techbridge.dto.EventoResponseDTO
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.repository.EventoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GuiaService {

    @Autowired
    lateinit var eventoRepository: EventoRepository

    fun postEvento(novoEvento: EventoRequestDTO): EventoRequestDTO {

        if(novoEvento == null){
            throw RuntimeException("Evento em branco")
        }

        eventoRepository.save(novoEvento.toEntity());

        return EventoRequestDTO(
            nome = novoEvento.nome,
            descricao = novoEvento.descricao,
            nivel_dificuldade = novoEvento.nivel_dificuldade,
            distancia_km = novoEvento.distancia_km,
            responsavel = novoEvento.responsavel,
            endereco = novoEvento.endereco
        )
    }

    fun getEventos(): List<Map<String, Any>> {
        val eventosEncontrados = eventoRepository.listarEventosComResponsavelERua()

        if (eventosEncontrados.isEmpty()) {
            throw RuntimeException("Nenhum evento encontrado")
        }

        return eventosEncontrados
    }

    fun getEventoPorGuia(nome:String): List<Map<String, Any>>{
        val eventosEncotrados = eventoRepository.buscarEventoPorGuia(nome)

        if(eventosEncotrados.isEmpty()){
            throw RuntimeException("Nenhum evento encontrado")
        }

        return eventosEncotrados
    }

}
