package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.EventoRequestDTO
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
            resposavel = novoEvento.resposavel,
            endereco = novoEvento.endereco
        )
    }

    fun getEvento(){

    }

}