package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.AgendaRequestDTO
import com.techbridge.techbridge.dto.AgendaResponseDTO
import com.techbridge.techbridge.entity.AgendaResponsavel
import com.techbridge.techbridge.repository.AgendaResponsavelRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class AgendaResponsavelService(
    private val agendaRepository: AgendaResponsavelRepository,
    private val usuarioRepository: UsuarioRepository
) {

    fun listarDatasDisponiveis(): List<AgendaResponseDTO> {
        val agendas = agendaRepository.findAllDisponiveis()

        return agendas.map {
            AgendaResponseDTO(
                dataDisponivel = it.dataDisponivel,
                nomeGuia = it.fkresponsavel?.nome.toString()
            )
        }
    }

    fun adicionarDisponibilidade(dto: AgendaRequestDTO): AgendaResponseDTO {
        val guia = usuarioRepository.findById(dto.fkGuia!!)
            .orElseThrow { RuntimeException("Guia não encontrado") }

        val novaAgenda = agendaRepository.save(dto.toEntity(guia))

        return AgendaResponseDTO(
            dataDisponivel = novaAgenda.dataDisponivel,
            nomeGuia = novaAgenda.fkresponsavel?.idUsuario.toString()
        )
    }

    fun listarAgenda(): List<AgendaResponseDTO> {
        return agendaRepository.findAll().map {
            AgendaResponseDTO(
                dataDisponivel = it.dataDisponivel,
                nomeGuia = it.fkresponsavel?.nome.toString()
            )
        }
    }
}