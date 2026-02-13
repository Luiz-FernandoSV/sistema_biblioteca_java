package Infrastructure;

import Domain.Livro;
import Utils.Enums.StatusLivro;

import java.util.*;

import javax.swing.JOptionPane;

import java.nio.file.*;
import java.io.*;

public class LivroRepo {
    private static List<Livro> lista_livros = new ArrayList<>();

    public static void CriarLivro(String titulo, Integer qtdTotal) {
        Optional<Livro> livroOpt = Livro.tryCreate(titulo, qtdTotal);
        if (livroOpt.isPresent()) {
            Livro l = livroOpt.get();
            lista_livros.add(l);
            System.out.println("Livro Criado!");
        } else {
            System.out.println("Livro nao criado!");
        }
    }

    public static void GravarLivros() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Livros.txt"));
        for (Livro livro : lista_livros) {
            writer.write(livro.getStringId());
            writer.newLine();

            writer.write(livro.Titulo);
            writer.newLine();

            writer.write(Integer.toString(livro.qtdTotal));
            writer.newLine();

            writer.write(Integer.toString(livro.qtdDisponivel));
            writer.newLine();

            writer.write(Integer.toString(livro.qtdEmprestados));
            writer.newLine();

            writer.write(Integer.toString(livro.getCodigoStatus()));
            writer.newLine();
        }
        writer.close();
        System.out.println("Arquivo Livros.txt gerado / atualizado!");
    }

    public static void LerArquivo() throws IOException {
        Path arquivo = Paths.get("Livros.txt");
        if (Files.exists(arquivo)) {
            BufferedReader reader = new BufferedReader(new FileReader("Livros.txt"));

            String linha;
            while ((linha = reader.readLine()) != null) {
                UUID id = UUID.fromString(linha);
                String titulo = reader.readLine();
                Integer qtdTotal = Integer.parseInt(reader.readLine());
                Integer qtdDisponivel = Integer.parseInt(reader.readLine());
                Integer qtdEmprestados = Integer.parseInt(reader.readLine());
                Integer codigoStatus = Integer.parseInt(reader.readLine());
                StatusLivro status = StatusLivro.values()[codigoStatus];

                Optional<Livro> livroOpt = Livro.tryRehydrate(id, titulo, qtdDisponivel, qtdTotal,qtdEmprestados, status);
                if (livroOpt.isPresent()) {
                    Livro livro = livroOpt.get();
                    lista_livros.add(livro);
                }else{
                    System.out.println("falha na rehidratacao");
                }
            }
            reader.close();
            System.out.println("Livros lidos!");
        } else {
            System.out.println("Arquivo livros.txt nao existe!");
        }
    }

    public static void ExibirLivros() {
        if (!lista_livros.isEmpty()) {
            for (Livro livro : lista_livros) {
                livro.exibirInformacoes();
                System.out.println("===================================");
            }
        }else{
            System.out.println("Nenhum livro cadastrado / carregado no sistema!");
        }
    }

    public static Optional<Livro> BuscarPorID(UUID livroID) {
        for (Livro livro : lista_livros) {
            if (livro.getId().equals(livroID)) {
                return Optional.of(livro);
            }
        }
        return Optional.empty();
    }

    public static boolean ApagarLivro(UUID livroID) throws IOException {
        int opc = 0;
        boolean confirmacao = false;
        System.out.println("Tem certeza que quer apagar o livro com id: " + livroID + "?");
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
            Optional<Livro> livroOpt = BuscarPorID(livroID);
            if (livroOpt.isPresent()) {
                Livro l = livroOpt.get();
                lista_livros.remove(l);
                System.out.println("Livro removido!");
                GravarLivros();
                return true;
            } else {
                System.out.println("Livro nao existe!");
                return false;
            }
        }else{
            return false;
        }
    }

    public static void AlterarLivro(UUID livroID) throws IOException {
        int opc = 0;
        boolean confirmacao = false;
        System.out.println("Tem certeza que quer alterar o livro com id: " + livroID + "?");
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
            Optional<Livro> livroOpt = BuscarPorID(livroID);
            int opcCampos = 0;
            if (livroOpt.isPresent()) {
                Livro livro = livroOpt.get();

                boolean opCompleta = false;
                do {
                    opcCampos = Integer.parseInt(JOptionPane.showInputDialog(
                            "Escolha os campos a serem alterados: " +
                                    "\n 1 - Titulo " +
                                    "\n 2 - Quantidade Total "));

                    switch (opcCampos) {
                        case 1:
                            livro.Titulo = JOptionPane.showInputDialog("Insira o novo titulo: ");
                            System.out.println("Titulo alterado!");
                            opCompleta = true;
                            GravarLivros();
                            break;
                        case 2:
                            int novaQtd = Integer.parseInt(
                                    JOptionPane.showInputDialog(("Insira a nova quantidade de exemplares: ")));
                            if (livro.alterarQuantidade(novaQtd)) {
                                System.out.println("Quantidade alterada!");
                                GravarLivros();
                            }
                            opCompleta = true;
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
                } while (opCompleta != true);
            }
        }
    }

}
