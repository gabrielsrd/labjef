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
--CREATE FUNCTION listar_disciplinas_oferecidas() RETURNS TABLE (
--    disciplina VARCHAR,
--    total_alunos INTEGER,
--    total_professores INTEGER,
--    total_oferecimentos INTEGER
--) AS $$
--BEGIN
--    RETURN QUERY
--    SELECT d.nome AS disciplina,
--           COUNT(DISTINCT dad.id_aluno) AS total_alunos,
--           COUNT(DISTINCT dad.id_docente) AS total_professores,
--           COUNT(DISTINCT dad.data_inic) AS total_oferecimentos
--    FROM rel_disciplina_aluno_docente AS dad, disciplina AS d
--    WHERE dad.id_disc = d.id
--    GROUP BY dad.id_disc, d.nome
--    ORDER BY COUNT(DISTINCT dad.data_inic) DESC
--    LIMIT 5;
--END;
--$$ LANGUAGE plpgsql;

CREATE FUNCTION listar_disciplinas_oferecidas() RETURNS TABLE (
    disciplina_id INT
    disciplina VARCHAR
) AS $$
BEGIN
    RETURN QUERY
    SELECT d.id as disciplina_id, d.nome AS disciplina
    FROM rel_disciplina_aluno_docente, disciplina as d
    JOIN rel_disciplina_aluno_docente dad ON dad.id_disc = d.id
    ORDER BY COUNT(DISTINCT dad.id_disc) DESC
    GROUP BY d.nome
    LIMIT 5;
END;
$$ LANGUAGE plpgsql;

-- 4
