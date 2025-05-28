package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.AgendaRequestDTO
import com.techbridge.techbridge.dto.AgendaResponseDTO
import com.techbridge.techbridge.repository.AgendaResponsavelRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/agendas")
class AgendaResponsavelController(
    private val agendaRepository: AgendaResponsavelRepository,
    private val usuarioRepository: UsuarioRepository
) {

    @GetMapping("/disponiveis")
    fun listarDatasDisponiveis(): ResponseEntity<List<AgendaResponseDTO>> {
        return try {
            val agendas = agendaRepository.findAllDisponiveis()
            if (agendas.isEmpty())
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            else
                ResponseEntity.ok(agendas.map { AgendaResponseDTO(it.dataDisponivel, it.fkresponsavel?.nome.toString()) })
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PostMapping
    fun adicionarDisponibilidade(@RequestBody dto: AgendaRequestDTO): ResponseEntity<AgendaResponseDTO> {
        return try {
            val usuarioOpt = usuarioRepository.findById(dto.fkGuia?.toLong()!!)
            if (!usuarioOpt.isPresent)
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            else {
                val novaAgenda = agendaRepository.save(dto.toEntity(usuarioOpt.get()))
                ResponseEntity.ok(AgendaResponseDTO(novaAgenda.dataDisponivel, novaAgenda.fkresponsavel?.idUsuario.toString()))
            }
        } catch (e: RuntimeException) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping
    fun listarAgenda(): ResponseEntity<List<AgendaResponseDTO>> {
        return try {
            val agendas = agendaRepository.findAll()
            if (agendas.isEmpty())
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            else
                ResponseEntity.ok(agendas.map { AgendaResponseDTO(it.dataDisponivel, it.fkresponsavel?.nome.toString()) })
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}