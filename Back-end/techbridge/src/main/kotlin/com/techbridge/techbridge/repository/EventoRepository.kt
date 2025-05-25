package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Evento
import org.springframework.data.jpa.repository.JpaRepository

interface EventoRepository: JpaRepository<Evento, Long> {
}