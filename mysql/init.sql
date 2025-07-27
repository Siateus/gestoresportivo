-- DDL (Data Definition Language) - Criação do Schema

-- 1. Criação do Banco de Dados
-- Garante que o banco de dados 'gestoresportivo' exista.
CREATE DATABASE IF NOT EXISTS gestoresportivo;

-- Seleciona o banco de dados para que os comandos seguintes operem nele.
USE gestoresportivo;

-- Define o conjunto de caracteres padrão e o collation para o banco de dados
-- Isso garante compatibilidade com caracteres especiais e ordenação.
ALTER DATABASE gestoresportivo
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

-- 2. Criação das Tabelas

-- Tabela Atleta
-- Armazena informações sobre os atletas.
CREATE TABLE atleta (
                        cod INT NOT NULL AUTO_INCREMENT, -- Chave Primária (PK), auto-incrementada
                        cpf VARCHAR(14) NOT NULL UNIQUE, -- CPF do atleta, deve ser único e não nulo
                        nome VARCHAR(30) NOT NULL,
                        sexo CHAR(1) NOT NULL CHECK (sexo IN ('f', 'm')), -- Sexo do atleta ('f' para feminino, 'm' para masculino)
                        cidade VARCHAR(30) NOT NULL,
                        data_nascimento DATE NOT NULL, -- Data de nascimento do atleta (usando snake_case para consistência)
                        PRIMARY KEY (cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela Modalidade
-- Armazena as modalidades esportivas disponíveis.
CREATE TABLE modalidade (
                            cod INT NOT NULL AUTO_INCREMENT, -- Chave Primária (PK), auto-incrementada
                            nome VARCHAR(30) NOT NULL,
                            qtd_atleta_equipe INT NOT NULL CHECK (qtd_atleta_equipe > 0), -- Quantidade mínima de atletas por equipe na modalidade
                            PRIMARY KEY (cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela Equipe
-- Armazena informações sobre as equipes.
CREATE TABLE equipe (
                        cod INT NOT NULL AUTO_INCREMENT, -- Chave Primária (PK), auto-incrementada
                        nome VARCHAR(30) NOT NULL,
                        sigla VARCHAR(5) NOT NULL,
                        cod_modalidade INT NOT NULL, -- Chave Estrangeira (FK) para Modalidade
                        PRIMARY KEY (cod),
                        FOREIGN KEY (cod_modalidade) REFERENCES modalidade(cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela Torneio
-- Armazena informações sobre os torneios.
CREATE TABLE torneio (
                         cod INT NOT NULL AUTO_INCREMENT, -- Chave Primária (PK), auto-incrementada
                         nome VARCHAR(30) NOT NULL,
                         qtd_equipe INT NOT NULL CHECK (qtd_equipe > 0), -- Quantidade mínima de equipes por torneio
                         cod_modalidade INT NOT NULL, -- Chave Estrangeira (FK) para Modalidade
                         PRIMARY KEY (cod),
                         FOREIGN KEY (cod_modalidade) REFERENCES modalidade(cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela AtletaEquipe
-- Tabela de junção para o relacionamento N:N entre Atleta e Equipe.
CREATE TABLE atleta_equipe ( -- Nome da tabela ajustado para snake_case
                               cod_atleta INT NOT NULL, -- Parte da Chave Primária Composta, FK para Atleta
                               cod_equipe INT NOT NULL, -- Parte da Chave Primária Composta, FK para Equipe
                               PRIMARY KEY (cod_atleta, cod_equipe), -- Chave Primária Composta
                               FOREIGN KEY (cod_atleta) REFERENCES atleta(cod),
                               FOREIGN KEY (cod_equipe) REFERENCES equipe(cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela EquipeTorneio
-- Tabela de junção para o relacionamento N:N entre Equipe e Torneio.
CREATE TABLE equipe_torneio ( -- Nome da tabela ajustado para snake_case
                                cod_equipe INT NOT NULL, -- Parte da Chave Primária Composta, FK para Equipe
                                cod_torneio INT NOT NULL, -- Parte da Chave Primária Composta, FK para Torneio
                                PRIMARY KEY (cod_equipe, cod_torneio), -- Chave Primária Composta
                                FOREIGN KEY (cod_equipe) REFERENCES equipe(cod),
                                FOREIGN KEY (cod_torneio) REFERENCES torneio(cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabela Jogo
-- Armazena informações sobre os jogos realizados.
CREATE TABLE jogo (
                      cod INT NOT NULL AUTO_INCREMENT, -- Chave Primária (PK), auto-incrementada
                      data DATE NOT NULL,
                      hora TIME NOT NULL,
                      p_time1 INT NOT NULL CHECK (p_time1 >= 0), -- Pontuação da primeira equipe (ajustado para snake_case)
                      p_time2 INT NOT NULL CHECK (p_time2 >= 0), -- Pontuação da segunda equipe (ajustado para snake_case)
                      placar VARCHAR(10) NOT NULL,
                      cod_equipe1 INT NOT NULL, -- FK para Equipe 1 (ajustado para snake_case)
                      cod_equipe2 INT NOT NULL, -- FK para Equipe 2 (ajustado para snake_case)
                      cod_torneio INT NOT NULL, -- FK para Torneio (ajustado para snake_case)
                      PRIMARY KEY (cod),
                      FOREIGN KEY (cod_equipe1) REFERENCES equipe(cod),
                      FOREIGN KEY (cod_equipe2) REFERENCES equipe(cod),
                      FOREIGN KEY (cod_torneio) REFERENCES torneio(cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

