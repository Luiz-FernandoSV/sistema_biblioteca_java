import UI.*;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) throws Exception {

        int opc = 0;
        while (opc != 9) {
            opc = Integer.parseInt(JOptionPane.showInputDialog("Insira a opção: \n 1 - Ações de usuário \n 2 - Ações de livro \n 3 - Ações de empréstimos \n 4 - Menu Geral"));
            switch (opc) {
                case 1:
                    MenuUser.MenuUsuario();
                    break;
                case 2:
                    MenuLivro.Menulivro();
                    break;
                case 3:
                    MenuEmprestimos.MenuEmprestimo();
                    break;
                case 4:
                    MenuGeral.Menu();
                    break;
                case 9:
                    System.out.println("Fim do programa!");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;

            }
        }

    }
}

/*
    - como fazer pra quando apagar um user / livro / emprestimo apagar tambem no arquivo
    - talvez refatorar depois a parte das atualizações para tryUpdate
        - caso altere a quantidade total de livros, a quantidade disponivel continua a mesma - feito
        - adicionar um campo de qtdEmprestados no livro e fazer o calculo, se o resultado = 0, fica indisponivel - feito
    - terminar as funções do emprestimo
    - mexi nas funções de buscar usuario e livro, troquei pra receber o id como parametro e nao pedir de dentro da função
    - usar overloaded methods para alteração de N campos de um objeto

    - atualizar o numero de disponiveis e emprestados no livro
*/