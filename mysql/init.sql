-- DDL (Data Definition Language) - Criação do Schema

-- 1. Criação do Banco de Dados
-- (Execute este comando primeiro, ou garanta que o 'gestoresportivo' já exista)
CREATE DATABASE IF NOT EXISTS gestoresportivo;

-- Seleciona o banco de dados para os comandos seguintes
USE gestoresportivo;



-- 2. Criação das Tabelas

-- Tabela Atleta
CREATE TABLE Atleta (
                        cod INT NOT NULL AUTO_INCREMENT, -- PK, NOT NULL, AUTO_INCREMENT
                        cpf VARCHAR(14) NOT NULL UNIQUE, -- CPF deve ser único
                        nome VARCHAR(30) NOT NULL,
                        sexo CHAR(1) NOT NULL CHECK (sexo IN ('f', 'm')), -- Restrição para 'f' ou 'm'
                        cidade VARCHAR(30) NOT NULL,
                        dataNasc DATE NOT NULL,
                        PRIMARY KEY (cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela Modalidade
CREATE TABLE Modalidade (
                            cod INT NOT NULL AUTO_INCREMENT, -- PK, NOT NULL, AUTO_INCREMENT
                            nome VARCHAR(30) NOT NULL,
                            qtdAtletaEquipe INT NOT NULL CHECK (qtdAtletaEquipe > 0), -- Restrição qtdAtletaEquipe > 0
                            PRIMARY KEY (cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela Equipe
CREATE TABLE Equipe (
                        cod INT NOT NULL AUTO_INCREMENT, -- PK, NOT NULL, AUTO_INCREMENT
                        nome VARCHAR(30) NOT NULL,
                        sigla VARCHAR(5) NOT NULL,
                        codModalidade INT NOT NULL, -- FK
                        PRIMARY KEY (cod),
                        FOREIGN KEY (codModalidade) REFERENCES Modalidade(cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela Torneio
CREATE TABLE Torneio (
                         cod INT NOT NULL AUTO_INCREMENT, -- PK, NOT NULL, AUTO_INCREMENT
                         nome VARCHAR(30) NOT NULL,
                         qtdEquipe INT NOT NULL CHECK (qtdEquipe > 0), -- Restrição qtdEquipe > 0
                         codModalidade INT NOT NULL, -- FK
                         PRIMARY KEY (cod),
                         FOREIGN KEY (codModalidade) REFERENCES Modalidade(cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela AtletaEquipe (Tabela de Junção Atleta-Equipe)
CREATE TABLE AtletaEquipe (
                              codAtleta INT NOT NULL, -- PK, FK
                              codEquipe INT NOT NULL, -- PK, FK
                              PRIMARY KEY (codAtleta, codEquipe), -- Chave Primária Composta
                              FOREIGN KEY (codAtleta) REFERENCES Atleta(cod),
                              FOREIGN KEY (codEquipe) REFERENCES Equipe(cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela EquipeTorneio (Tabela de Junção Equipe-Torneio)
CREATE TABLE EquipeTorneio (
                               codEquipe INT NOT NULL, -- PK, FK
                               codTorneio INT NOT NULL, -- PK, FK
                               PRIMARY KEY (codEquipe, codTorneio), -- Chave Primária Composta
                               FOREIGN KEY (codEquipe) REFERENCES Equipe(cod),
                               FOREIGN KEY (codTorneio) REFERENCES Torneio(cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela Jogo
CREATE TABLE Jogo (
                      cod INT NOT NULL AUTO_INCREMENT, -- PK, NOT NULL, AUTO_INCREMENT
                      data DATE NOT NULL,
                      hora TIME NOT NULL,
                      pTime1 INT NOT NULL CHECK (pTime1 >= 0), -- Pontuação >= 0
                      pTime2 INT NOT NULL CHECK (pTime2 >= 0), -- Pontuação >= 0
                      placar VARCHAR(10) NOT NULL,
                      codEquipe1 INT NOT NULL, -- FK para Equipe
                      codEquipe2 INT NOT NULL, -- FK para Equipe
                      codTorneio INT NOT NULL, -- FK para Torneio
                      PRIMARY KEY (cod),
                      FOREIGN KEY (codEquipe1) REFERENCES Equipe(cod),
                      FOREIGN KEY (codEquipe2) REFERENCES Equipe(cod),
                      FOREIGN KEY (codTorneio) REFERENCES Torneio(cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



