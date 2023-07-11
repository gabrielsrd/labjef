-- conecta no database labjefdb
\c labjefdb 

-- PROCEDURES

-- 1
CREATE OR REPLACE FUNCTION get_usuario_servico()
RETURNS TABLE (nome VARCHAR, tipo_serv VARCHAR, descricao VARCHAR, id_servico INT)
AS $$
BEGIN
    RETURN QUERY
    SELECT us.nome, serv.tipo_serv, serv.descricao, serv.id
    FROM rel_usuario_perfil AS relpf, servico AS serv, usuario AS us, perfil AS perf
    WHERE relpf.id_perf = perf.id
      AND relpf.id_usuario = us.id
      AND serv.id_perfil = relpf.id_perf;
END;
$$ LANGUAGE plpgsql;

-- 2
CREATE OR REPLACE FUNCTION get_total_servicos_por_perfil()
RETURNS TABLE (tipo_perf VARCHAR(30), total_servicos BIGINT)
AS $$
BEGIN
    RETURN QUERY
    SELECT p.tipo_perf, COUNT(rs.id_servico) AS total_servicos
    FROM perfil p
    JOIN rel_usuario_perfil up ON p.id = up.id_perf
    JOIN rel_usuario_servico rs ON up.id_usuario = rs.id_usuario
    GROUP BY p.tipo_perf
    ORDER BY total_servicos ASC;
END;
$$ LANGUAGE plpgsql;

-- 3
CREATE OR REPLACE FUNCTION listar_disciplinas_oferecidas() RETURNS TABLE (
    disciplina_id INT,
    nome_disc VARCHAR,
    nome_al VARCHAR,
    nome_doc VARCHAR,
    data_inic DATE
) AS $$
BEGIN
    RETURN QUERY
    SELECT id_disc as disciplina_id,  disciplina.nome as nome_disc, us1.nome as nome_al, us2.nome as nome_doc, rel.data_inic as data_inic
    FROM lista_5() AS lim, usuario AS us1, usuario AS us2, aluno, docente, rel_disciplina_aluno_docente AS rel, disciplina
    WHERE us1.id = aluno.id_usuario 
        AND us2.id = docente.id_usuario 
        AND aluno.id = rel.id_aluno 
        AND docente.id = rel.id_docente 
        AND rel.id_disc = lim.disciplina_id
        AND disciplina.id = lim.disciplina_id
    ORDER BY lim.quant DESC;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION lista_5() RETURNS TABLE (
    disciplina_id INTEGER,
    disciplina VARCHAR,
    quant BIGINT
) AS $$ 
BEGIN
        RETURN QUERY
        SELECT dad.id_disc AS disciplina_id, disciplina.nome as disciplina, COUNT(*) AS quant
        FROM (
            SELECT DISTINCT data_inic, id_disc
            FROM rel_disciplina_aluno_docente
        ) AS dad
        JOIN disciplina ON disciplina.id = dad.id_disc
        GROUP BY dad.id_disc, disciplina.nome
        ORDER BY COUNT(*) DESC
        LIMIT 5;
END;
$$ LANGUAGE plpgsql;

-- 4
CREATE OR REPLACE FUNCTION listar_docentes_mais_ministraram() RETURNS TABLE (
    docente_id INT,
    docente_nome VARCHAR,
    total_disciplinas BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT d.id, u.nome, COUNT(*) AS total_disciplinas
    FROM (
        SELECT DISTINCT id_docente, id_disc, data_inic
        FROM rel_disciplina_aluno_docente
    ) as r
    JOIN docente d ON r.id_docente = d.id
    JOIN usuario u ON d.id_usuario = u.id
    WHERE r.data_inic BETWEEN '2020-05-01' AND '2023-05-31'
    GROUP BY d.id, u.nome
    ORDER BY total_disciplinas DESC
    LIMIT 5;
END;
$$ LANGUAGE plpgsql;
