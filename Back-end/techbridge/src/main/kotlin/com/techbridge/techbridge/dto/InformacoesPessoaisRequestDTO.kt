package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.enums.Nivel
import java.util.*

data class InformacoesPessoaisRequestDTO(
    var dataNascimento: Date? = null,
    var cpf: String? = null,
    var rg: String? = null,
    var contatoEmergencia: String? = null,
    var endereco: Long = 0,
    var nivel: Nivel? = null,
    var usuario: Long? = null,
    var relatorioAnamnese: String? = null,
    var idioma: String? = null,
    var questionarioRespondido: Boolean? = null
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
