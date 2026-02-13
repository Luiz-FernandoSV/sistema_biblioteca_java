package UI;

import javax.swing.JOptionPane;
import Service.UserService;
import java.io.IOException;

public class MenuUser {
    public static void MenuUsuario() throws IOException {


        int opc = 0;
        while (opc != 9) {
            opc = Integer.parseInt(JOptionPane.showInputDialog(
                    "Insira a opção desejada:"+
                    "\n 1 - Criar usuários" +  
                    "\n 2 - Gravar usuários" +  
                    "\n 3 - Ler arquivo "+ 
                    "\n 4 - Exibir usuários" +
                    "\n 5 - Buscar por ID" +
                    "\n 6 - Apagar usuário" +
                    "\n 7 - Alterar usuário"+
                    "\n 9 - Finalizar"
                ));
            switch (opc) {
                case 1:
                    UserService.CriarUsuario();
                    break;
                case 2:
                    UserService.GravarUsuarios();
                    break;
                case 3:
                    UserService.LerArquivo();
                    break;
                case 4:
                    UserService.ExibirUsuarios();
                    break;
                case 5:
                    UserService.BuscarPorID();
                    break;
                case 6:
                    UserService.ApagarUsuario();
                    break;
                case 7:
                    UserService.AlterarUsuario();
                    break;
                case 9:
                    System.out.println("Fim do menu de usuários!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

    }
}
