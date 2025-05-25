package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Int> {
    fun findByEmail(email: String)
    fun findUsuariosById_usuario(id_usuario: Long?)
}