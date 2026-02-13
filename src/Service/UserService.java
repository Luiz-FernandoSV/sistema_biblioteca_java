package Service;

import java.io.IOException;
import javax.swing.JOptionPane;
import Infrastructure.UserRepo;
import java.util.UUID;
import java.util.Optional;

import Domain.Usuario;

public class UserService {

    public static void CriarUsuario() throws IOException {
        String nome = JOptionPane.showInputDialog("Insira o nome do usuário: ");
        UserRepo.CriarUsuario(nome);
    }

    public static void GravarUsuarios() throws IOException{
        UserRepo.GravarUsuarios();
    }

    public static void LerArquivo() throws IOException{
        UserRepo.LerArquivo();
    }

    public static void ExibirUsuarios(){
        UserRepo.ExibirUsuarios();
    }

    
    public static void BuscarPorID(){
        ExibirUsuarios();
        UUID idUser = UUID.fromString(JOptionPane.showInputDialog("Insira o ID do usuário a ser buscado: "));
        Optional<Usuario> userOpt = UserRepo.BuscarPorID(idUser);
        if(userOpt.isPresent()){
            Usuario u = userOpt.get();
            u.exibirInformacoes();
        }else{
            System.out.println("Usuário nao existe!");
        }
    }

    public static Optional<Usuario> BuscarUsuario(UUID idUser){
        Optional<Usuario> userOpt = UserRepo.BuscarPorID(idUser);
        if(userOpt.isPresent()){
            Usuario u = userOpt.get();
            return Optional.of(u);
        }else{
            System.out.println("Usuário nao existe!");
            return Optional.empty();
        }
    }

    public static void ApagarUsuario() throws IOException{
        ExibirUsuarios();
        UUID idUser = UUID.fromString(JOptionPane.showInputDialog("Insira o ID do usuário a ser apagado: "));
        if(UserRepo.ApagarUsuario(idUser)){
            EmprestimoService.ApagarEmprestimos(1, idUser);
            UserRepo.GravarUsuarios();
        }
    }

    public static void AlterarUsuario() throws IOException{ 
        ExibirUsuarios();
        UUID idUser = UUID.fromString(JOptionPane.showInputDialog("Insira o ID do usuário a ser alterado: "));
        UserRepo.AlterarUsuario(idUser);
    }

    public static void BloquearUsuario(UUID idUser) throws IOException{
        Optional<Usuario> userOpt = BuscarUsuario(idUser);
        if(userOpt.isPresent()){
            Usuario user = userOpt.get();
            if(user.estaAtivo()){
                user.AlterarStatus();
            }
            UserRepo.GravarUsuarios();
        }
    }

}
