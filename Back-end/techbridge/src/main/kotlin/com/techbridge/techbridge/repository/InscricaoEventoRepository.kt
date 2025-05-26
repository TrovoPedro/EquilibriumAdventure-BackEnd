package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.InscricaoEvento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InscricoesEventoRepository : JpaRepository<InscricaoEvento, Long>