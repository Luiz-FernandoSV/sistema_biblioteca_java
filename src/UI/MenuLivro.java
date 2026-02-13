package UI;

import javax.swing.JOptionPane;
import Service.LivroService;
import java.io.IOException;

public class MenuLivro {
    public static void Menulivro() throws IOException {
        int opc = 0;
        while (opc != 9) {
            opc = Integer.parseInt(JOptionPane.showInputDialog(
                    "Insira a opção desejada:"+
                    "\n 1 - Criar livro" +  
                    "\n 2 - Gravar livros" +  
                    "\n 3 - Ler arquivo "+ 
                    "\n 4 - Exibir livros" +
                    "\n 5 - Buscar por ID" +
                    "\n 6 - Apagar livro" +
                    "\n 7 - Alterar livro"+
                    "\n 9 - Finalizar"
                ));
            switch (opc) {
                case 1:
                    LivroService.CriarLivro();
                    break;
                case 2:
                    LivroService.GravarLivros();
                    break;
                case 3:
                    LivroService.LerArquivo();
                    break;
                case 4:
                    LivroService.ExibirLivros();;
                    break;
                case 5:
                    LivroService.BuscarPorID();
                    break;
                case 6:
                    LivroService.ApagarLivro();
                    break;
                case 7:
                    LivroService.AlterarLivro();
                    break;
                case 9:
                    System.out.println("Fim do menu de livros");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

    }
}
