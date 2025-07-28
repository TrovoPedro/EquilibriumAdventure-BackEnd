package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.RespostaAventureiro
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RespostaAventureiroRepository : JpaRepository<RespostaAventureiro, Int> {

    fun findByUsuarioIdUsuario(idUsuario: Long): List<RespostaAventureiro>

    fun existsByUsuarioIdUsuario(idUsuario: Long): Boolean

}
