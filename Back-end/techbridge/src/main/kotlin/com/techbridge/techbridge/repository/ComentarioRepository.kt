package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Comentario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ComentarioRepository : JpaRepository<Comentario, Int> {
    fun findByAtivacaoEvento_IdAtivacao(idAtivacao: Long): List<Comentario>
}

