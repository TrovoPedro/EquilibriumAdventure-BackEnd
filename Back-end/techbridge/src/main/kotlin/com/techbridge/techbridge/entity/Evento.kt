package com.techbridge.techbridge.entity

import jakarta.persistence.*

@Entity
@Table(name = "evento")
class Evento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvento") // <- mapeia o nome correto da coluna
    var idEvento: Int? = null,
    var nome: String = "",
    var descricao: String = "",
    var nivel_dificuldade: String = "",
    var distancia_km: Double = 0.0,
    var contador_trilha: Int = 0,
    var fk_responsavel: Int? = null,
    var fk_endereco: Int? = null
)
