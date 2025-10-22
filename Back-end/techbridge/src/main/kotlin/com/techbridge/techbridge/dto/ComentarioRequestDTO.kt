    package com.techbridge.techbridge.dto

    import com.fasterxml.jackson.annotation.JsonProperty
    import java.time.LocalDateTime

    data class ComentarioRequestDTO(
        val texto: String,
        val idUsuario: Long,
        val idAtivacaoEvento: Long  // antes estava idEvento
    )

