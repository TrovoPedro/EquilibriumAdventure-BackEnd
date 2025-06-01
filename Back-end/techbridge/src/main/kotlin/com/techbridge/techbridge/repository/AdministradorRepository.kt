package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Administrador
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdministradorRepository: JpaRepository<Administrador, Int> {

}