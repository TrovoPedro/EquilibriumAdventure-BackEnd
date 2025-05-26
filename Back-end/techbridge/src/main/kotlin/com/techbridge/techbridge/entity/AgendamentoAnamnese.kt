package com.techbridge.techbridge.entity

import jakarta.persistence.*

@Entity
@Table(name = "agendamento_anamnese")
data class AgendamentoAnamnese(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anamnese")
    val idAnamnese: Int = 0,

    @Column(name = "fk_data", nullable = false)
    val fkData: Int,

    @Column(name = "fk_aventureiro", nullable = false)
    val fkAventureiro: Int
) {
    constructor() : this(0, 0, 0)
}