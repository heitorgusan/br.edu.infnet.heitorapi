# Sistema de Ocorrências Criminais

API REST para gerenciamento de ocorrências criminais.

## Como Executar

```bash
mvn spring-boot:run
```

## Acesso

- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console (user: sa)

## Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/ocorrencias` | Criar ocorrência |
| `GET` | `/api/ocorrencias` | Listar ocorrências |
| `GET` | `/api/ocorrencias/{id}` | Buscar por ID |
| `PUT` | `/api/ocorrencias/{id}` | Atualizar ocorrência |
| `DELETE` | `/api/ocorrencias/{id}` | Excluir ocorrência |
| `GET` | `/api/ocorrencias/cidade/{cidade}` | Filtrar por cidade |
| `GET` | `/api/enderecos/cep/{cep}` | Consultar CEP |

## Tecnologias

- Java 21
- Spring Boot 3.4.5
- H2 Database
- Spring Data JPA
- ViaCEP
- Swagger/OpenAPI

## Configuração Java 21

Configurar `JAVA_HOME`:
```bash
export JAVA_HOME=/path/to/java21
```