package com.techbridge.techbridge.dto

import java.time.LocalDate
import java.util.Date

data class InscricaoAgendaDTO(
    val idInscricao: Long?,
    val idAtivacaoEvento: Long?,
    val idUsuario: Long?,
    val dataInscricao: LocalDate?,
    val nomeEvento: String?,
    val dataAtivacao: LocalDate?
) {
    constructor(
        idInscricao: Long?,
        idAtivacaoEvento: Long?,
        idUsuario: Long?,
        dataInscricao: Date?,
        nomeEvento: String?,
        dataAtivacao: Date?
    ) : this(
        idInscricao,
        idAtivacaoEvento,
        idUsuario,
        (dataInscricao as? java.sql.Date)?.toLocalDate(),
        nomeEvento,
        (dataAtivacao as? java.sql.Date)?.toLocalDate()
    )
}
