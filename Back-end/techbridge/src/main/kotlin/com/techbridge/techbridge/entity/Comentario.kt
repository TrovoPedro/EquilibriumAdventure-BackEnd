package com.techbridge.techbridge.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comentario")
data class Comentario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    val id: Long = 0,

    @Column(name = "texto", nullable = false)
    val texto: String = "",

    @Column(name = "data_comentario", nullable = false)
    val dataComentario: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "fk_usuario", nullable = false)
    val usuario: Usuario? = null,

    @ManyToOne
    @JoinColumn(name = "fk_ativacao_evento", nullable = false)
    val ativacaoEvento: AtivacaoEvento? = null
) {
    constructor() : this(0, "", LocalDateTime.now(), null, null,)
}