package com.techbridge.techbridge.entity

data class UsuarioLogin(

    open var email: String,
    open var senha: String,
    open var autenticado: Boolean = false
)
