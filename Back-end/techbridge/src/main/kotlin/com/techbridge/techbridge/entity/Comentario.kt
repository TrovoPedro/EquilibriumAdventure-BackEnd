package com.techbridge.techbridge.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comentario")
data class Comentario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    val id: Int = 0,

    @Column(name = "texto", nullable = false)
    val texto: String = "",

    @Column(name = "data_comentario", nullable = false)
    val dataComentario: LocalDateTime = LocalDateTime.now(),

    @Column(name = "avaliacao", nullable = true)
    val avaliacao: Int? = null,

    @Column(name = "fk_usuario", nullable = false)
    val fkUsuario: Int = 0,

    @Column(name = "fk_ativacao_evento", nullable = false)
    val fkAtivacaoEvento: Int = 0
) {
    constructor() : this(0, "", LocalDateTime.now(), null, 0, 0)
}