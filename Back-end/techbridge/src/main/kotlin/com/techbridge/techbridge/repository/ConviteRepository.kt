package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Convite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ConviteRepository:JpaRepository<Convite,Long> {
    @Query(value = "SELECT c FROM Convite c WHERE c.fkUsuario = :id_usuario", nativeQuery = true)
    fun findByFkUsuario(id_usuario: Long): List<Convite>

    @Query(
        value = "INSERT INTO convite (data_convite, convite_aceito, fk_usuario) VALUES (CURRENT_TIMESTAMP, :conviteAceito, (SELECT id_usuario FROM usuario WHERE email = :email))",
        nativeQuery = true
    )
    fun enviarConvite(
       @Param("email") email: String,
       @Param("conviteAceito") conviteAceito: Boolean
    ): Int
    fun atualizarConvite(@Param("id_convite") idConvite: Int, @Param("convite_aceito") conviteAceito: Boolean): Int

}