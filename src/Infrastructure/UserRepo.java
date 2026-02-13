package Infrastructure;

import java.util.*;
import Domain.Usuario;
import Utils.Enums.StatusUser;
import javax.swing.JOptionPane;
import java.io.*;
import java.nio.file.*;

public class UserRepo {

    private static List<Usuario> lista_usuarios = new ArrayList<>();

    public static void CriarUsuario(String nome) {
        Optional<Usuario> userOpt = Usuario.tryCreate(nome);
        if (userOpt.isEmpty()) {
            System.out.println("Nome invalido!");
            return;
        }

        Usuario newUser = userOpt.get();
        lista_usuarios.add(newUser);
        System.out.println("Usuário criado!");
    }

    public static void GravarUsuarios() throws IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Usuarios.txt"));

            for (Usuario user : lista_usuarios) {
                writer.write(user.getStringId());
                writer.newLine();
                writer.write(user.Nome);
                writer.newLine();
                writer.write(Integer.toString(user.getCodigoStatus()));
                writer.newLine();
            }

            writer.close();
            System.out.println("Arquivo Usuarios.txt gerado / atualizado!");
    }

    public static void LerArquivo() throws IOException {
        Path arquivo = Paths.get("Usuarios.txt");

        if (Files.exists(arquivo)) {
            BufferedReader reader = new BufferedReader(new FileReader("Usuarios.txt"));

            String linha;
            while ((linha = reader.readLine()) != null) {
                UUID id = UUID.fromString(linha);
                String nome = reader.readLine();
                int codigoStatus = Integer.parseInt(reader.readLine());
                StatusUser status = StatusUser.values()[codigoStatus];

                Optional<Usuario> userOpt = Usuario.tryRehydrate(id, nome, status);
                if (userOpt.isPresent()) {
                    Usuario user = userOpt.get();
                    lista_usuarios.add(user);
                }
            }
            reader.close();
            System.out.println("Usuarios lidos!");
        } else {
            System.out.println("Arquivo usuarios.txt nao existe!");
        }

    }

    public static void ExibirUsuarios() {
        if (!lista_usuarios.isEmpty()) {
            for (Usuario user : lista_usuarios) {
                user.exibirInformacoes();
                System.out.println("===============================================");
            }
        } else {
            System.out.println("Nenhum usuário cadastrado / carregado no sistema!");
        }
    }

    public static Optional<Usuario> BuscarPorID(UUID userId) {
        if (!lista_usuarios.isEmpty()) {
            for (Usuario u : lista_usuarios) {
                if (u.getId().equals(userId)) {
                    return Optional.of(u);
                }
            }
        }
        return Optional.empty();
    }

    public static boolean ApagarUsuario(UUID userId) {
        int opc = 0;
        boolean confirmacao = false;
        System.out.println("Tem certeza que quer apagar o usuário com id: " + userId);
        opc = Integer.parseInt(JOptionPane.showInputDialog("1 - Confirmar \n2 - Cancelar"));
        switch (opc) {
            case 1:
                confirmacao = true;
                break;
            case 2:
                System.out.println("Operação cancelada!");
                return false;
            default:
                System.out.println("Opção inválida!");
                break;
        }
        if (confirmacao) {
            Optional<Usuario> userOpt = BuscarPorID(userId);
            if (userOpt.isPresent()) {
                Usuario u = userOpt.get();
                lista_usuarios.remove(u);
                System.out.println("Usuário removido!");
                return true;
            } else {
                System.out.println("Usuário nao existe!");
                return false;
            }
        }else{
            return false;
        }
    }

    public static void AlterarUsuario(UUID userId) {
        int opc = 0;
        boolean confirmacao = false;
        System.out.println("Tem certeza que quer alterar o usuário com id: " + userId + "?");
        opc = Integer.parseInt(JOptionPane.showInputDialog("1 - Confirmar \n2 - Cancelar"));
        switch (opc) {
            case 1:
                confirmacao = true;
                break;
            case 2:
                System.out.println("Operação cancelada!");
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
        if (confirmacao) {
            Optional<Usuario> userOpt = BuscarPorID(userId);
            if (userOpt.isPresent()) {
                Usuario u = userOpt.get();
                u.setNome(JOptionPane.showInputDialog("Insira o nome"));
            } else {
                System.out.println("Usuário nao existe!");
            }
        }

    }

}
