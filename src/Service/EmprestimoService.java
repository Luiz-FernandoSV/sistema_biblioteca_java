package Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import javax.swing.JOptionPane;
import Domain.*;
import Infrastructure.EmprestimoRepo;
import Utils.Utilidades;

public class EmprestimoService {
    public static void CriarEmprestimo() throws IOException {
        UserService.ExibirUsuarios();
        UUID idUser = UUID.fromString(JOptionPane.showInputDialog("Insira o ID do usuário a ser buscado: "));
        Optional<Usuario> userOpt = UserService.BuscarUsuario(idUser);
        LivroService.ExibirLivros();
        UUID idLivro = UUID.fromString(JOptionPane.showInputDialog("Insira o ID do livro a ser buscado: "));
        Optional<Livro> livroEscolhidoOpt = LivroService.BuscarLivro(idLivro);

        String data = JOptionPane.showInputDialog("Insira a data de devolução prevista (YYYY-MM-DD): ");
        LocalDate dataDevolucaoPrevista = Utilidades.toLocalDate(data);

        if (userOpt.isPresent() && livroEscolhidoOpt.isPresent()) {
            if (EmprestimoRepo.CriarEmprestimo(userOpt.get(), livroEscolhidoOpt.get(), dataDevolucaoPrevista)) {
                LivroService.GravarLivros(); // atualiza o arquivo
            }
            ;
        }
    }

    public static void GravarEmprestimos() throws IOException {
        EmprestimoRepo.GravarEmprestimos();
    }

    public static void LerArquivo() throws IOException {
        List<EmprestimoDTO> lista_Dtos = EmprestimoRepo.LerArquivo();
        List<Emprestimo> emprestimosRecuperados = new ArrayList<>();

        for (EmprestimoDTO emprestimoDTO : lista_Dtos) {
            Optional<Usuario> userOpt = UserService.BuscarUsuario(emprestimoDTO.getIdUsuario());
            Optional<Livro> livroOpt = LivroService.BuscarLivro(emprestimoDTO.getIdLivro());
            if (userOpt.isPresent() && userOpt.isPresent()) {
                Optional<Emprestimo> emprestimoOpt = Emprestimo.tryRehydrate(
                        emprestimoDTO.getIdEmprestimo(),
                        userOpt.get(),
                        livroOpt.get(),
                        emprestimoDTO.getDataEmprestimo(),
                        emprestimoDTO.getDataDevolucaoPrevista(),
                        emprestimoDTO.getDataDevolucaoReal(),
                        emprestimoDTO.getStatus());

                if (emprestimoOpt.isPresent()) {
                    emprestimosRecuperados.add(emprestimoOpt.get());
                }
            }
        }
        EmprestimoRepo.CarregarEmprestimos(emprestimosRecuperados);
    }

    public static void ExibirEmprestimos() {
        EmprestimoRepo.ExibirEmprestimos();
    }

    public static boolean ExibirEmprestimosAtivos() {
        if (EmprestimoRepo.ExibirEmprestimosAtivos()) {
            return true;
        } else {
            return false;
        }
    }

    public static void BuscarEmprestimosPorUsuario() {
        UserService.ExibirUsuarios();
        UUID idUser = UUID.fromString(JOptionPane.showInputDialog("Insira o ID do usuario a ser buscado: "));
        Optional<Usuario> userOpt = UserService.BuscarUsuario(idUser);
        if (userOpt.isPresent()) {
            Optional<List<Emprestimo>> listaOpt = EmprestimoRepo.BuscarEmprestimosPorUsuario(idUser);
            if (listaOpt.isPresent()) {
                List<Emprestimo> lista_emprestimos = listaOpt.get();
                for (Emprestimo emprestimoUser : lista_emprestimos) {
                    emprestimoUser.ExibirDados();
                    System.out.println("======================================");
                }
            }

        }
    }

    public static void DevolverEmprestimo() throws IOException {
        if (ExibirEmprestimosAtivos()) {
            UUID idEmprestimo = UUID
                    .fromString(JOptionPane.showInputDialog("Insira o ID do emprestimo a ser devolvido: "));
            if (EmprestimoRepo.DevolverEmprestimo(idEmprestimo)) {
                UserService.GravarUsuarios();
                LivroService.GravarLivros();
            }
        }else{
            System.out.println("Nenhum emprestimo ativo encontrado!");
        }
        
    }

    public static void VerificarAtrasos() throws IOException {
        Optional<UUID> idUserBloqueado = EmprestimoRepo.VerificarAtrasos();
        if (idUserBloqueado.isPresent()) {
            UUID idUser = idUserBloqueado.get();
            UserService.BloquearUsuario(idUser);
            EmprestimoRepo.GravarEmprestimos();
        } else {
            System.out.println("Nenhum usuario bloqueado...");
        }
    }

    public static void ApagarEmprestimos(int tipo, UUID idObjeto) throws IOException {
        EmprestimoRepo.ApagarEmprestimos(tipo, idObjeto);
    }
}
