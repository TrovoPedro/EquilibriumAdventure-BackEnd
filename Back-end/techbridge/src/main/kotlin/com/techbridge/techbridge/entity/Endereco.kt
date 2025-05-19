package com.techbridge.techbridge.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idEndereco: Long? = null

    private val rua: String? = null
    private val numero: String? = null
    private val complemento: String? = null
    private val bairro: String? = null
    private val cidade: String? = null
    private val estado: String? = null
    private val cep: String? = null
}