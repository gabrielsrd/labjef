
-- conecta no database labjefdb
\c labjefdb 
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    cpf VARCHAR(30) NOT NULL,
    nome VARCHAR(30) NOT NULL,
    login VARCHAR(30) NOT NULL,
    senha VARCHAR(30) NOT NULL,
    data_nasc DATE NOT NULL,
    instituicao VARCHAR(30) NOT NULL,
    endereco VARCHAR(30) NOT NULL
);

CREATE TABLE perfil (
    id SERIAL PRIMARY KEY,
    tipo_perf VARCHAR(30) NOT NULL,
    cod_perf INT NOT NULL
);

CREATE TABLE servico (
    id_serv SERIAL PRIMARY KEY,
    descricao VARCHAR(30) NOT NULL,
    tipo_serv VARCHAR(30) NOT NULL,
    cod_serv INT NOT NULL,
    id_perfil INT NOT NULL,
    FOREIGN KEY (id_perfil) REFERENCES perfil(id)
);

CREATE TABLE curso (
    id SERIAL PRIMARY KEY,
    cod_curso INT NOT NULL UNIQUE
);

CREATE TABLE disciplina (
    id SERIAL PRIMARY KEY,
    codigo INT NOT NULL UNIQUE,
    ementa VARCHAR(1000) NOT NULL,
    data_criacao DATE NOT NULL,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE aluno (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE docente (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE funcionario (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE rel_disciplina_curso (
    id SERIAL PRIMARY KEY,
    id_disc INT NOT NULL,
    id_curso INT NOT NULL,
    FOREIGN KEY (id_disc) REFERENCES disciplina(id),
    FOREIGN KEY (id_curso) REFERENCES curso(id)
);

CREATE TABLE rel_usuario_perfil (
    id SERIAL PRIMARY KEY,
    id_perf INT NOT NULL,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_perf) REFERENCES perfil(id),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE rel_aluno_curso (
    id SERIAL PRIMARY KEY,
    data_inic DATE NOT NULL,
    data_fim DATE NULL,
    nota_ingresso INT NOT NULL,
    id_curso INT NOT NULL,
    id_aluno INT NOT NULL,
    FOREIGN KEY (id_curso) REFERENCES curso(id),
    FOREIGN KEY (id_aluno) REFERENCES aluno(id)
);

CREATE TABLE especialidade_docente (
    id SERIAL PRIMARY KEY,
    especialidade VARCHAR(255) NOT NULL,
    id_docente INT NOT NULL,
    FOREIGN KEY (id_docente) REFERENCES docente(id)
);

CREATE TABLE funcao_tecnica_docente (
    id SERIAL PRIMARY KEY,
    func_tec VARCHAR(255) NOT NULL,
    id_docente INT NOT NULL,
    FOREIGN KEY (id_docente) REFERENCES docente(id)
);

CREATE TABLE especialidade_funcionario (
    id SERIAL PRIMARY KEY,
    especialidade VARCHAR(255) NOT NULL,
    id_func INT NOT NULL,
    FOREIGN KEY (id_func) REFERENCES funcionario(id)
);

CREATE TABLE funcao_tecnica_funcionario (
    id SERIAL PRIMARY KEY,
    func_tec VARCHAR(255) NOT NULL,
    id_func INT NOT NULL,
    FOREIGN KEY (id_func) REFERENCES funcionario(id)
);

CREATE TABLE disciplina_aluno_docente (
    id SERIAL PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_docente INT NOT NULL,
    id_disc INT NOT NULL,
    data_inic DATE NOT NULL,
    data_fim DATE NULL,
    nota INT NULL,
    FOREIGN KEY (id_aluno) REFERENCES aluno(id),
    FOREIGN KEY (id_docente) REFERENCES docente(id),
    FOREIGN KEY (id_disc) REFERENCES disciplina(id)
);




