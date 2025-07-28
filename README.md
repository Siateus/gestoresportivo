
# 🏆 Sistema de Gestão de Torneios e Equipes Esportivas

Este projeto é uma **API RESTful** desenvolvida com **Spring Boot** para gerenciar dados de torneios, equipes e atletas. O banco de dados **MySQL** é orquestrado via **Docker Compose**.

---

## 🚀 Como Rodar o Projeto

### ✅ Pré-requisitos
- JDK 17+
- Docker Desktop (ou Docker Engine + Docker Compose)

### 🔧 Clonar o repositório

```bash
git clone https://github.com/Siateus/gestoresportivo
cd gestoresportivo
```

### 🐬 Iniciar o Banco de Dados MySQL com Docker Compose

Este comando irá baixar a imagem do MySQL, criar o contêiner e executar o script `mysql/init.sql` (que cria o banco de dados, tabelas, views, functions e triggers).

```bash

docker-compose down -v  
docker-compose up -d
```

⏱️ Aguarde 15–20 segundos após o `up -d` para o MySQL inicializar. Verifique o status com:

```bash
docker-compose logs mysql
```

### ☕ Iniciar a Aplicação Spring Boot

Navegue até a raiz do projeto (onde está o `pom.xml`) e execute:

```bash
./mvnw spring-boot:run
```

---

## 🗺️ Rotas da API (Endpoints)

📍 Base URL: `http://localhost:8080`  
📄 Documentação interativa (Swagger UI): [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### 🏟️ Torneios `/torneios`
- `GET /torneios`
- `GET /torneios/{id}`
- `POST /torneios`  
  Body: `TorneioDTO (nome, qtdEquipe, codModalidade)`
- `PUT /torneios/{id}`  
  Body: `TorneioDTO`
- `DELETE /torneios/{id}`

### 🏅 Modalidades `/modalidades`
- `GET /modalidades`
- `GET /modalidades/{id}`
- `POST /modalidades`  
  Body: `ModalidadeDTO (nome, qtdAtletaEquipe)`
- `PUT /modalidades/{id}`  
  Body: `ModalidadeDTO`
- `DELETE /modalidades/{id}`

### 🎮 Jogos `/jogos`
- `GET /jogos`
- `GET /jogos/{id}`
- `POST /jogos`  
  Body: `JogoDTO (data, hora, pTime1, pTime2, placar, codEquipe1, codEquipe2, codTorneio)`
- `PUT /jogos/{id}`  
  Body: `JogoDTO`
- `DELETE /jogos/{id}`

### 🧢 Equipes `/equipes`
- `GET /equipes`
- `GET /equipes/{id}`
- `GET /equipes/nome?nome={nome}`
- `POST /equipes`  
  Body: `EquipeDTO (nome, sigla, codModalidade)`
- `PUT /equipes/{id}`  
  Body: `EquipeDTO`
- `DELETE /equipes/{id}`

### 🧍 Atletas `/atletas`
- `GET /atletas`
- `GET /atletas/{id}`
- `GET /atletas/cpf/{cpf}`
- `POST /atletas`  
  Body: `AtletaDTO (nome, cpf, sexo, cidade, dataNascimento)`
- `PUT /atletas/{id}`  
  Body: `AtletaDTO`
- `DELETE /atletas/{id}`

### 🔗 Associações Equipe-Torneio `/equipes-torneios`
- `GET /equipes-torneios`
- `GET /equipes-torneios/{codEquipe}/{codTorneio}`
- `POST /equipes-torneios`  
  Body: `EquipeTorneioDTO (codEquipe, codTorneio)`
- `DELETE /equipes-torneios/{codEquipe}/{codTorneio}`

### 👥 Associações Atleta-Equipe `/atletas-equipes`
- `GET /atletas-equipes`
- `GET /atletas-equipes/{codAtleta}/{codEquipe}`
- `POST /atletas-equipes`  
  Body: `AtletaEquipeDTO (codAtleta, codEquipe)`
- `DELETE /atletas-equipes/{codAtleta}/{codEquipe}`

### 📊 Relatórios `/relatorios`
- `GET /relatorios/atletas-por-equipe-modalidade`

---

## ⚙️ Detalhes da Implementação

| Recurso           | Tecnologia/Detalhe                               |
|-------------------|--------------------------------------------------|
| Banco de Dados    | MySQL 8.0                                        |
| Framework         | Spring Boot 3.x                                  |
| ORM               | Spring Data JPA / Hibernate                      |
| Validação         | Jakarta Bean Validation                          |
| Documentação API  | SpringDoc OpenAPI                                |
| Arquitetura       | Camadas (Controller, Service, Repository + DTOs) |
| Recursos MySQL    | VIEWs, FUNCTIONs e TRIGGERs                      |

---

