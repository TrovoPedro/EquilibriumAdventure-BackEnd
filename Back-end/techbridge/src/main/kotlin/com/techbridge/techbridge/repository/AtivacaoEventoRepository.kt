package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.enums.EstadoEvento
import org.springframework.data.jpa.repository.JpaRepository

interface AtivacaoEventoRepository : JpaRepository<AtivacaoEvento, Long>{
    fun findByEventoIdAndEstadoIn(eventoId: Long, estados: List<EstadoEvento>): AtivacaoEvento?
}
