package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.enums.Nivel
import java.util.*

data class InformacoesPessoaisRequestDTO(
    var dataNascimento: Date?,
    var cpf: String?,
    var rg: String?,
    var contatoEmergencia: String?,
    var endereco: Long,
    var nivel: Nivel,
    var usuario: Long,
    var relatorioAnamnese: String?,
    var idioma: String?,
    var questionarioRespondido: Boolean?
) {
    fun toEntity(): InformacoesPessoais {
        val entidade = InformacoesPessoais()
        entidade.dataNascimento = dataNascimento
        entidade.cpf = cpf
        entidade.rg = rg
        entidade.contatoEmergencia = contatoEmergencia
        entidade.endereco = endereco
        entidade.nivel = nivel
        entidade.usuario = usuario
        entidade.relatorioAnamnese = relatorioAnamnese
        entidade.idioma = idioma
        entidade.questionarioRespondido = questionarioRespondido
        return entidade
    }
}
