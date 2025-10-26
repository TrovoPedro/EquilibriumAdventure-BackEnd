import java.time.LocalDate
import java.util.*
import java.util.Base64

data class InscricaoAgendaDTO(
    val idInscricao: Long?,
    val idAtivacaoEvento: Long?,
    val idUsuario: Long?,
    val dataInscricao: LocalDate?,
    val nomeEvento: String?,
    val dataAtivacao: LocalDate?,
    val imagemEvento: String?
) {
    constructor(
        idInscricao: Long?,
        idAtivacaoEvento: Long?,
        idUsuario: Long?,
        dataInscricao: Date?,
        nomeEvento: String?,
        dataAtivacao: Date?,
        imagemEventoBytes: ByteArray?
    ) : this(
        idInscricao,
        idAtivacaoEvento,
        idUsuario,
        (dataInscricao as? java.sql.Date)?.toLocalDate(),
        nomeEvento,
        (dataAtivacao as? java.sql.Date)?.toLocalDate(),
        imagemEventoBytes?.let { Base64.getEncoder().encodeToString(it) }
    )
}
