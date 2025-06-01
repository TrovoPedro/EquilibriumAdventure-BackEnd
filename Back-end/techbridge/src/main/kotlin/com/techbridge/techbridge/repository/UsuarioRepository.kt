package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {
<<<<<<< HEAD
    fun findByEmail(email: String)
    fun findUsuariosById_usuario(id_usuario: Long?)
=======
    fun findByEmail(email: String): Usuario?
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
}