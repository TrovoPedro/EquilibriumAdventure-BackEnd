package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.AgendaRequestDTO
import com.techbridge.techbridge.dto.AgendaResponseDTO
import com.techbridge.techbridge.repository.AgendaResponsavelRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/agendas")
class AgendaResponsavelController(
    private val agendaRepository: AgendaResponsavelRepository,
    private val usuarioRepository: UsuarioRepository
) {

    @GetMapping("/disponiveis")
    fun listarDatasDisponiveis(): List<AgendaResponseDTO> {
        val agendas = agendaRepository.findAllDisponiveis()

        return agendas.map { AgendaResponseDTO(it.idAgenda, it.dataDisponivel, it.fkresponsavel.nome.toString()) }
    }

    @PostMapping
    fun adicionarDisponibilidade(@RequestBody dto: AgendaRequestDTO): AgendaResponseDTO {
        val guia = usuarioRepository.findById(dto.fkGuia!!)
            .orElseThrow { RuntimeException("Guia não encontrado") }

        val novaAgenda = agendaRepository.save(dto.toEntity(guia))

        return AgendaResponseDTO(novaAgenda.idAgenda, novaAgenda.dataDisponivel, novaAgenda.fkresponsavel.nome.toString())
    }

    @GetMapping
    fun listarAgenda(): List<AgendaResponseDTO> {
        return agendaRepository.findAll().map {
            AgendaResponseDTO(
                it.idAgenda,
                it.dataDisponivel,
                it.fkresponsavel.nome.toString()
            )
        }
    }

}