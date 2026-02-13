package Service;

import java.io.IOException;
import javax.swing.JOptionPane;
import Infrastructure.LivroRepo;
import java.util.*;
import Domain.Livro;

public class LivroService {
    public static void CriarLivro() {
        String titulo = JOptionPane.showInputDialog("Insira o titulo do livro: ");
        String qtdTotalStr = JOptionPane.showInputDialog("Insira a quantidade de livros: ");
        Integer qtdTotal = 0;
        if(!qtdTotalStr.isEmpty()){
            qtdTotal = Integer.parseInt(qtdTotalStr);
        }else{
            System.out.println("A Quantidade nao pode ser nula!");
        }


        LivroRepo.CriarLivro(titulo, qtdTotal);
    }

    public static void GravarLivros() throws IOException {
        LivroRepo.GravarLivros();
    }

    public static void LerArquivo() throws IOException {
        LivroRepo.LerArquivo();
    }

    public static void ExibirLivros() {
        LivroRepo.ExibirLivros();
    }

    public static void BuscarPorID() {
        ExibirLivros();
        UUID idLivro = UUID.fromString(JOptionPane.showInputDialog("Insira o ID a ser buscado: "));
        Optional<Livro> livroOpt = LivroRepo.BuscarPorID(idLivro);
        if (livroOpt.isPresent()) {
            Livro l = livroOpt.get();
            l.exibirInformacoes();
        } else {
            System.out.println("Livro nao existe!");
        }
    }

    public static Optional<Livro> BuscarLivro(UUID idLivro) {
        Optional<Livro> livroOpt = LivroRepo.BuscarPorID(idLivro);
        if (livroOpt.isPresent()) {
            Livro l = livroOpt.get();
            return Optional.of(l);
        } else {
            System.out.println("Livro nao existe!");
            return Optional.empty();
        }
    }

    public static void ApagarLivro() throws IOException{
        ExibirLivros();
        UUID idlivro = UUID.fromString(JOptionPane.showInputDialog("Insira o ID a ser apagado: "));
        if(LivroRepo.ApagarLivro(idlivro)){
            EmprestimoService.ApagarEmprestimos(2, idlivro);
            LivroRepo.GravarLivros();
        }
    }

    public static void AlterarLivro() throws IOException {
        ExibirLivros();
        UUID idlivro = UUID.fromString(JOptionPane.showInputDialog("Insira o ID a ser alterado: "));
        LivroRepo.AlterarLivro(idlivro);
    }
}
