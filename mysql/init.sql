-- 1. Criacao do Banco de Dados
CREATE
DATABASE IF NOT EXISTS gestoresportivo;
USE gestoresportivo;
ALTER DATABASE gestoresportivo CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
-- 2. Criacao das Tabelas
CREATE TABLE atleta
(
    cod             INT         NOT NULL AUTO_INCREMENT,
    cpf             VARCHAR(14) NOT NULL UNIQUE,
    nome            VARCHAR(50) NOT NULL,
    sexo            CHAR(1)     NOT NULL CHECK (sexo IN ('f', 'm')),
    cidade          VARCHAR(50) NOT NULL,
    data_nascimento DATE        NOT NULL,
    PRIMARY KEY (cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE modalidade
(
    cod               INT         NOT NULL AUTO_INCREMENT,
    nome              VARCHAR(50) NOT NULL,
    qtd_atleta_equipe INT         NOT NULL CHECK (qtd_atleta_equipe > 0),
    PRIMARY KEY (cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE equipe
(
    cod            INT         NOT NULL AUTO_INCREMENT,
    nome           VARCHAR(50) NOT NULL,
    sigla          VARCHAR(5)  NOT NULL,
    cod_modalidade INT         NOT NULL,
    PRIMARY KEY (cod),
    FOREIGN KEY (cod_modalidade) REFERENCES modalidade (cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE torneio
(
    cod            INT         NOT NULL AUTO_INCREMENT,
    nome           VARCHAR(50) NOT NULL,
    qtd_equipe     INT         NOT NULL CHECK (qtd_equipe > 0),
    cod_modalidade INT         NOT NULL,
    PRIMARY KEY (cod),
    FOREIGN KEY (cod_modalidade) REFERENCES modalidade (cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE atleta_equipe
(
    cod_atleta INT NOT NULL,
    cod_equipe INT NOT NULL,
    PRIMARY KEY (cod_atleta, cod_equipe),
    FOREIGN KEY (cod_atleta) REFERENCES atleta (cod),
    FOREIGN KEY (cod_equipe) REFERENCES equipe (cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE equipe_torneio
(
    cod_equipe  INT NOT NULL,
    cod_torneio INT NOT NULL,
    PRIMARY KEY (cod_equipe, cod_torneio),
    FOREIGN KEY (cod_equipe) REFERENCES equipe (cod),
    FOREIGN KEY (cod_torneio) REFERENCES torneio (cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE jogo
(
    cod         INT         NOT NULL AUTO_INCREMENT,
    data        DATE        NOT NULL,
    hora        TIME        NOT NULL,
    p_time1     INT         NOT NULL CHECK (p_time1 >= 0),
    p_time2     INT         NOT NULL CHECK (p_time2 >= 0),
    placar      VARCHAR(10) NOT NULL,
    cod_equipe1 INT         NOT NULL,
    cod_equipe2 INT         NOT NULL,
    cod_torneio INT         NOT NULL,
    PRIMARY KEY (cod),
    FOREIGN KEY (cod_equipe1) REFERENCES equipe (cod),
    FOREIGN KEY (cod_equipe2) REFERENCES equipe (cod),
    FOREIGN KEY (cod_torneio) REFERENCES torneio (cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- View
CREATE VIEW atletas_por_equipe_modalidade AS
SELECT A.nome AS nome_atleta, E.nome AS nome_equipe, M.nome AS nome_modalidade
FROM atleta A
         JOIN atleta_equipe AE ON A.cod = AE.cod_atleta
         JOIN equipe E ON AE.cod_equipe = E.cod
         JOIN modalidade M ON E.cod_modalidade = M.cod;
-- Function
DELIMITER
//
CREATE FUNCTION gerar_placar_jogo(p_time1 INT, p_time2 INT) RETURNS VARCHAR(10)
    DETERMINISTIC BEGIN RETURN CONCAT(p_time1, 'X', p_time2);
END //
DELIMITER ;
-- Procedure
DELIMITER
//
CREATE PROCEDURE inscrever_equipe(IN p_cod_equipe INT, IN p_cod_torneio INT) BEGIN INSERT INTO equipe_torneio (cod_equipe, cod_torneio) VALUES (p_cod_equipe, p_cod_torneio);
END //
DELIMITER ;
-- TRIGGERS
DELIMITER
//
CREATE TRIGGER validar_equipe_torneio_modalidade
    BEFORE INSERT
    ON equipe_torneio
    FOR EACH ROW
BEGIN
    DECLARE equipe_modalidade_cod INT; DECLARE torneio_modalidade_cod INT;
    SELECT cod_modalidade INTO equipe_modalidade_cod FROM equipe WHERE cod = NEW.cod_equipe;
    SELECT cod_modalidade INTO torneio_modalidade_cod FROM torneio WHERE cod = NEW.cod_torneio;
    IF equipe_modalidade_cod IS NULL OR torneio_modalidade_cod IS NULL OR equipe_modalidade_cod <> torneio_modalidade_cod THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Nao e possivel inscrever uma equipe em um torneio de modalidade diferente.';
END IF;
END //
DELIMITER ;

USE
gestoresportivo;

-- 1. Inserção de Modalidades
-- ID 1: Voleibol
INSERT INTO modalidade (nome, qtd_atleta_equipe)
VALUES ('Voleibol', 6);
-- ID 2: Volei (Duplicado no nome, mas diferente do anterior. Se o nome fosse UNIQUE, seria rejeitado)
INSERT INTO modalidade (nome, qtd_atleta_equipe)
VALUES ('Volei', 6);
-- ID 3: Futebol
INSERT INTO modalidade (nome, qtd_atleta_equipe)
VALUES ('Futebol', 11);


-- 2. Inserção de Equipes
-- ID 1: Time Alpha Renomeado (Modalidade Futebol - ID 3)
INSERT INTO equipe (nome, sigla, cod_modalidade)
VALUES ('Time Alpha Renomeado', 'TAR', 3);
-- ID 2: Time Alpha (Modalidade Futebol - ID 3)
INSERT INTO equipe (nome, sigla, cod_modalidade)
VALUES ('Time Alpha', 'TMA', 3);


-- 3. Inserção de Atletas
-- ID 1: Pedro Costa Júnior (CPF: 11122233344)
INSERT INTO atleta (nome, cpf, sexo, cidade, data_nascimento)
VALUES ('Pedro Costa Júnior', '38530634039', 'm', 'São Paulo', '1999-05-10');

-- ID 2: Mariana Souza (CPF: 55566677788)
INSERT INTO atleta (nome, cpf, sexo, cidade, data_nascimento)
VALUES ('Mariana Souza', '57126929021', 'f', 'Curitiba', '2001-11-22');

-- ID 3: Fulano de Tal (CPF: 98765432109)
INSERT INTO atleta (nome, cpf, sexo, cidade, data_nascimento)
VALUES ('Fulano de Tal', '99989799008', 'm', 'Porto Alegre', '1990-10-25');

-- ID 4: Carlos Pereira da Silva (CPF: 11223344556)
INSERT INTO atleta (nome, cpf, sexo, cidade, data_nascimento)
VALUES ('Carlos Pereira da Silva', '98866240001', 'm', 'Niterói', '1995-03-20');


-- 4. Inserção de Torneios
-- ID 1: Campeonato Nacional (Modalidade Futebol - ID 3)
INSERT INTO torneio (nome, qtd_equipe, cod_modalidade)
VALUES ('Campeonato Nacional', 16, 3);
-- ID 2: Campeonato Nacional - Edição 2025 (Modalidade Futebol - ID 3)
INSERT INTO torneio (nome, qtd_equipe, cod_modalidade)
VALUES ('Campeonato Nacional - Edição 2025', 32, 3);


-- 5. Inserção de Associações Equipes-Torneios
-- ID Equipe 1 (TAR) e ID Torneio 1 (Campeonato Nacional)
INSERT INTO equipe_torneio (cod_equipe, cod_torneio)
VALUES (1, 1);
-- ID Equipe 2 (TMA) e ID Torneio 1 (Campeonato Nacional)
INSERT INTO equipe_torneio (cod_equipe, cod_torneio)
VALUES (2, 1);


-- 6. Inserção de Jogos
-- Jogo 1: Equipe 1 vs Equipe 2 no Torneio 1
INSERT INTO jogo (data, hora, p_time1, p_time2, placar, cod_equipe1, cod_equipe2, cod_torneio)
VALUES ('2025-08-01', '19:00:00', 3, 2, gerar_placar_jogo(3, 2), 1, 2, 1);
-- Jogo 2: Equipe 1 vs Equipe 2 no Torneio 1 (com pontuação diferente)
INSERT INTO jogo (data, hora, p_time1, p_time2, placar, cod_equipe1, cod_equipe2, cod_torneio)
VALUES ('2025-08-01', '20:30:00', 4, 3, gerar_placar_jogo(4, 3), 1, 2, 1);


-- 7. Inserção de Associações Atletas-Equipes
-- Atleta 1 e Equipe 1
INSERT INTO atleta_equipe (cod_atleta, cod_equipe)
VALUES (1, 1);
