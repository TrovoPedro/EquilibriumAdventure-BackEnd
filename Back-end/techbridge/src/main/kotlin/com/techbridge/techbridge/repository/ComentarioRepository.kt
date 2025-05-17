package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Comentario
import org.springframework.data.jpa.repository.JpaRepository

interface ComentarioRepository : JpaRepository<Comentario, Int>