package com.techbridge.techbridge.entity

data class UsuarioLogado(

    open var email: String,
    open var senha: String,
    open var autenticado: Boolean = false
)
