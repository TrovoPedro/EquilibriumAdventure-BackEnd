package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.AnamneseRequestDTO
import com.techbridge.techbridge.dto.AnamneseResponseDTO
import com.techbridge.techbridge.entity.AgendamentoAnamnese
import com.techbridge.techbridge.repository.AgendamentoAnamneseRepository
import com.techbridge.techbridge.repository.AgendaResponsavelRepository
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AgendamentoAnamneseService(
    private val agendamentoRepository: AgendamentoAnamneseRepository,
    private val agendaResponsavelRepository: AgendaResponsavelRepository,
    private val usuarioRepository: UsuarioRepository,
    private val informacoesPessoaisRepository: InformacoesPessoaisRepository
) {

    fun listarHistorico(): List<Map<String, Any>> {
        return agendamentoRepository.listarHistorico()
    }

    fun listarDatasDisponiveisPorResponsavel(fkResponsavel: Int): List<Map<String, Any>> {
        return agendamentoRepository.listarDatasDisponiveisPorResponsavel(fkResponsavel)
    }

    fun listarPorResponsavel(fkResponsavel: Int): List<AnamneseResponseDTO> {
        return agendamentoRepository.findByAgendaResponsavelFkresponsavelIdUsuario(fkResponsavel).map { agendamento ->
            AnamneseResponseDTO(
                id = agendamento.id,
                dataDisponivel = agendamento.agendaResponsavel?.dataDisponivel
                    ?: throw IllegalStateException("Data indisponível"),
                nomeAventureiro = agendamento.aventureiro?.nome
                    ?: throw IllegalStateException("Aventureiro não encontrado"),
                fkResponsavel = agendamento.agendaResponsavel?.fkresponsavel?.idUsuario?.toLong()
                    ?: throw IllegalStateException("Responsável não encontrado"),
                fkAventureiro = agendamento.aventureiro?.idUsuario?.toLong()
                    ?: throw IllegalStateException("Aventureiro não encontrado")
            )
        }
    }

    fun salvarAgendamento(dto: AnamneseRequestDTO): AnamneseResponseDTO {
        val agendaResponsavel = agendaResponsavelRepository.findById(dto.fkData)
            .orElseThrow { IllegalArgumentException("Agenda com ID ${dto.fkData} não encontrada.") }

        val usuario = usuarioRepository.findById(dto.fkAventureiro)
            .orElseThrow { IllegalArgumentException("Usuário com ID ${dto.fkAventureiro} não encontrado.") }

        val novoAgendamento = AgendamentoAnamnese(
            agendaResponsavel = agendaResponsavel,
            aventureiro = usuario
        )

        val agendamentoSalvo = agendamentoRepository.save(novoAgendamento)

        return AnamneseResponseDTO(
            id = agendamentoSalvo.id,
            dataDisponivel = agendamentoSalvo.agendaResponsavel?.dataDisponivel
                ?: throw IllegalStateException("Data indisponível"),
            nomeAventureiro = agendamentoSalvo.aventureiro?.nome
                ?: throw IllegalStateException("Aventureiro não encontrado"),
            fkResponsavel = agendamentoSalvo.agendaResponsavel?.fkresponsavel?.idUsuario?.toLong()
                ?: throw IllegalStateException("Responsável não encontrado"),
            fkAventureiro = agendamentoSalvo.aventureiro?.idUsuario?.toLong()
                ?: throw IllegalStateException("Aventureiro não encontrado")
        )
    }

    fun listarPorAventureiro(fkAventureiro: Int): List<AnamneseResponseDTO> {
        return agendamentoRepository.findByAventureiroIdUsuario(fkAventureiro).map { agendamento ->
            AnamneseResponseDTO(
                id = agendamento.id,
                dataDisponivel = agendamento.agendaResponsavel?.dataDisponivel
                    ?: throw IllegalStateException("Data indisponível"),
                nomeAventureiro = agendamento.aventureiro?.nome
                    ?: throw IllegalStateException("Aventureiro não encontrado"),
                fkResponsavel = agendamento.agendaResponsavel?.fkresponsavel?.idUsuario?.toLong()
                    ?: throw IllegalStateException("Responsável não encontrado"),
                fkAventureiro = agendamento.aventureiro?.idUsuario?.toLong()
                    ?: throw IllegalStateException("Aventureiro não encontrado")
            )
        }
    }
}