import com.techbridge.techbridge.dto.EnderecoDTO
import com.techbridge.techbridge.entity.Endereco

fun Endereco.toDTO(): EnderecoDTO {
    return EnderecoDTO(
        rua = this.rua,
        numero = this.numero,
        complemento = this.complemento,
        bairro = this.bairro,
        cidade = this.cidade,
        estado = this.estado,
        cep = this.cep
    )
}
