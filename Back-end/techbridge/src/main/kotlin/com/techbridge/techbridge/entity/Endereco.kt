package com.techbridge.techbridge.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Endereco(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id_endereco: Long? = null,

    var rua: String? = null,
    var numero: String? = null,
    var complemento: String? = null,
    var bairro: String? = null,
    var cidade: String? = null,
    var estado: String? = null,
    var cep: String? = null
)
