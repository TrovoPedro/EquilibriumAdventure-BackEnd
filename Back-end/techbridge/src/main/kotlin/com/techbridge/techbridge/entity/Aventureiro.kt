package com.techbridge.techbridge.entity

data class Aventureiro (
    var dtNascAventureiro: String?,
    var telContatoAventureiro: String?,
    var cpfAventureiro: String?,
    var rgAventureiro: String?,
    var enderecoCidadeAventureiro: String?,
    var enderecoCepAventureiro: String?,
    var enderecoRuaNumAventureiro: String?,
    var enderecoEmergenciaAventureiro: String?,
) : Usuario()
    {
        fun setTipoUsuario(novoTipo: String) {
            this.fk_tipo_usuario = 1
        }
}
