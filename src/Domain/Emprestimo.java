package Domain;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import Utils.Enums.StatusEmprestimo;

public class Emprestimo {
    private final UUID ID;
    public Usuario Usuario;
    public Livro Livro;
    public LocalDate DataEmprestimo;
    public LocalDate DataDevolucaoPrevista;
    private LocalDate DataDevolucaoReal;
    public StatusEmprestimo Status;

    private Emprestimo(UUID id, Usuario user, Livro livro, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista,
            LocalDate dataDevolucaoReal, StatusEmprestimo status) {
        this.ID = id;
        this.Usuario = user;
        this.Livro = livro;
        this.DataEmprestimo = dataEmprestimo;
        this.DataDevolucaoPrevista = dataDevolucaoPrevista;
        this.DataDevolucaoReal = dataDevolucaoReal;
        this.Status = status;
    }

    private Emprestimo(Usuario user, Livro livro, LocalDate dataDevolucaoPrevista) {
        this.ID = UUID.randomUUID();
        this.Usuario = user;
        this.Livro = livro;
        this.DataEmprestimo = LocalDate.now();
        this.DataDevolucaoPrevista = dataDevolucaoPrevista;
        this.DataDevolucaoReal = null;
        this.Status = StatusEmprestimo.ativo;
    }

    public UUID getId() {
        return this.ID;
    }

    public String getIdString() {
        return this.ID.toString();
    }

    public String getStringDevolucaoReal() {
        return this.DataDevolucaoReal.toString();
    }

    public static Optional<Emprestimo> tryCreate(Usuario user, Livro livro, LocalDate dataDevolucaoPrevista) {
        if (!user.estaAtivo()) {
            System.out.println("Usuário bloqueado!");
            return Optional.empty();
        }
        if (!livro.estaDisponivel()) {
            System.out.println("Livro indisponível no momento!");
            return Optional.empty();
        }

        livro.estaDisponivel();
        return Optional.of(new Emprestimo(user, livro, dataDevolucaoPrevista));
    }

    public static Optional<Emprestimo> tryRehydrate(UUID id, Usuario user, Livro livro, LocalDate dataEmprestimo,
            LocalDate dataDevolucaoPrevista, LocalDate dataDevolucaoReal, StatusEmprestimo status) {
        if (id == null || user == null || livro == null || dataEmprestimo == null || dataDevolucaoPrevista == null
                || status == null) {
            System.out.println("Campos obrigatórios vazios detectados!");
            return Optional.empty();
        }
        return Optional
                .of(new Emprestimo(id, user, livro, dataEmprestimo, dataDevolucaoPrevista, dataDevolucaoReal, status));
    }

    public void ExibirDados() {
        System.out.println("ID: " + this.ID);
        System.out.println("Usuario: " + this.Usuario.Nome);
        System.out.println("Livro: " + this.Livro.Titulo);
        System.out.println("Data Emprestimo: " + this.DataEmprestimo);
        if (this.DataDevolucaoReal == null) {
            System.out.println("Data Devolucao prevista: " + this.DataDevolucaoPrevista);
        } else {
            System.out.println("Data de Devolucao real: " + this.DataDevolucaoReal);
        }
        System.out.println("Status: " + this.Status);
    }

    public void Devolver() {
        this.DataDevolucaoReal = LocalDate.now();
        this.Status = StatusEmprestimo.devolvido;
        if(!this.Usuario.estaAtivo()){
            this.Usuario.AlterarStatus();
        }
        this.Livro.decrementarEmprestados();
    }

    public boolean estaAtivo() {
        if (this.Status == StatusEmprestimo.ativo || this.Status == StatusEmprestimo.atrasado) {
            return true;
        }
        return false;
    }

    public boolean estaDevolvido() {
        return this.DataDevolucaoReal != null;
    }

    public int getCodigoStatus() {
        return this.Status.ordinal();
    }

    public void MarcarAtraso() {
        this.Status = StatusEmprestimo.atrasado;
    }
}
