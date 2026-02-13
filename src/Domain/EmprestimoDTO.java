package Domain;

import java.time.LocalDate;
import java.util.UUID;
import Utils.Enums.StatusEmprestimo;

public class EmprestimoDTO {

    private UUID idEmprestimo;
    private UUID idUsuario;
    private UUID idLivro;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal; // pode ser null

    private StatusEmprestimo status;

    public EmprestimoDTO(
        UUID idEmprestimo,
        UUID idUsuario,
        UUID idLivro,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucaoPrevista,
        LocalDate dataDevolucaoReal,
        StatusEmprestimo status
    ) {
        this.idEmprestimo = idEmprestimo;
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoReal = dataDevolucaoReal;
        this.status = status;
    }

    public UUID getIdEmprestimo() {
        return idEmprestimo;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public UUID getIdLivro() {
        return idLivro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }
}