-- insere dados nas tabelas
INSERT INTO usuario (cpf, nome, login, senha, data_nasc, instituicao, endereco) VALUES ('12345678901', 'adminnome', 'admin', 'admin', '1990-01-01', 'USP', 'Rua 1');
INSERT INTO usuario (cpf, nome, login, senha, data_nasc, instituicao, endereco) VALUES ('12345678902', 'alunonome', 'aluno', 'aluno', '1990-01-01', 'USP', 'Rua 2');
INSERT INTO usuario (cpf, nome, login, senha, data_nasc, instituicao, endereco) VALUES ('12345678903', 'docentenome', 'docente', 'docente', '1990-01-01', 'USP', 'Rua 3');
INSERT INTO usuario (cpf, nome, login, senha, data_nasc, instituicao, endereco) VALUES ('12345678904', 'funcionarionome', 'funcionario', 'funcionario', '1990-01-01', 'USP', 'Rua 4');

INSERT INTO perfil (tipo_perf, cod_perf) VALUES ('Administrador', 1);
INSERT INTO perfil (tipo_perf, cod_perf) VALUES ('Aluno', 2);
INSERT INTO perfil (tipo_perf, cod_perf) VALUES ('Docente', 3);
INSERT INTO perfil (tipo_perf, cod_perf) VALUES ('Funcionario', 4);

INSERT INTO servico (descricao, tipo_serv, cod_serv, id_perfil) VALUES ('Cadastrar Usuario', 'Cadastrar', 1, 1);
INSERT INTO servico (descricao, tipo_serv, cod_serv, id_perfil) VALUES ('Editar Usuario', 'Editar', 2, 1);
INSERT INTO servico (descricao, tipo_serv, cod_serv, id_perfil) VALUES ('Excluir Usuario', 'Excluir', 3, 1);

INSERT INTO servico (descricao, tipo_serv, cod_serv, id_perfil) VALUES ('Matricular Disciplina', 'Cadastrar', 1, 2);

INSERT INTO servico (descricao, tipo_serv, cod_serv, id_perfil) VALUES ('Cadastrar Disciplina', 'Cadastrar', 1, 3);
INSERT INTO servico (descricao, tipo_serv, cod_serv, id_perfil) VALUES ('Editar Disciplina', 'Editar', 2, 3);
INSERT INTO servico (descricao, tipo_serv, cod_serv, id_perfil) VALUES ('Excluir Disciplina', 'Excluir', 3, 3);


INSERT INTO curso (nome, codigo, data_criacao) VALUES ('Ciencia da Computacao', 1, '2010-01-01');
INSERT INTO curso (nome, codigo, data_criacao) VALUES ('Engenharia da Computacao', 2, '2010-01-01');
INSERT INTO curso (nome, codigo, data_criacao) VALUES ('Engenharia de Software', 3, '2010-01-01');

INSERT INTO disciplina (nome, codigo, carga_horaria, data_criacao) VALUES ('Programacao I', 1, 60, '2010-01-01');
INSERT INTO disciplina (nome, codigo, carga_horaria, data_criacao) VALUES ('Programacao II', 2, 60, '2010-01-01');

INSERT INTO aluno (matricula, data_ingresso, id_usuario) VALUES (1, '2010-01-01', 2);

INSERT INTO docente (matricula, data_ingresso, id_usuario) VALUES (1, '2010-01-01', 3);