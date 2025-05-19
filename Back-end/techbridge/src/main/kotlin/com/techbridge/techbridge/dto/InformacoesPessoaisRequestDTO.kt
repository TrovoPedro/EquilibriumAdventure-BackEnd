package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Endereco
import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.enums.Nivel
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.*

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    fun toEntity() = InformacoesPessoais(
        data_nascimento = data_nascimento,
        cpf = cpf,
        rg = rg,
        contato_emergencia = contato_emergencia,
        endereco = endereco,
        nivel = nivel,
        usuario = usuario,
        relatorioAnamnese = relatorioAnamnese,
        idioma = idioma,
        questionario_respondido = questionario_respondido
    )
}