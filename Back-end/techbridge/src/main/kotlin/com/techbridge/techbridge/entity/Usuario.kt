package com.techbridge.techbridge.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class Usuario(
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idUsuario: Int? = null,
    var nome: String = "",
    var email: String = "",
    @JsonIgnore
    private var senha: String = "",
    var fkTipo_usuario: Int = 1,
    var fkNivel: Int = 0
) {

    fun setSenha(novaSenha: String) {
        senha = novaSenha
    }

    fun getSenha(): String {
        return senha
    }
}
