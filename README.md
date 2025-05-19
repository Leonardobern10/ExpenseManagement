# Gestão de Movimentações Financeiras

Uma API para gerenciar movimentações financeiras, incluindo controle de despesas, dívidas e limites por categoria, com autenticação baseada em JWT.

## 📑 Funcionalidades

- Gerenciamento de movimentações financeiras:
  - Consultas por mês, ano e categoria.
  - Atualização do status de pagamento.
- Controle de limites por categoria.
  - Alerta por e-mail caso a categoria tenha superado o limite definido.
- Cadastro e autenticação de usuários:
  - Baseada em tokens JWT.
  - Roles de usuário: ADMIN e USER.
- Integração com MongoDB para armazenamento de dados.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
  - Spring Data
  - Spring Security
- **MongoDB**
- **JWT (JSON Web Token)** para autenticação
- **Maven** para gerenciamento de dependências

## 🛠️ Instalação e Configuração

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/Leonardobern10/ExpenseManagement.git
   cd seu-repositorio
   ```

2. **Configure o banco de dados:**
   - Certifique-se de que o MongoDB está em execução na sua máquina ou configure o `application.properties` com as credenciais do seu ambiente:
     ```properties
     spring.data.mongodb.uri=mongodb://<seu-usuario>:<sua-senha>@<host>:<porta>/<database>
     ```

3. **Execute o projeto:**
   ```bash
   mvn spring-boot:run
   ```

4. **Acesse a API:**
   - A aplicação estará disponível em: `http://localhost:8080`

## 📚 Endpoints

### **Autenticação**
- **POST** `/auth/login`  
  Retorna um token JWT para acesso à API.

### **Movimentações**
- **GET** `/movimentations/year/{year}`  
  Retorna movimentações do ano especificado.
- **POST** `/movimentations/create`  
  Cria uma nova movimentação financeira.
- **PUT** `/movimentations/{id}/status`  
  Atualiza o status de pagamento de uma movimentação.

### **Limites**
- **POST** `/categories/limit`  
  Define um limite para uma categoria.

### **Usuários**
- **POST** `/users/create`  
  Cria um novo usuário.

## 🧪 Testes

Execute os testes unitários com o Maven:
```bash
mvn test
```

## 📁 Estrutura do Projeto

```
src
├── main
│   ├── java
│   │   └── com.example.ExampleManagement
│   │       ├── config   # Camada de controladores
│   │       ├── controller   # Camada de controladores
│   │       ├── dto   # Objetos para transferência de dados
│   │       ├── model        # Classes de modelo
│   │       ├── repository   # Interfaces de repositórios
│   │       ├── util      # Ferramentas para JWT
│   │       ├── validations      # Validações de negócios
│   └── resources
│       ├── application.properties  # Configurações da aplicação
```

## 🤝 Contribuição

1. Faça um fork do projeto.
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
3. Faça commit das suas alterações (`git commit -m 'Adiciona nova feature'`).
4. Envie para o repositório remoto (`git push origin feature/nova-feature`).
5. Abra um pull request.

## 📜 Licença

Este projeto está licenciado sob a **MIT License**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 📞 Contato

- **Nome:** Leonardo Bernardo
- **E-mail:** leonardo.bernardo2658@gmail.com
- **GitHub:** [Leonardobern10](https://github.com/Leonardobern10)
- **LinkedIn:** [Leonardo Bernardo](https://www.linkedin.com/in/leonardo-bern/)
