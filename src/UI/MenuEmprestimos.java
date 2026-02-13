package UI;

import java.io.IOException;

import javax.swing.JOptionPane;

import Service.EmprestimoService;

public class MenuEmprestimos {
    public static void MenuEmprestimo() throws IOException{
        int opc = 0;
        while (opc != 9) {
            opc = Integer.parseInt(JOptionPane.showInputDialog(
                    "Insira a opção desejada:"+
                    "\n 1 - Criar empréstimo" +
                    "\n 2 - Gravar empréstimos" +
                    "\n 3 - Ler arquivo" +
                    "\n 4 - Exibir empréstimos cadastrados" +
                    "\n 5 - Buscar empréstimo de usuário" +
                    "\n 6 - Verificar atrasos" +
                    "\n 7 - Devolver empréstimo" +
                    "\n 9 - Finalizar"
                ));
            switch (opc) {
                case 1:
                    EmprestimoService.CriarEmprestimo();
                    break;
                case 2:
                    EmprestimoService.GravarEmprestimos();
                    break;
                case 3:
                    EmprestimoService.LerArquivo();
                    break;
                case 4:
                    EmprestimoService.ExibirEmprestimos();
                    break;
                case 5:
                    EmprestimoService.BuscarEmprestimosPorUsuario();
                    break;
                case 6:
                    EmprestimoService.VerificarAtrasos();
                    break;
                case 7:
                    EmprestimoService.DevolverEmprestimo();
                    break;
                case 9:
                    System.out.println("Fim do menu de emprestimos!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }
}
