    package com.techbridge.techbridge.dto

    import com.fasterxml.jackson.annotation.JsonProperty
    import java.time.LocalDateTime

    data class ComentarioRequestDTO(
        val texto: String,

        @JsonProperty("fk_usuario")
        val idUsuario: Long,

        @JsonProperty("fk_evento") // muda para passar o id do evento
        val idEvento: Long
    )
