package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.UsuarioRequestDTO
import com.techbridge.techbridge.dto.UsuarioResponseDTO
import com.techbridge.techbridge.mapper.toResponseDTO
import com.techbridge.techbridge.repository.AdministradorGuiaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdministradorService {

    @Autowired
    lateinit var administradorGuiaRepository: AdministradorGuiaRepository

    fun salvarGuia(usuario: UsuarioRequestDTO): UsuarioResponseDTO {

        if (usuario == null) {
            throw RuntimeException("Evento em branco")
        }

        administradorGuiaRepository.save(usuario.toEntity())

        return usuario.toResponseDTO()
    }
}