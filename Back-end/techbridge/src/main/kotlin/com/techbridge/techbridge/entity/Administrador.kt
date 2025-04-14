package com.techbridge.techbridge.entity

import jakarta.persistence.Entity

@Entity
class Administrador : Usuario() {

    fun setTipoUsuario(novoTipo: String) {
        this.fk_tipo_usuario = 1
    }

}
