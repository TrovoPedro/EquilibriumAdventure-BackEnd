package com.techbridge.techbridge.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "evento")
data class Evento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    var id_evento: Long? = null,

    var nome: String? = null,
    var descricao: String? = null,
    var nivel_dificuldade: String? = null,
    var distancia_km: Double? = null,

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    var caminho_arquivo_evento: String? = null,

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    var img_evento: ByteArray? = null,

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    var pdf_evento: ByteArray? = null,

    @Column(name = "fk_responsavel", nullable = false)
    var responsavel: Long = 1,

    @Column(name = "fk_endereco", nullable = false)
    var endereco: Long? = null
)
