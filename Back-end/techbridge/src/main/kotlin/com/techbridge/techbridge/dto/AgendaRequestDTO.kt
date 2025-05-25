package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.AgendaResponsavel
import com.techbridge.techbridge.entity.Usuario
import java.time.LocalDateTime

data class AgendaRequestDTO(
    val dataDisponivel: String,
    val fkGuia: Int?
) {
    fun toEntity(guia: Usuario) = AgendaResponsavel(
        dataDisponivel = LocalDateTime.parse(dataDisponivel),
        fkresponsavel = guia
    )
}
