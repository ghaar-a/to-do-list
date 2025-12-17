# To-Do List API - Spring Boot

API RESTful completa para gerenciamento de tarefas, desenvolvida com **Spring Boot 3** e **Java 17**.

Projeto de portfólio criado para demonstrar habilidades em desenvolvimento back-end Java – perfeito para vagas júnior.

## Tecnologias utilizadas

- **Spring Boot 3** (Web, Data JPA, Security, Mail)
- **PostgreSQL** (banco de dados relacional real)
- **Hibernate/JPA** (mapeamento objeto-relacional)
- **Lombok** (redução de código boilerplate)
- **Bean Validation** (validações de entrada)
- **Maven** (gerenciamento de dependências)

## Funcionalidades

- CRUD completo de tarefas
- Autenticação básica com Spring Security
- Validação de campos (título obrigatório, limites de tamanho)
- Categorias para organização
- Notificação por email ao criar tarefa (JavaMailSender)

## Endpoints da API

Base URL: `http://localhost:8080/api/tasks`

| Método | Endpoint          | Descrição                  | Autenticação |
|--------|-------------------|----------------------------|--------------|
| GET    | /api/tasks        | Lista todas as tarefas     | Sim         |
| GET    | /api/tasks/{id}   | Busca tarefa por ID        | Sim         |
| POST   | /api/tasks        | Cria nova tarefa           | Sim         |
| PUT    | /api/tasks/{id}   | Atualiza tarefa            | Sim         |
| DELETE | /api/tasks/{id}   | Deleta tarefa              | Sim         |

### Exemplo de criação (POST)

```json
{
  "title": "Estudar Spring Boot",
  "description": "Finalizar projeto de portfólio",
  "completed": false,
  "category": "Estudos"
}
