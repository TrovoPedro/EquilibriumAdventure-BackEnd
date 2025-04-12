package com.techbridge.techbridge.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class Usuario {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var idUsuario: Int? = null

    open var nome: String = ""

    open var email: String = ""

    @JsonIgnore
    private var _senha: String = ""

    open var fkTipo_usuario: Int = 1

    open var fkNivel: Int = 0

    open fun getSenha(): String {
        return _senha
    }

    open fun setSenha(novaSenha: String) {
        _senha = novaSenha
    }
}
