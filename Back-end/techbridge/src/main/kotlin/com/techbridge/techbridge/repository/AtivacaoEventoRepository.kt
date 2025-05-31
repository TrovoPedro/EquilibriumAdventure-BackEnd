package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.AtivacaoEvento
import org.springframework.data.jpa.repository.JpaRepository

interface AtivacaoEventoRepository : JpaRepository<AtivacaoEvento, Long>
