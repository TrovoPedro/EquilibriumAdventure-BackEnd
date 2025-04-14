package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository : JpaRepository<Usuario, Int> {

}