package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {
    fun findByEmail(email: String): Usuario?

    @Query("SELECT u FROM Usuario u WHERE u.tipo_usuario IN ('GUIA', 'ADMINISTRADOR')")
    fun findGuiaOuAdministrador(): List<Usuario>
}