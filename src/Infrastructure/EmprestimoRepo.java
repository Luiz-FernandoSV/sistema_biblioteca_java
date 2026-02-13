package Infrastructure;

import java.util.*;
import Domain.*;
import Utils.Enums.StatusEmprestimo;
import java.io.*;
import java.time.LocalDate;
import java.nio.file.*;

public class EmprestimoRepo {

    private static List<Emprestimo> lista_Emprestimos = new ArrayList<>();

    public static void CarregarEmprestimos(List<Emprestimo> emprestimos) {
        lista_Emprestimos = emprestimos;
    }

    public static boolean CriarEmprestimo(Usuario user, Livro livroEscolhido, LocalDate dataDevolucaoPrevista) {
        if (!lista_Emprestimos.isEmpty()) {
            Optional<List<Emprestimo>> emprestimosUserOpt = BuscarEmprestimosPorUsuario(user.getId());
            List<Emprestimo> emprestimosUsuario = new ArrayList<>();

            int qtdAtivos = 0;

            if (emprestimosUserOpt.isPresent()) {
                emprestimosUsuario = emprestimosUserOpt.get();
            }
            for (Emprestimo emprestimo : emprestimosUsuario) {
                if (emprestimo.estaAtivo()) {
                    qtdAtivos++;
                }
            }
            if (qtdAtivos >= 3) {
                System.out.println("Limite de emprestimos atingido!");
                return false;
            }
        }

        Optional<Emprestimo> emprestimoOpt = Emprestimo.tryCreate(user, livroEscolhido, dataDevolucaoPrevista);
        if (emprestimoOpt.isPresent()) {
            Emprestimo novoEmprestimo = emprestimoOpt.get();
            livroEscolhido.incrementarEmprestados(); // aumenta o valor do campo de livros emprestados
            lista_Emprestimos.add(novoEmprestimo);
            System.out.println("Empréstimo criado!");
            novoEmprestimo.ExibirDados();
            return true;
        } else {
            return false;
        }
    }

    public static void GravarEmprestimos() throws IOException {
        if (!lista_Emprestimos.isEmpty()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Emprestimos.txt"));

            for (Emprestimo emprestimo : lista_Emprestimos) {
                writer.write(emprestimo.getIdString());
                writer.newLine();

                writer.write(emprestimo.Usuario.getStringId());
                writer.newLine();

                writer.write(emprestimo.Livro.getStringId());
                writer.newLine();

                writer.write(emprestimo.DataEmprestimo.toString());
                writer.newLine();

                writer.write(emprestimo.DataDevolucaoPrevista.toString());
                writer.newLine();

                if (emprestimo.Status == StatusEmprestimo.devolvido) {
                    writer.write(emprestimo.getStringDevolucaoReal());
                    writer.newLine();
                } else {
                    writer.write("nao-devolvido");
                    writer.newLine();
                }

                writer.write(Integer.toString(emprestimo.getCodigoStatus()));
                writer.newLine();
            }
            writer.close();
            System.out.println("Arquivo Emprestimos.txt gerado / alterado!");
        } else {
            System.out.println("Nenhum empréstimo cadastrado!");
        }
    }

    public static List<EmprestimoDTO> LerArquivo() throws IOException {
        List<EmprestimoDTO> lista_Dtos = new ArrayList<>();
        Path arquivo = Paths.get("Emprestimos.txt");

        if (Files.exists(arquivo)) {
            BufferedReader reader = new BufferedReader(new FileReader("Emprestimos.txt"));

            String linha;
            while ((linha = reader.readLine()) != null) {
                UUID id = UUID.fromString(linha);

                UUID idUser = UUID.fromString(reader.readLine());

                UUID idLivro = UUID.fromString(reader.readLine());

                LocalDate dataEmprestimo = LocalDate.parse(reader.readLine());
                LocalDate dataDevolucaoPrevista = LocalDate.parse(reader.readLine());

                String dataDevolucaoRealString = reader.readLine();
                LocalDate dataDevolucaoReal = null;
                if (dataDevolucaoRealString.equals("nao-devolvido")) {
                    dataDevolucaoReal = null;
                } else {
                    dataDevolucaoReal = LocalDate.parse(dataDevolucaoRealString);
                }
                int status = Integer.parseInt(reader.readLine());
                StatusEmprestimo statusFormatado = StatusEmprestimo.values()[status];

                lista_Dtos.add(new EmprestimoDTO(
                        id,
                        idUser,
                        idLivro,
                        dataEmprestimo,
                        dataDevolucaoPrevista,
                        dataDevolucaoReal,
                        statusFormatado));
            }
            reader.close();
            System.out.println("Emprestimos lidos!");
        } else {
            System.out.println("Arquivo Emprestimos.txt nao existe!");
        }
        return lista_Dtos;

    }

    public static void ExibirTodos() {
        if (!lista_Emprestimos.isEmpty()) {
            for (Emprestimo emprestimo : lista_Emprestimos) {
                emprestimo.ExibirDados();
                System.out.println("==============================================");
            }
        } else {
            System.out.println("Nenhum empréstimos cadastrados!");
        }
    }

    public static Optional<Emprestimo> BuscarPorId(UUID idEmprestimo) {
        if (!lista_Emprestimos.isEmpty()) {
            for (Emprestimo emprestimo : lista_Emprestimos) {
                if (emprestimo.getId().equals(idEmprestimo)) {
                    return Optional.of(emprestimo);
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<List<Emprestimo>> BuscarEmprestimosPorUsuario(UUID idUser) {
        List<Emprestimo> lista_emprestimos_user = new ArrayList<>();

        if (!lista_Emprestimos.isEmpty()) {
            for (Emprestimo emprestimo : lista_Emprestimos) {
                if (emprestimo.Usuario.getId().equals(idUser)) {
                    lista_emprestimos_user.add(emprestimo);
                }
            }
        }
        if (lista_emprestimos_user.size() > 0) {
            return Optional.of(lista_emprestimos_user);
        } else {
            System.out.println("Este usuário nao possui empréstimos cadastrados!");
            return Optional.empty();
        }
    }

    public static void ExibirEmprestimos() {
        if (!lista_Emprestimos.isEmpty()) {
            for (Emprestimo emprestimo : lista_Emprestimos) {
                emprestimo.ExibirDados();
                System.out.println("============================================");
            }
        } else {
            System.out.println("Nenhum emprestimo cadastrado / carregado no sistema!");
        }
    }

    public static boolean ExibirEmprestimosAtivos() {
        int qtdAtivos = 0;
        if (!lista_Emprestimos.isEmpty()) {
            for (Emprestimo emprestimo : lista_Emprestimos) {
                if (emprestimo.estaAtivo()) {
                    qtdAtivos++;
                    emprestimo.ExibirDados();
                    System.out.println("============================================");
                }
            }
            if(qtdAtivos > 0){
                return true;
            }else{
                return false;
            }
        } else {
            System.out.println("Nenhum emprestimo cadastrado / carregado no sistema!");
            return false;
        }
    }

    public static boolean DevolverEmprestimo(UUID idEmprestimo) throws IOException {
        Optional<Emprestimo> emprestimoOpt = BuscarPorId(idEmprestimo);
        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimo = emprestimoOpt.get();
            if (!emprestimo.estaDevolvido()) {
                emprestimo.Devolver();
                emprestimo.ExibirDados();
                GravarEmprestimos();
                return true;
            } else {
                System.out.println("Emprestimo ja esta devolvido!");
                return false;
            }
        } else {
            return false;
        }
    }

    public static Optional<UUID> VerificarAtrasos() throws IOException {
        if (!lista_Emprestimos.isEmpty()) {
            for (Emprestimo emprestimo : lista_Emprestimos) {
                if (emprestimo.estaAtivo() && emprestimo.DataDevolucaoPrevista.isBefore(LocalDate.now())) {
                    System.out.println(
                            "O Emprestimo de ID: " + emprestimo.getId() + " esta atrasado!");
                    emprestimo.MarcarAtraso();
                    return Optional.of(emprestimo.Usuario.getId());
                }
            }
            System.out.println("Nenhum emprestimo atrasado encontrado!");
            return Optional.empty();
        } else {
            System.out.println("Nenhum emprestimo cadastrado!");
            return Optional.empty();
        }

    }

    public static void ApagarEmprestimos(int tipo, UUID idObjeto) throws IOException{
        switch (tipo) {
            case 1:
                lista_Emprestimos.removeIf(e -> e.Usuario.getId().equals(idObjeto));
                GravarEmprestimos();
                break;
            case 2:
                lista_Emprestimos.removeIf(e -> e.Livro.getId().equals(idObjeto));
                GravarEmprestimos();
                break;
            default:
                break;

        }
    }
}
