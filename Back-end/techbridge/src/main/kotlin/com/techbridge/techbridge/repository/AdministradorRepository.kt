package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Administrador
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.enums.TipoUsuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AdministradorRepository: JpaRepository<Administrador, Int> {

    fun save(administrador: Administrador): Administrador

    @Query("SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario and u.tipo_usuario = :tipoUsuario")
    fun findByIdAndTipo(@Param("idUsuario") idUsuario: Int, @Param("tipo_usuario") tipo_usuario: TipoUsuario): Usuario?

    fun findByIdUsuario(id: Long): Administrador?
}