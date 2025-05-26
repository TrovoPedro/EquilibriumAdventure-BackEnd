package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Credenciais
import com.techbridge.techbridge.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {
    fun findByEmail(email: String)
}