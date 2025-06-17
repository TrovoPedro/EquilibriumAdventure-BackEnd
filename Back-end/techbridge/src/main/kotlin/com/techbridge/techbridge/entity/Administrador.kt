package com.techbridge.techbridge.entity

import com.techbridge.techbridge.enums.TipoUsuario
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType

@Entity
@DiscriminatorValue(value = "3") // assumindo que ADMINISTRADOR é 0 no enum
class Administrador : Usuario(
    tipo_usuario = TipoUsuario.ADMINISTRADOR
)
{

}