package com.techbridge.techbridge.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Evento(
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id // do pacote jakarta.persistence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idEvento:Int?,
    var nome: String = "",
    var descricao: String = "",
    var nivel_dificuldade: String = "",
    var distancia_km: Double = 0.0,
    var contador_trilha: Int = 0,
    var fkResponsavel: Int?,
    var fkEndereco: Int?
) {


}