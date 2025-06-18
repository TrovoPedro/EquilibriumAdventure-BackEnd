package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Aventureiro
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.enums.TipoUsuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AventureiroRepository: JpaRepository<Aventureiro, Int> {
    @Query("""
        SELECT u FROM Usuario u 
        WHERE u.idUsuario = :idUsuario AND u.tipo_usuario = :tipoUsuario
    """)
    fun findByIdUsuarioAndTipoUsuario(idUsuario: Long, tipo_usuario: TipoUsuario): Aventureiro?

    @Query("SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario and u.tipo_usuario = :tipo_usuario")
    fun findByIdAndTipo(@Param("idUsuario") idUsuario: Int, @Param("tipo_usuario") tipo_usuario: TipoUsuario): Usuario?


    fun findByIdUsuario(idUsuario: Long): Aventureiro?

    fun save(aventureiro: Aventureiro): Aventureiro


}