package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Endereco
import com.techbridge.techbridge.enums.Nivel
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
data class InformacoesPessoaisResponseDTO(
    private var contato_emergencia: String?,
    private var endereco: Long,
    private var nivel: Nivel,
    private var usuario: Long,
    private var relatorioAnamnese: String?,
    private var idioma: String?,
    private var questionario_respondido: Boolean?,
) {
}