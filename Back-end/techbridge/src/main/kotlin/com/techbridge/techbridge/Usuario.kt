package com.techbridge.techbridge

import com.fasterxml.jackson.annotation.JsonIgnore

open class Usuario (
    var nome: String = "",
    var email: String = "",
    @JsonIgnore
    private var senha: String = "",
    var tipoUsuario: Int = 1
)
    {

    fun setSenha(novaSenha: String) {
        senha = novaSenha
    }

    fun getSenha(): String {
        return senha
    }

}