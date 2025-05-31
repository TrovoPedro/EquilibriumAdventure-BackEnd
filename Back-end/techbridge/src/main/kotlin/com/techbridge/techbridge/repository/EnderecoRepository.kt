package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Endereco
import org.springframework.data.jpa.repository.JpaRepository

interface EnderecoRepository: JpaRepository<Endereco, Long> {
}