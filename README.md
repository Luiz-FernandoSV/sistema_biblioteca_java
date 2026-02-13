# Sistema de Biblioteca
Sistema desenvolvido em Java para gerenciamento de usuários, livros e empréstimos, utilizando persistência de dados em arquivos `.txt`.

## Funcionalidades

### 📚 Gerenciamento de Usuários e Livros
- Cadastro de usuários e livros
- Edição de registros
- Exclusão de registros
- Listagem e exibição de informações
- Controle de status (ativo, bloqueado, disponível, emprestado)

### 🔁 Gerenciamento de Empréstimos
- Criação de empréstimos
- Registro de empréstimos em arquivo
- Leitura de dados a partir de arquivos `.txt`
- Exibição do histórico de empréstimos
- Devolução de livros
- Verificação de atrasos nos empréstimos

### 💾 Persistência de Dados
- Armazenamento de dados em arquivos de texto (`.txt`)

## Estrutura de Pastas

```text
sistema_biblioteca/
├── Livros.txt 
├── Usuarios.txt
├── Emprestimos.txt
├── src/
│   ├── Domain/
│   │   ├── Livro.java
│   │   ├── Usuario.java
│   │   ├── Emprestimo.java
│   │   └── EmprestimoDTO.java
│   ├── Infrastructure/
│   │   ├── LivroRepo.java
│   │   ├── UserRepo.java
│   │   └── EmprestimoRepo.java
│   ├── Service/
│   │   ├── LivroService.java
│   │   ├── UserService.java
│   │   └── EmprestimoService.java
│   ├── UI/
│   │   ├── MenuGeral.java
│   │   ├── MenuLivro.java
│   │   ├── MenuUser.java
│   │   └── MenuEmprestimos.java
│   ├── Utils/
│   │   ├── Enums/
│   │   │   ├── StatusLivro.java
│   │   │   ├── StatusUser.java
│   │   │   └── StatusEmprestimo.java
│   │   └── Utilidades.java
│   └── Main.java
