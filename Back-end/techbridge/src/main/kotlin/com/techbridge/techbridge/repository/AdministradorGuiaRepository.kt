package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface AdministradorGuiaRepository: JpaRepository<Usuario, Int> {

    fun findByFkTipoUsuario(fkTipoUsuario: Int): List<Usuario>

}