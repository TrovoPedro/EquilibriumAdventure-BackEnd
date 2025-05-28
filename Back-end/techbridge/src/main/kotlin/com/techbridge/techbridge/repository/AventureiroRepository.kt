package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Aventureiro
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AventureiroRepository: JpaRepository<Aventureiro, Int> {

    fun findById(id: Long): Aventureiro?

    fun findByFkTipoUsuario(fkTipoUsuario: Int): List<Aventureiro>

    fun save(aventureiro: Aventureiro): Aventureiro

    fun deleteById(id: Long)
}