package com.techbridge.techbridge.dto

import com.techbridge.techbridge.enums.Nivel
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.*

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
data class InscricaoEventoDTO(
    private var evento: Long,
    private var usuario: Long,
    private var dataInscricao: Date,
) {
}