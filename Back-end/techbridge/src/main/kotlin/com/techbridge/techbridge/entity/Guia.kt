package com.techbridge.techbridge.entity

import com.techbridge.techbridge.enums.TipoUsuario
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue(value = "2")
class Guia : Usuario(
    tipo_usuario = TipoUsuario.GUIA
) {

}
