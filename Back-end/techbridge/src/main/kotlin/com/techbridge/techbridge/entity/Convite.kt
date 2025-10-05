package com.techbridge.techbridge.entity

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "convite")
data class Convite(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_convite")
    val idConvite: Long = 0,

    @Column(name = "data_convite", nullable = false)
    val dataConvite: LocalDateTime = LocalDateTime.now(),

    @Column(name = "email_convidado", nullable = false)
    val emailConvidado: String = "",

    @Column(name = "convite_aceito", nullable = true)
    var conviteAceito: Boolean? = null,

    @ManyToOne
    @JoinColumn(name = "fk_aventureiro")
    var aventureiro: Usuario? = null,

    @ManyToOne
    @JoinColumn(name = "fk_convidado")
    var convidado: Usuario? = null,
) {
    constructor() : this(0, LocalDateTime.now(), "", null, null, null)
}