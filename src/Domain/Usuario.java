package Domain;

import Utils.Enums.StatusUser;
import java.util.Optional;
import java.util.UUID;

public class Usuario {
    private final UUID ID;
    public String Nome;
    public StatusUser Status;

    private Usuario(UUID id, String nome, StatusUser status) {
        this.ID = id;
        this.Nome = nome;
        this.Status = status;
    }

    private Usuario(String nome) {
        this.ID = UUID.randomUUID();
        this.Nome = nome;
        this.Status = StatusUser.ativo;
    }

    public UUID getId() {
        return this.ID;
    }

    public String getStringId(){
        return this.ID.toString();
    }

    public static Optional<Usuario> tryCreate(String nome) {
        if (nome.length() < 4 || nome.isEmpty()) {
            System.out.println("O Nome tem que ter no minimo 4 caracteres!");
            return Optional.empty();
        }

        return Optional.of(new Usuario(nome));
    }

    public static Optional<Usuario> tryRehydrate(UUID id, String nome, StatusUser status) {
        if (id == null || nome.isEmpty()) {
            return Optional.empty();
        }

        Usuario newUsuario = new Usuario(id, nome, status);
        return Optional.of(newUsuario);
    }

    public void exibirInformacoes() {
        System.out.println("ID: " + this.ID);
        System.out.println("Nome: " + this.Nome);
        System.out.println("Status: " + this.Status);

    }

    public boolean estaAtivo() {
        if (this.Status == StatusUser.ativo)
            return true;
        return false;
    }

    public Integer getCodigoStatus() {
        return this.Status.ordinal();
    }

    public void AlterarStatus(){
        StatusUser status = this.Status;
        switch (status) {
            case StatusUser.ativo:
                this.Status = StatusUser.bloqueado;
                System.out.println("O Usuario: "+ this.Nome +" agora esta bloqueado!");
                break;
            case StatusUser.bloqueado:
                this.Status = StatusUser.ativo;
                System.out.println("O Usuario: "+ this.Nome +" agora esta ativo!");
                break;
            default:
                break;
        }
    }

    public void setNome(String novoNome){
        if (novoNome.length() < 4 || novoNome.isEmpty()) {
            System.out.println("O Nome tem que ter no minimo 4 caracteres!");  
            return;
        }
        else{
            this.Nome = novoNome;
            System.out.println("Usuario alterado!");
        }
    }
}
