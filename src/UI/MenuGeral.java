package UI;

import javax.swing.JOptionPane;

import Service.EmprestimoService;
import Service.LivroService;
import Service.UserService;

import java.io.IOException;

public class MenuGeral {
    public static void Menu() throws IOException {
        int opc = 0;
        while (opc != 9) {
            opc = Integer.parseInt(JOptionPane.showInputDialog(
                    "Insira a opção desejada:"+
                    "\n 1 - Ler arquivos(usuarios,livros,emprestimos)" +  
                    "\n 2 - Gravar dados(usuarios,livros,emprestimos)" +  
                    "\n 9 - Finalizar"
                ));
            switch (opc) {
                case 1:
                    UserService.LerArquivo();
                    LivroService.LerArquivo();
                    EmprestimoService.LerArquivo();
                    break;
                case 2:
                    UserService.GravarUsuarios();
                    LivroService.GravarLivros();
                    EmprestimoService.GravarEmprestimos();
                    break;
                case 9:
                    System.out.println("Fim do menu geral!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

    }
}
