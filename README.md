# Sistema de Gestão Hoteleira – Backend

Backend da aplicação de gestão de hotel responsável pelo controle de:

- Hóspedes
- Reservas
- Check-in
- Check-out
- Cálculo automático de diárias e atrasos

Este projeto foi desenvolvido utilizando **Spring Boot**, aplicando boas práticas de organização em camadas (**Controller**, **Service**, **Repository**).

---

# Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- Banco de Dados: *(PostgreSQL)*
- Flyway (migração de banco de dados)
- Lombok

---

# Como Executar o Projeto

## 1. Clonar o repositório

git clone https: https://github.com/Brunoalura29/Hotel_api

cd seu-backend 

---

## 2. Configurar o Banco de Dados

1. Certifique-se de ter o PostgreSQL instalado.

### Instalação do PostgreSQL
Linux (Debian/Ubuntu)
```
sudo apt update
sudo apt install postgresql postgresql-contrib
```
Linux (Fedora/CentOS/RHEL)
```
sudo dnf install postgresql-server postgresql-contrib
sudo postgresql-setup --initdb
sudo systemctl enable --now postgresql
```
macOS (via Homebrew)
```
brew update
brew install postgresql
brew services start postgresql
```
Windows

No Windows, não existe comando direto pelo terminal. O recomendado é:

Baixar o instalador oficial: https://www.postgresql.org/download/windows/

Seguir o assistente de instalação (configurando usuário e senha do Postgres).
<br>
<br>
<br>
   
   
2. Crie um banco de dados vazio para a aplicação, por exemplo:

```
CREATE DATABASE hotel;
```
<br>
<br>
   
3. Configure as variáveis de ambiente para o seu usuário, senha e URL do banco:

```
# Linux / Mac
export DB_URL=jdbc:postgresql://localhost:5432/hotel
export DB_USER=seu_usuario
export DB_PASSWORD=sua_senha

# Windows PowerShell
$env:DB_URL="jdbc:postgresql://localhost:5432/hotel"
$env:DB_USER="seu_usuario"
$env:DB_PASSWORD="sua_senha"

```

4. O arquivo src/main/resources/application.properties já está preparado para usar essas variáveis:

```
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
spring.jpa.properties.hibernate.jdbc.time_zone=UTC
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

```

### ⚠️ Importante: O Flyway vai criar todas as tabelas automaticamente na primeira execução, portanto não é necessário criar nada manualmente além do banco vazio.

---

### 3. Executar a aplicação

Via terminal:

- mvn spring-boot:run

Ou execute a classe principal:

- HotelApplication.java

A aplicação estará disponível em:

🔹 http://localhost:8080

---

## Principais Endpoints da API

### Hóspedes
| Método | Endpoint        | Descrição                |
|--------|----------------|--------------------------|
| GET    | /api/hospedes   | Lista todos os hóspedes  |
| POST   | /api/hospedes   | Cadastra um novo hóspede |

### Reservas
| Método | Endpoint                     | Descrição                  |
|--------|------------------------------|----------------------------|
| POST   | /api/reservas                | Criar nova reserva         |
| GET    | /api/reservas/status/{status} | Buscar reservas por status |

### Operações
| Método | Endpoint                  | Descrição       |
|--------|---------------------------|----------------|
| PUT    | /api/ops/checkin/{id}     | Realizar check-in |
| PUT    | /api/ops/checkout/{id}    | Realizar checkout |

---

### Exemplo de Requisição – Criar Reserva

```json
{
  "hospedeId": 1,
  "dataEntradaPrevista": "2026-03-01T14:00:00",
  "dataSaidaPrevista": "2026-03-05T12:00:00",
  "usaVaga": true
}
```
---

## Regras de Negócio Implementadas

- ✅ Não permite check-in se a reserva não estiver no status correto
- ✅ Não permite checkout duplicado
- ✅ Calcula automaticamente o valor total da estadia
- ✅ Aplica valor adicional para atraso no checkout
- ✅ Considera valor adicional para uso de vaga de garagem
- ✅ Atualiza status da reserva automaticamente (RESERVADO → CHECKED_IN → CHECKED_OUT)

---

Arquitetura baseada em separação de responsabilidades, seguindo padrão em camadas.

---

## Integração com Frontend

Este backend foi desenvolvido para integração com aplicação Angular disponível em:

🔹https://github.com/Brunoalura29/hotel-api-frontend

---

## Possíveis Melhorias Futuras

- Autenticação com Spring Security
- Documentação automática com Swagger/OpenAPI
- Dockerização da aplicação
- Deploy em ambiente cloud

---

## Autor:

Bruno Henrique Dias

GitHub: https://github.com/Brunoalura29

