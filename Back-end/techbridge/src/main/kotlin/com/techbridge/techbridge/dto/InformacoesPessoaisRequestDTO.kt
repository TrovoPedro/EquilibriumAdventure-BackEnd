package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.enums.Nivel
import java.util.*

data class InformacoesPessoaisRequestDTO(
    var data_nascimento: Date?,
    var cpf: String?,
    var rg: String?,
    var contato_emergencia: String?,
    var endereco: Long,
    var nivel: Nivel,
    var usuario: Long,
    var relatorioAnamnese: String?,
    var idioma: String?,
    var questionario_respondido: Boolean?
) {
    fun toEntity(): InformacoesPessoais {
        val entidade = InformacoesPessoais()
        entidade.data_nascimento = data_nascimento
        entidade.cpf = cpf
        entidade.rg = rg
        entidade.contato_emergencia = contato_emergencia
        entidade.endereco = endereco
        entidade.nivel = nivel
        entidade.usuario = usuario
        entidade.relatorioAnamnese = relatorioAnamnese
        entidade.idioma = idioma
        entidade.questionario_respondido = questionario_respondido
        return entidade
    }
}
