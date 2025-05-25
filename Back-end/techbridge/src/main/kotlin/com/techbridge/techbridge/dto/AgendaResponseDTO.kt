package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.AgendaResponsavel
import com.techbridge.techbridge.entity.Guia
import java.time.LocalDateTime

data class AgendaResponseDTO(
    val id: Int,
    val dataDisponivel: LocalDateTime,
    val nomeGuia: String
) {
    fun toEntity(guia: Guia) = AgendaResponsavel(
        idAgenda = id,
        dataDisponivel = dataDisponivel,
        fkresponsavel = guia
    )
}

