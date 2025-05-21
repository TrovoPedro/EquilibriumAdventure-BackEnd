package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.InformacoesPessoais
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InformacoesPessoasRepository : JpaRepository<InformacoesPessoais, Long> {
}