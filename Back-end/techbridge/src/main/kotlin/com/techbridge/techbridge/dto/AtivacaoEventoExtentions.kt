package com.techbridge.techbridge.dto

import AtivacaoEventoDTO
import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.entity.Endereco
import com.techbridge.techbridge.repository.EnderecoRepository
import toDTO

fun AtivacaoEvento.toDTO(enderecoRepository: EnderecoRepository): AtivacaoEventoDTO {
    val eventoDTO = this.evento?.let { ev ->
        // Busca o endereço pelo ID
        val enderecoDTO = ev.endereco?.let { enderecoId ->
            enderecoRepository.findById(enderecoId).orElse(null)?.toDTO()
        }

        EventoAtivacaoDTO(
            idEvento = ev.id_evento,
            nome = ev.nome,
            descricao = ev.descricao,
            nivelDificuldade = ev.nivel_dificuldade,
            distanciaKm = ev.distancia_km,
            responsavel = ev.responsavel,
            endereco = enderecoDTO,
        )
    }

    return AtivacaoEventoDTO(
        idAtivacao = this.idAtivacao,
        horaInicio = this.horaInicio,
        horaFinal = this.horaFinal,
        tempoEstimado = this.tempoEstimado,
        limiteInscritos = this.limiteInscritos,
        dataAtivacao = this.dataAtivacao,
        tipo = this.tipo,
        preco = this.preco,
        estado = this.estado,
        evento = eventoDTO
    )
}


