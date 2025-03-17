package com.techbridge.techbridge

import org.springframework.web.bind.annotation.RestController

open class Guia:Usuario() {
    fun setTipoUsuario(novoTipo: String) {
        val guia = Guia()
        guia.tipoUsuario = 2
    }
}