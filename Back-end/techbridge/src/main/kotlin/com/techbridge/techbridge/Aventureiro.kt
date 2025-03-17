import com.techbridge.techbridge.Guia
import com.techbridge.techbridge.Usuario

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
            val aventureiro = Usuario()
            aventureiro.tipoUsuario = 1
        }
}
