package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Administrador
import com.techbridge.techbridge.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AdministradorRepository: JpaRepository<Administrador, Int> {

    @Query("SELECT u FROM Usuario u WHERE u.fk_tipo_usuario = :tipo")
    fun findByTipoUsuario(@Param("tipo") tipo: Int): List<Usuario>

}