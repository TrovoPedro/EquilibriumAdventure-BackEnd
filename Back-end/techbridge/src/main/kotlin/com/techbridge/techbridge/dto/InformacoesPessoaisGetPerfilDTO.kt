package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Endereco
import com.techbridge.techbridge.enums.Nivel
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.Date

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
data class InformacoesPessoaisGetPerfilDTO(
    val telefone_contato: String?,
    val nome: String?,
    val data_nascimento: Date?,
    val nivel: Nivel,
    val rua: String?
)

{

}
