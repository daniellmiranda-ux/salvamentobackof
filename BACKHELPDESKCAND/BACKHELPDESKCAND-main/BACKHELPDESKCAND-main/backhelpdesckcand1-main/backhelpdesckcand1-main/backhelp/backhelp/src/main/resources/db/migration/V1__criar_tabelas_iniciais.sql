CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    setor VARCHAR(100),
    perfil VARCHAR(50) NOT NULL,
    email_confirmado BOOLEAN DEFAULT FALSE
);

CREATE TABLE chamados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    nivel VARCHAR(50) NOT NULL,
    urgencia VARCHAR(50) NOT NULL,
    prazo_resolucao DATETIME,
    caminho_anexo VARCHAR(255),
    usuario_id BIGINT,
    CONSTRAINT fk_chamado_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);