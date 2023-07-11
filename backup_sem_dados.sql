--
-- PostgreSQL database dump
--

-- Dumped from database version 14.8 (Ubuntu 14.8-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.8 (Ubuntu 14.8-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: get_total_servicos_por_perfil(); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_total_servicos_por_perfil() RETURNS TABLE(tipo_perf character varying, total_servicos bigint)
    LANGUAGE plpgsql
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
$$;


ALTER FUNCTION public.get_total_servicos_por_perfil() OWNER TO admin;

--
-- Name: get_usuario_servico(); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_usuario_servico() RETURNS TABLE(nome character varying, tipo_serv character varying, descricao character varying, id_servico integer)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT us.nome, serv.tipo_serv, serv.descricao, serv.id
    FROM rel_usuario_perfil AS relpf, servico AS serv, usuario AS us, perfil AS perf
    WHERE relpf.id_perf = perf.id
      AND relpf.id_usuario = us.id
      AND serv.id_perfil = relpf.id_perf;
END;
$$;


ALTER FUNCTION public.get_usuario_servico() OWNER TO admin;

--
-- Name: lista_5(); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.lista_5() RETURNS TABLE(disciplina_id integer, disciplina character varying, quant bigint)
    LANGUAGE plpgsql
    AS $$ 
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
    $$;


ALTER FUNCTION public.lista_5() OWNER TO admin;

--
-- Name: listar_disciplinas_oferecidas(); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.listar_disciplinas_oferecidas() RETURNS TABLE(disciplina_id integer, nome_disc character varying, nome_al character varying, nome_doc character varying, data_inic date)
    LANGUAGE plpgsql
    AS $$
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
    $$;


ALTER FUNCTION public.listar_disciplinas_oferecidas() OWNER TO admin;

--
-- Name: listar_disciplinas_oferecidas2(); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.listar_disciplinas_oferecidas2() RETURNS TABLE(disciplina_id integer, nome_al character varying, nome_doc character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT id_disc as disciplina_id, us1.nome as nome_al, us2.nome as nome_doc
    FROM lista_5() AS lim, usuario AS us1, usuario AS us2, aluno, docente, rel_disciplina_aluno_docente AS rel
    WHERE us1.id = aluno.id_usuario 
        AND us2.id = docente.id_usuario 
        AND aluno.id = rel.id_aluno 
        AND docente.id = rel.id_docente 
        AND rel.id_disc = lim.disciplina_id
    ORDER BY lim.quant DESC;
END;
$$;


ALTER FUNCTION public.listar_disciplinas_oferecidas2() OWNER TO admin;

--
-- Name: listar_docentes_mais_ministraram(); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.listar_docentes_mais_ministraram() RETURNS TABLE(docente_id integer, docente_nome character varying, total_disciplinas bigint)
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.listar_docentes_mais_ministraram() OWNER TO admin;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: aluno; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.aluno (
    id integer NOT NULL,
    id_usuario integer NOT NULL
);


ALTER TABLE public.aluno OWNER TO admin;

--
-- Name: aluno_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.aluno_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.aluno_id_seq OWNER TO admin;

--
-- Name: aluno_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.aluno_id_seq OWNED BY public.aluno.id;


--
-- Name: curso; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.curso (
    id integer NOT NULL,
    cod_curso character varying(30) NOT NULL
);


ALTER TABLE public.curso OWNER TO admin;

--
-- Name: curso_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.curso_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.curso_id_seq OWNER TO admin;

--
-- Name: curso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.curso_id_seq OWNED BY public.curso.id;


--
-- Name: disciplina; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.disciplina (
    id integer NOT NULL,
    codigo character varying(30) NOT NULL,
    ementa character varying(1000) NOT NULL,
    data_criacao date NOT NULL,
    nome character varying(50) NOT NULL
);


ALTER TABLE public.disciplina OWNER TO admin;

--
-- Name: disciplina_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.disciplina_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.disciplina_id_seq OWNER TO admin;

--
-- Name: disciplina_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.disciplina_id_seq OWNED BY public.disciplina.id;


--
-- Name: docente; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.docente (
    id integer NOT NULL,
    id_usuario integer NOT NULL
);


ALTER TABLE public.docente OWNER TO admin;

--
-- Name: docente_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.docente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.docente_id_seq OWNER TO admin;

--
-- Name: docente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.docente_id_seq OWNED BY public.docente.id;


--
-- Name: especialidade_docente; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.especialidade_docente (
    id integer NOT NULL,
    especialidade character varying(255) NOT NULL,
    id_docente integer NOT NULL
);


ALTER TABLE public.especialidade_docente OWNER TO admin;

--
-- Name: especialidade_docente_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.especialidade_docente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.especialidade_docente_id_seq OWNER TO admin;

--
-- Name: especialidade_docente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.especialidade_docente_id_seq OWNED BY public.especialidade_docente.id;


--
-- Name: especialidade_funcionario; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.especialidade_funcionario (
    id integer NOT NULL,
    especialidade character varying(255) NOT NULL,
    id_func integer NOT NULL
);


ALTER TABLE public.especialidade_funcionario OWNER TO admin;

--
-- Name: especialidade_funcionario_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.especialidade_funcionario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.especialidade_funcionario_id_seq OWNER TO admin;

--
-- Name: especialidade_funcionario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.especialidade_funcionario_id_seq OWNED BY public.especialidade_funcionario.id;


--
-- Name: funcao_tecnica_docente; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.funcao_tecnica_docente (
    id integer NOT NULL,
    func_tec character varying(255) NOT NULL,
    id_docente integer NOT NULL
);


ALTER TABLE public.funcao_tecnica_docente OWNER TO admin;

--
-- Name: funcao_tecnica_docente_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.funcao_tecnica_docente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.funcao_tecnica_docente_id_seq OWNER TO admin;

--
-- Name: funcao_tecnica_docente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.funcao_tecnica_docente_id_seq OWNED BY public.funcao_tecnica_docente.id;


--
-- Name: funcao_tecnica_funcionario; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.funcao_tecnica_funcionario (
    id integer NOT NULL,
    func_tec character varying(255) NOT NULL,
    id_func integer NOT NULL
);


ALTER TABLE public.funcao_tecnica_funcionario OWNER TO admin;

--
-- Name: funcao_tecnica_funcionario_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.funcao_tecnica_funcionario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.funcao_tecnica_funcionario_id_seq OWNER TO admin;

--
-- Name: funcao_tecnica_funcionario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.funcao_tecnica_funcionario_id_seq OWNED BY public.funcao_tecnica_funcionario.id;


--
-- Name: funcionario; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.funcionario (
    id integer NOT NULL,
    id_usuario integer NOT NULL
);


ALTER TABLE public.funcionario OWNER TO admin;

--
-- Name: funcionario_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.funcionario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.funcionario_id_seq OWNER TO admin;

--
-- Name: funcionario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.funcionario_id_seq OWNED BY public.funcionario.id;


--
-- Name: perfil; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.perfil (
    id integer NOT NULL,
    tipo_perf character varying(30) NOT NULL,
    cod_perf character varying(30) NOT NULL
);


ALTER TABLE public.perfil OWNER TO admin;

--
-- Name: perfil_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.perfil_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.perfil_id_seq OWNER TO admin;

--
-- Name: perfil_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.perfil_id_seq OWNED BY public.perfil.id;


--
-- Name: rel_aluno_curso; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.rel_aluno_curso (
    id integer NOT NULL,
    data_inic date NOT NULL,
    data_fim date,
    nota_ingresso double precision NOT NULL,
    id_curso integer NOT NULL,
    id_aluno integer NOT NULL
);


ALTER TABLE public.rel_aluno_curso OWNER TO admin;

--
-- Name: rel_aluno_curso_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.rel_aluno_curso_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rel_aluno_curso_id_seq OWNER TO admin;

--
-- Name: rel_aluno_curso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.rel_aluno_curso_id_seq OWNED BY public.rel_aluno_curso.id;


--
-- Name: rel_disciplina_aluno_docente; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.rel_disciplina_aluno_docente (
    id integer NOT NULL,
    id_aluno integer NOT NULL,
    id_docente integer NOT NULL,
    id_disc integer NOT NULL,
    data_inic date NOT NULL,
    data_fim date,
    nota double precision
);


ALTER TABLE public.rel_disciplina_aluno_docente OWNER TO admin;

--
-- Name: rel_disciplina_aluno_docente_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.rel_disciplina_aluno_docente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rel_disciplina_aluno_docente_id_seq OWNER TO admin;

--
-- Name: rel_disciplina_aluno_docente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.rel_disciplina_aluno_docente_id_seq OWNED BY public.rel_disciplina_aluno_docente.id;


--
-- Name: rel_disciplina_curso; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.rel_disciplina_curso (
    id integer NOT NULL,
    id_disc integer NOT NULL,
    id_curso integer NOT NULL
);


ALTER TABLE public.rel_disciplina_curso OWNER TO admin;

--
-- Name: rel_disciplina_curso_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.rel_disciplina_curso_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rel_disciplina_curso_id_seq OWNER TO admin;

--
-- Name: rel_disciplina_curso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.rel_disciplina_curso_id_seq OWNED BY public.rel_disciplina_curso.id;


--
-- Name: rel_usuario_perfil; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.rel_usuario_perfil (
    id integer NOT NULL,
    id_perf integer NOT NULL,
    id_usuario integer NOT NULL
);


ALTER TABLE public.rel_usuario_perfil OWNER TO admin;

--
-- Name: rel_usuario_perfil_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.rel_usuario_perfil_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rel_usuario_perfil_id_seq OWNER TO admin;

--
-- Name: rel_usuario_perfil_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.rel_usuario_perfil_id_seq OWNED BY public.rel_usuario_perfil.id;


--
-- Name: rel_usuario_servico; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.rel_usuario_servico (
    id integer NOT NULL,
    id_servico integer NOT NULL,
    id_usuario integer NOT NULL,
    data_time timestamp(3) without time zone NOT NULL
);


ALTER TABLE public.rel_usuario_servico OWNER TO admin;

--
-- Name: rel_usuario_servico_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.rel_usuario_servico_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rel_usuario_servico_id_seq OWNER TO admin;

--
-- Name: rel_usuario_servico_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.rel_usuario_servico_id_seq OWNED BY public.rel_usuario_servico.id;


--
-- Name: servico; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.servico (
    id integer NOT NULL,
    descricao character varying(1000) NOT NULL,
    tipo_serv character varying(30) NOT NULL,
    cod_serv character varying(30) NOT NULL,
    id_perfil integer NOT NULL
);


ALTER TABLE public.servico OWNER TO admin;

--
-- Name: servico_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.servico_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.servico_id_seq OWNER TO admin;

--
-- Name: servico_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.servico_id_seq OWNED BY public.servico.id;


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.usuario (
    id integer NOT NULL,
    cpf character varying(30) NOT NULL,
    nome character varying(30) NOT NULL,
    login character varying(30) NOT NULL,
    senha character varying(30) NOT NULL,
    data_nasc date NOT NULL,
    instituicao character varying(30) NOT NULL,
    endereco character varying(30) NOT NULL
);


ALTER TABLE public.usuario OWNER TO admin;

--
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO admin;

--
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- Name: aluno id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.aluno ALTER COLUMN id SET DEFAULT nextval('public.aluno_id_seq'::regclass);


--
-- Name: curso id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.curso ALTER COLUMN id SET DEFAULT nextval('public.curso_id_seq'::regclass);


--
-- Name: disciplina id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.disciplina ALTER COLUMN id SET DEFAULT nextval('public.disciplina_id_seq'::regclass);


--
-- Name: docente id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.docente ALTER COLUMN id SET DEFAULT nextval('public.docente_id_seq'::regclass);


--
-- Name: especialidade_docente id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.especialidade_docente ALTER COLUMN id SET DEFAULT nextval('public.especialidade_docente_id_seq'::regclass);


--
-- Name: especialidade_funcionario id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.especialidade_funcionario ALTER COLUMN id SET DEFAULT nextval('public.especialidade_funcionario_id_seq'::regclass);


--
-- Name: funcao_tecnica_docente id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcao_tecnica_docente ALTER COLUMN id SET DEFAULT nextval('public.funcao_tecnica_docente_id_seq'::regclass);


--
-- Name: funcao_tecnica_funcionario id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcao_tecnica_funcionario ALTER COLUMN id SET DEFAULT nextval('public.funcao_tecnica_funcionario_id_seq'::regclass);


--
-- Name: funcionario id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcionario ALTER COLUMN id SET DEFAULT nextval('public.funcionario_id_seq'::regclass);


--
-- Name: perfil id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.perfil ALTER COLUMN id SET DEFAULT nextval('public.perfil_id_seq'::regclass);


--
-- Name: rel_aluno_curso id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_aluno_curso ALTER COLUMN id SET DEFAULT nextval('public.rel_aluno_curso_id_seq'::regclass);


--
-- Name: rel_disciplina_aluno_docente id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_disciplina_aluno_docente ALTER COLUMN id SET DEFAULT nextval('public.rel_disciplina_aluno_docente_id_seq'::regclass);


--
-- Name: rel_disciplina_curso id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_disciplina_curso ALTER COLUMN id SET DEFAULT nextval('public.rel_disciplina_curso_id_seq'::regclass);


--
-- Name: rel_usuario_perfil id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_usuario_perfil ALTER COLUMN id SET DEFAULT nextval('public.rel_usuario_perfil_id_seq'::regclass);


--
-- Name: rel_usuario_servico id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_usuario_servico ALTER COLUMN id SET DEFAULT nextval('public.rel_usuario_servico_id_seq'::regclass);


--
-- Name: servico id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.servico ALTER COLUMN id SET DEFAULT nextval('public.servico_id_seq'::regclass);


--
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- Name: aluno aluno_id_usuario_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.aluno
    ADD CONSTRAINT aluno_id_usuario_key UNIQUE (id_usuario);


--
-- Name: aluno aluno_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.aluno
    ADD CONSTRAINT aluno_pkey PRIMARY KEY (id);


--
-- Name: curso curso_cod_curso_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.curso
    ADD CONSTRAINT curso_cod_curso_key UNIQUE (cod_curso);


--
-- Name: curso curso_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.curso
    ADD CONSTRAINT curso_pkey PRIMARY KEY (id);


--
-- Name: disciplina disciplina_codigo_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.disciplina
    ADD CONSTRAINT disciplina_codigo_key UNIQUE (codigo);


--
-- Name: disciplina disciplina_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.disciplina
    ADD CONSTRAINT disciplina_pkey PRIMARY KEY (id);


--
-- Name: docente docente_id_usuario_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.docente
    ADD CONSTRAINT docente_id_usuario_key UNIQUE (id_usuario);


--
-- Name: docente docente_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.docente
    ADD CONSTRAINT docente_pkey PRIMARY KEY (id);


--
-- Name: especialidade_docente especialidade_docente_id_docente_especialidade_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.especialidade_docente
    ADD CONSTRAINT especialidade_docente_id_docente_especialidade_key UNIQUE (id_docente, especialidade);


--
-- Name: especialidade_docente especialidade_docente_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.especialidade_docente
    ADD CONSTRAINT especialidade_docente_pkey PRIMARY KEY (id);


--
-- Name: especialidade_funcionario especialidade_funcionario_id_func_especialidade_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.especialidade_funcionario
    ADD CONSTRAINT especialidade_funcionario_id_func_especialidade_key UNIQUE (id_func, especialidade);


--
-- Name: especialidade_funcionario especialidade_funcionario_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.especialidade_funcionario
    ADD CONSTRAINT especialidade_funcionario_pkey PRIMARY KEY (id);


--
-- Name: funcao_tecnica_docente funcao_tecnica_docente_id_docente_func_tec_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcao_tecnica_docente
    ADD CONSTRAINT funcao_tecnica_docente_id_docente_func_tec_key UNIQUE (id_docente, func_tec);


--
-- Name: funcao_tecnica_docente funcao_tecnica_docente_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcao_tecnica_docente
    ADD CONSTRAINT funcao_tecnica_docente_pkey PRIMARY KEY (id);


--
-- Name: funcao_tecnica_funcionario funcao_tecnica_funcionario_id_func_func_tec_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcao_tecnica_funcionario
    ADD CONSTRAINT funcao_tecnica_funcionario_id_func_func_tec_key UNIQUE (id_func, func_tec);


--
-- Name: funcao_tecnica_funcionario funcao_tecnica_funcionario_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcao_tecnica_funcionario
    ADD CONSTRAINT funcao_tecnica_funcionario_pkey PRIMARY KEY (id);


--
-- Name: funcionario funcionario_id_usuario_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_id_usuario_key UNIQUE (id_usuario);


--
-- Name: funcionario funcionario_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_pkey PRIMARY KEY (id);


--
-- Name: perfil perfil_cod_perf_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.perfil
    ADD CONSTRAINT perfil_cod_perf_key UNIQUE (cod_perf);


--
-- Name: perfil perfil_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.perfil
    ADD CONSTRAINT perfil_pkey PRIMARY KEY (id);


--
-- Name: rel_aluno_curso rel_aluno_curso_id_curso_id_aluno_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_aluno_curso
    ADD CONSTRAINT rel_aluno_curso_id_curso_id_aluno_key UNIQUE (id_curso, id_aluno);


--
-- Name: rel_aluno_curso rel_aluno_curso_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_aluno_curso
    ADD CONSTRAINT rel_aluno_curso_pkey PRIMARY KEY (id);


--
-- Name: rel_disciplina_aluno_docente rel_disciplina_aluno_docente_id_aluno_id_docente_id_disc_da_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_disciplina_aluno_docente
    ADD CONSTRAINT rel_disciplina_aluno_docente_id_aluno_id_docente_id_disc_da_key UNIQUE (id_aluno, id_docente, id_disc, data_inic);


--
-- Name: rel_disciplina_aluno_docente rel_disciplina_aluno_docente_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_disciplina_aluno_docente
    ADD CONSTRAINT rel_disciplina_aluno_docente_pkey PRIMARY KEY (id);


--
-- Name: rel_disciplina_curso rel_disciplina_curso_id_disc_id_curso_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_disciplina_curso
    ADD CONSTRAINT rel_disciplina_curso_id_disc_id_curso_key UNIQUE (id_disc, id_curso);


--
-- Name: rel_disciplina_curso rel_disciplina_curso_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_disciplina_curso
    ADD CONSTRAINT rel_disciplina_curso_pkey PRIMARY KEY (id);


--
-- Name: rel_usuario_perfil rel_usuario_perfil_id_perf_id_usuario_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_usuario_perfil
    ADD CONSTRAINT rel_usuario_perfil_id_perf_id_usuario_key UNIQUE (id_perf, id_usuario);


--
-- Name: rel_usuario_perfil rel_usuario_perfil_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_usuario_perfil
    ADD CONSTRAINT rel_usuario_perfil_pkey PRIMARY KEY (id);


--
-- Name: rel_usuario_servico rel_usuario_servico_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_usuario_servico
    ADD CONSTRAINT rel_usuario_servico_pkey PRIMARY KEY (id);


--
-- Name: servico servico_cod_serv_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.servico
    ADD CONSTRAINT servico_cod_serv_key UNIQUE (cod_serv);


--
-- Name: servico servico_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.servico
    ADD CONSTRAINT servico_pkey PRIMARY KEY (id);


--
-- Name: usuario usuario_cpf_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cpf_key UNIQUE (cpf);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- Name: aluno aluno_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.aluno
    ADD CONSTRAINT aluno_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- Name: docente docente_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.docente
    ADD CONSTRAINT docente_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- Name: especialidade_docente especialidade_docente_id_docente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.especialidade_docente
    ADD CONSTRAINT especialidade_docente_id_docente_fkey FOREIGN KEY (id_docente) REFERENCES public.docente(id);


--
-- Name: especialidade_funcionario especialidade_funcionario_id_func_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.especialidade_funcionario
    ADD CONSTRAINT especialidade_funcionario_id_func_fkey FOREIGN KEY (id_func) REFERENCES public.funcionario(id);


--
-- Name: funcao_tecnica_docente funcao_tecnica_docente_id_docente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcao_tecnica_docente
    ADD CONSTRAINT funcao_tecnica_docente_id_docente_fkey FOREIGN KEY (id_docente) REFERENCES public.docente(id);


--
-- Name: funcao_tecnica_funcionario funcao_tecnica_funcionario_id_func_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcao_tecnica_funcionario
    ADD CONSTRAINT funcao_tecnica_funcionario_id_func_fkey FOREIGN KEY (id_func) REFERENCES public.funcionario(id);


--
-- Name: funcionario funcionario_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- Name: rel_aluno_curso rel_aluno_curso_id_aluno_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_aluno_curso
    ADD CONSTRAINT rel_aluno_curso_id_aluno_fkey FOREIGN KEY (id_aluno) REFERENCES public.aluno(id);


--
-- Name: rel_aluno_curso rel_aluno_curso_id_curso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_aluno_curso
    ADD CONSTRAINT rel_aluno_curso_id_curso_fkey FOREIGN KEY (id_curso) REFERENCES public.curso(id);


--
-- Name: rel_disciplina_aluno_docente rel_disciplina_aluno_docente_id_aluno_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_disciplina_aluno_docente
    ADD CONSTRAINT rel_disciplina_aluno_docente_id_aluno_fkey FOREIGN KEY (id_aluno) REFERENCES public.aluno(id);


--
-- Name: rel_disciplina_aluno_docente rel_disciplina_aluno_docente_id_disc_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_disciplina_aluno_docente
    ADD CONSTRAINT rel_disciplina_aluno_docente_id_disc_fkey FOREIGN KEY (id_disc) REFERENCES public.disciplina(id);


--
-- Name: rel_disciplina_aluno_docente rel_disciplina_aluno_docente_id_docente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_disciplina_aluno_docente
    ADD CONSTRAINT rel_disciplina_aluno_docente_id_docente_fkey FOREIGN KEY (id_docente) REFERENCES public.docente(id);


--
-- Name: rel_disciplina_curso rel_disciplina_curso_id_curso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_disciplina_curso
    ADD CONSTRAINT rel_disciplina_curso_id_curso_fkey FOREIGN KEY (id_curso) REFERENCES public.curso(id);


--
-- Name: rel_disciplina_curso rel_disciplina_curso_id_disc_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_disciplina_curso
    ADD CONSTRAINT rel_disciplina_curso_id_disc_fkey FOREIGN KEY (id_disc) REFERENCES public.disciplina(id);


--
-- Name: rel_usuario_perfil rel_usuario_perfil_id_perf_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_usuario_perfil
    ADD CONSTRAINT rel_usuario_perfil_id_perf_fkey FOREIGN KEY (id_perf) REFERENCES public.perfil(id);


--
-- Name: rel_usuario_perfil rel_usuario_perfil_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_usuario_perfil
    ADD CONSTRAINT rel_usuario_perfil_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- Name: rel_usuario_servico rel_usuario_servico_id_servico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_usuario_servico
    ADD CONSTRAINT rel_usuario_servico_id_servico_fkey FOREIGN KEY (id_servico) REFERENCES public.servico(id);


--
-- Name: rel_usuario_servico rel_usuario_servico_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.rel_usuario_servico
    ADD CONSTRAINT rel_usuario_servico_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- Name: servico servico_id_perfil_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.servico
    ADD CONSTRAINT servico_id_perfil_fkey FOREIGN KEY (id_perfil) REFERENCES public.perfil(id);


--
-- PostgreSQL database dump complete
--

