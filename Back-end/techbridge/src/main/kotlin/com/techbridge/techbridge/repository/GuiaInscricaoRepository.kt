package com.techbridge.techbridge.repository

import org.springframework.data.jpa.repository.JpaRepository

interface GuiaInscricaoRepository: JpaRepository<Inscricao, Int> {

    fun findByFkUsuarioAndFkAtivacaoEvento(fkUsuario: Int, fkAtivacaoEvento: Int): MutableList<Inscricao>?
}