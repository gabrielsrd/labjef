-- PROCEDURES

-- 1
CREATE OR REPLACE FUNCTION get_usuario_servico()
RETURNS TABLE (nome VARCHAR, tipo_serv VARCHAR)
AS $$
BEGIN
    RETURN QUERY
    SELECT us.nome, serv.tipo_serv
    FROM rel_usuario_perfil AS relpf, servico AS serv, usuario AS us, perfil AS perf
    WHERE relpf.id_perf = perf.id
      AND relpf.id_usuario = us.id
      AND serv.id_perfil = relpf.id_perf;
END;
$$ LANGUAGE plpgsql;

-- 2
CREATE OR REPLACE FUNCTION get_total_servicos_por_perfil()
RETURNS TABLE(total_servicos INTEGER, id_perf INTEGER) AS $$
BEGIN
    RETURN QUERY
    SELECT COUNT(servico.id) AS total_servicos, rel1.id_perf
    FROM rel_usuario_perfil AS rel1, usuario AS us, servico
    WHERE us.id = rel1.id_usuario
        AND rel1.id_perf = servico.id_perfil
    GROUP BY rel1.id_perf
    ORDER BY total_servicos ASC;

    RETURN;
END;
$$ LANGUAGE plpgsql;

-- 3
CREATE FUNCTION listar_disciplinas_oferecidas() RETURNS TABLE (
    disciplina VARCHAR,
    total_alunos INTEGER,
    total_professores INTEGER,
    total_oferecimentos INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT d.nome AS disciplina,
           COUNT(DISTINCT dad.id_aluno) AS total_alunos,
           COUNT(DISTINCT dad.id_docente) AS total_professores,
           COUNT(DISTINCT dad.data_inic) AS total_oferecimentos
    FROM rel_disciplina_aluno_docente AS dad, disciplina AS d
    WHERE dad.id_disc = d.id
    GROUP BY dad.id_disc, d.nome
    ORDER BY COUNT(DISTINCT dad.data_inic) DESC
    LIMIT 5;
END;
$$ LANGUAGE plpgsql;