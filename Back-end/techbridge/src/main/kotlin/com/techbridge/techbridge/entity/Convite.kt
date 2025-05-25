package com.techbridge.techbridge.entity

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "convite")
data class Convite(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_convite")
    val id: Int = 0,

    @Column(name = "data_convite", nullable = false)
    val dataConvite: LocalDateTime = LocalDateTime.now(),

    @Column(name = "email_convidade", nullable = false)
    val emailConvidado: String = "",

    @Column(name = "convite_aceito", nullable = true)
    var conviteAceito: Boolean? = null,

    @ManyToOne
    @JoinColumn(name = "fk_aventureiro")
    val aventureiro: Usuario,

    @ManyToOne
    @JoinColumn(name = "fk_convidado")
    val fkConvidado: Usuario? = null,
) {
    constructor() : this(0, LocalDateTime.now(), "", null, Usuario(), null)
}