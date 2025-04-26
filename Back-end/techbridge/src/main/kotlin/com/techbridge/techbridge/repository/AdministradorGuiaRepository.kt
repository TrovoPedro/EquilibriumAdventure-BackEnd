package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AdministradorGuiaRepository: JpaRepository<Usuario, Int> {

    @Query("SELECT u FROM Usuario u WHERE u.fk_tipo_usuario = :tipo")
    fun findByTipoUsuario(@Param("tipo") tipo: Int): List<Usuario>

}