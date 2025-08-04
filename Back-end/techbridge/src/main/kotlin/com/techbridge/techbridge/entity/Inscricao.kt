package com.techbridge.techbridge.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "inscricao")
class Inscricao(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscricao")
    val idInscricao: Long? = null,

    @ManyToOne
    @JoinColumn(name = "fk_aventureiro", nullable = false)
    val aventureiro: Usuario,

    @ManyToOne
    @JoinColumn(name = "fk_ativacao_evento", nullable = false)
    val ativacaoEvento: AtivacaoEvento,

    @Column(name = "data_inscricao", nullable = false)
    val dataInscricao: LocalDateTime = LocalDateTime.now(),

    @Column(name = "avaliacao")
    val avaliacao: Int? = null
) {

    constructor() : this(
        ativacaoEvento = AtivacaoEvento(),
        aventureiro = Usuario(),
        dataInscricao = LocalDateTime.now()
    )
}