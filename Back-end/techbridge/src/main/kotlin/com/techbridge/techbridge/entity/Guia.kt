package com.techbridge.techbridge.entity

import jakarta.persistence.Entity

@Entity
class Guia : Usuario() {

    fun setTipoUsuario(novoTipo: String) {
        this.fk_tipo_usuario = 1
    }

}
