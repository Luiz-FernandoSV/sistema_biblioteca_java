package Domain;

import java.util.Optional;
import java.util.UUID;

import Utils.Enums.StatusLivro;

public class Livro {
    private final UUID ID;
    public String Titulo;
    public Integer qtdDisponivel;
    public Integer qtdTotal;
    public Integer qtdEmprestados;
    public StatusLivro Status;

    private Livro(UUID id, String titulo, Integer qtdDisponivel, Integer qtdTotal, Integer qtdEmprestados, StatusLivro status){
        this.ID = id;
        this.Titulo = titulo;
        this.qtdEmprestados = qtdEmprestados;
        this.qtdTotal = qtdTotal;
        this.qtdDisponivel = (qtdTotal - qtdEmprestados);
        this.Status = status;
    }

    private Livro(String titulo, Integer qtdTotal){
        this.ID = UUID.randomUUID();
        this.Titulo = titulo;
        this.qtdEmprestados = 0;
        this.qtdDisponivel = qtdTotal - qtdEmprestados;
        this.qtdTotal = qtdTotal;
        this.Status = StatusLivro.disponivel;
    }

    public UUID getId(){
        return this.ID;
    }
    public String getStringId(){
        return this.ID.toString();
    }

    public static Optional<Livro> tryCreate(String titulo, Integer qtdTotal){
        if(titulo.isEmpty()){
            System.out.println("Titulo nao pode ser vazio!");
            return Optional.empty();
        } 
        if(qtdTotal <= 0 || qtdTotal == null){
            System.out.println("Quantidade nao pode ser menor / igual a 0!");
            return Optional.empty();
        } 

        return Optional.of(new Livro(titulo, qtdTotal));
    }

    public static Optional<Livro> tryRehydrate(UUID id, String titulo, Integer qtdDisponivel, Integer qtdTotal, Integer qtdEmprestados, StatusLivro status){
        if(id == null || titulo == null || qtdDisponivel < 0 || qtdTotal <= 0 ||qtdEmprestados > qtdTotal){
            return Optional.empty();
        }

        Livro newLivro = new Livro(id,titulo,qtdDisponivel,qtdTotal,qtdEmprestados,status);
        return Optional.of(newLivro);
    }

    public void exibirInformacoes(){
        System.out.println("ID: "+this.ID);
        System.out.println("Titulo: "+this.Titulo);
        System.out.println("Quantidade Total: "+this.qtdTotal);
        System.out.println("Quantidade Disponivel: "+this.qtdDisponivel);
        System.out.println("Quantidade de emprestimos: "+this.qtdEmprestados);
        System.out.println("Status: "+this.Status);
    }

    public boolean estaDisponivel(){
        if(this.qtdDisponivel == 0){
            this.Status = StatusLivro.indisponivel;
            return false;
        }else{
            this.Status = StatusLivro.disponivel;
            return true;
        } 
    }

    public Integer getCodigoStatus(){
        return this.Status.ordinal();
    }

    public boolean alterarQuantidade(Integer novaQuantidade){
        if(novaQuantidade >= this.qtdEmprestados){
            this.qtdTotal = novaQuantidade;
            this.qtdDisponivel = novaQuantidade - this.qtdEmprestados;
            estaDisponivel();
            return true;
        }else if((novaQuantidade - this.qtdEmprestados) < 0){
            System.out.println("A Nova quantidade nao pode ser menor que a de exemplares já emprestados!");
            return false;
        }else{
            System.out.println("A Nova quantidade nao pode ser menor que a de exemplares disponíveis!");
            return false;
        }
    }

    public void incrementarEmprestados(){
        this.qtdEmprestados++;
        this.qtdDisponivel = this.qtdTotal - this.qtdEmprestados;
        estaDisponivel(); // atualiza o status do livro
    }

    public void decrementarEmprestados(){
        this.qtdEmprestados--;
        this.qtdDisponivel = this.qtdTotal - this.qtdEmprestados;
        estaDisponivel(); // atualiza o status do livro
    }
}
