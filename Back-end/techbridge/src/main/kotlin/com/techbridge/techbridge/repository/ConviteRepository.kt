// src/main/kotlin/com/techbridge/techbridge/repository/ConviteRepository.kt
package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Convite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConviteRepository : JpaRepository<Convite, Long> {
    fun findByAventureiroIdUsuario(aventureiroId: Long): List<Convite>
}