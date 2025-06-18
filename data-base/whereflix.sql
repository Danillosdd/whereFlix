--
-- PostgreSQL database dump
--

-- Dumped from database version 15.13 (Ubuntu 15.13-1.pgdg24.04+1)
-- Dumped by pg_dump version 15.13 (Ubuntu 15.13-1.pgdg24.04+1)

-- Started on 2025-06-18 09:43:51 -03

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

DROP DATABASE IF EXISTS whereflix;
--
-- TOC entry 3444 (class 1262 OID 16669)
-- Name: whereflix; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE whereflix WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'pt_BR.UTF-8';


ALTER DATABASE whereflix OWNER TO postgres;

\connect whereflix

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
-- TOC entry 5 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3445 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 17158)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    id integer NOT NULL,
    nome character varying(255)
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 17190)
-- Name: categoria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categoria_seq OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 17163)
-- Name: filme; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.filme (
    id integer NOT NULL,
    ano integer,
    avaliacao double precision,
    duracao integer,
    foto character varying(255),
    sinopse character varying(255),
    titulo character varying(255),
    categoria_id integer,
    qualidade_id integer,
    tipo_id integer
);


ALTER TABLE public.filme OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17191)
-- Name: filme_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.filme_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.filme_seq OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 17170)
-- Name: filme_streamings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.filme_streamings (
    filme_id integer NOT NULL,
    streamings_id integer NOT NULL
);


ALTER TABLE public.filme_streamings OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 17173)
-- Name: qualidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.qualidade (
    id integer NOT NULL,
    nome character varying(255)
);


ALTER TABLE public.qualidade OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 17192)
-- Name: qualidade_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.qualidade_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.qualidade_seq OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 17178)
-- Name: streaming; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.streaming (
    id integer NOT NULL,
    foto character varying(255),
    nome character varying(255)
);


ALTER TABLE public.streaming OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 17193)
-- Name: streaming_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.streaming_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.streaming_seq OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17185)
-- Name: tipo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo (
    id integer NOT NULL,
    nome character varying(255)
);


ALTER TABLE public.tipo OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 17194)
-- Name: tipo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipo_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tipo_seq OWNER TO postgres;

--
-- TOC entry 3428 (class 0 OID 17158)
-- Dependencies: 214
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoria (id, nome) VALUES (1, 'Ação');
INSERT INTO public.categoria (id, nome) VALUES (2, 'Animação');
INSERT INTO public.categoria (id, nome) VALUES (3, 'Aventura');
INSERT INTO public.categoria (id, nome) VALUES (4, 'Biografia');
INSERT INTO public.categoria (id, nome) VALUES (5, 'Comédia');
INSERT INTO public.categoria (id, nome) VALUES (6, 'Drama');
INSERT INTO public.categoria (id, nome) VALUES (7, 'Documentário');
INSERT INTO public.categoria (id, nome) VALUES (8, 'Fantasia');
INSERT INTO public.categoria (id, nome) VALUES (9, 'Fatos Reais');
INSERT INTO public.categoria (id, nome) VALUES (10, 'Ficção Científica');
INSERT INTO public.categoria (id, nome) VALUES (11, 'Romance');
INSERT INTO public.categoria (id, nome) VALUES (12, 'Super-heróis');
INSERT INTO public.categoria (id, nome) VALUES (13, 'Suspense');
INSERT INTO public.categoria (id, nome) VALUES (14, 'Terror');


--
-- TOC entry 3429 (class 0 OID 17163)
-- Dependencies: 215
-- Data for Name: filme; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (1, 2022, 8.5, 137, 'upcoming-1.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'The Northman', 1, 3, 1);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (2, 2024, 8.3, 126, 'upcoming-2.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Doctor Strange in the Multiverse of Madness', 8, 2, 1);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (6, 2019, 5.9, 104, 'movie-2.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Morbius', 14, 3, 1);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (7, 2023, 6.8, 106, 'movie-3.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'The Adam Project', 13, 2, 1);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (8, 2021, 7.7, 115, 'movie-4.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Free Guy', 5, 3, 1);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (11, 2020, 6.5, 127, 'movie-7.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Death on the Nile', 8, 2, 1);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (12, 2021, 5.3, 131, 'movie-8.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'The King''s Man', 8, 3, 1);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (13, 2024, 5.7, 47, 'series-1.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Moon Knight', 14, 3, 2);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (14, 2023, 7.4, 59, 'series-2.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Halo', 10, 2, 2);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (18, 2021, 3.8, 112, 'show-eminem.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'The eminem Show', 7, 1, 3);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (19, 2022, 4.2, 78, 'show-felipe.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Felipe Araújo', 8, 2, 3);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (20, 2021, 5.2, 109, 'show-rock.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Rock & Roll', 7, 1, 3);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (15, 2025, 9.6, 51, 'series-3.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Vikings: Valhalla', 7, 3, 2);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (3, 2025, 8.2, 126, 'upcoming-3.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Memory', 1, 1, 1);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (9, 2024, 9.1, 176, 'movie-5.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'The Batman', 12, 3, 1);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (4, 2020, 6.6, 102, 'upcoming-4.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'The Unbearable Weight of Massive Talent', 3, 2, 1);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (17, 2024, 5.4, 107, 'michael.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Michael Jackson Number Ones', 7, 3, 3);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (16, 2021, 7.8, 70, 'series-4.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'La Casa de Papel', 1, 3, 2);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (10, 2022, 8.1, 116, 'movie-6.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Uncharted', 3, 3, 1);
INSERT INTO public.filme (id, ano, avaliacao, duracao, foto, sinopse, titulo, categoria_id, qualidade_id, tipo_id) VALUES (5, 2025, 8.8, 56, 'movie-1.png', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Sonic the Hedgehog 2', 2, 2, 1);


--
-- TOC entry 3430 (class 0 OID 17170)
-- Dependencies: 216
-- Data for Name: filme_streamings; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (1, 3);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (1, 4);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (1, 7);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (2, 1);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (2, 8);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (6, 4);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (6, 6);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (6, 8);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (7, 1);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (7, 4);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (7, 6);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (8, 7);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (8, 8);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (11, 1);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (11, 9);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (12, 4);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (12, 7);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (13, 3);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (13, 7);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (13, 8);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (14, 2);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (14, 8);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (14, 9);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (18, 3);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (18, 4);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (19, 4);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (19, 7);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (20, 3);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (20, 6);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (20, 8);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (3, 4);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (3, 6);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (9, 3);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (9, 4);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (9, 7);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (9, 9);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (15, 1);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (15, 3);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (15, 7);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (17, 1);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (17, 8);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (17, 9);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (4, 1);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (4, 3);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (4, 4);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (16, 7);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (10, 1);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (10, 6);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (10, 7);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (5, 2);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (5, 7);
INSERT INTO public.filme_streamings (filme_id, streamings_id) VALUES (5, 9);


--
-- TOC entry 3431 (class 0 OID 17173)
-- Dependencies: 217
-- Data for Name: qualidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.qualidade (id, nome) VALUES (1, 'HD');
INSERT INTO public.qualidade (id, nome) VALUES (2, 'Full HD');
INSERT INTO public.qualidade (id, nome) VALUES (3, '4K');


--
-- TOC entry 3432 (class 0 OID 17178)
-- Dependencies: 218
-- Data for Name: streaming; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.streaming (id, foto, nome) VALUES (1, 'apple-tv.png', 'Apple TV');
INSERT INTO public.streaming (id, foto, nome) VALUES (2, 'crunchyroll.png', 'Crunchyroll');
INSERT INTO public.streaming (id, foto, nome) VALUES (3, 'disney+.png', 'Disney +');
INSERT INTO public.streaming (id, foto, nome) VALUES (4, 'globoplay.png', 'Globoplay');
INSERT INTO public.streaming (id, foto, nome) VALUES (5, 'hbo-max.jpg', 'Hbo Max');
INSERT INTO public.streaming (id, foto, nome) VALUES (6, 'hulu.png', 'Hulu');
INSERT INTO public.streaming (id, foto, nome) VALUES (7, 'netflix.png', 'Netflix');
INSERT INTO public.streaming (id, foto, nome) VALUES (8, 'paramount +.png', 'Paramount+');
INSERT INTO public.streaming (id, foto, nome) VALUES (9, 'prime-video.png', 'Prime Video');


--
-- TOC entry 3433 (class 0 OID 17185)
-- Dependencies: 219
-- Data for Name: tipo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo (id, nome) VALUES (1, 'Filme');
INSERT INTO public.tipo (id, nome) VALUES (2, 'Série');
INSERT INTO public.tipo (id, nome) VALUES (3, 'Show');


--
-- TOC entry 3447 (class 0 OID 0)
-- Dependencies: 220
-- Name: categoria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_seq', 51, true);


--
-- TOC entry 3448 (class 0 OID 0)
-- Dependencies: 221
-- Name: filme_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.filme_seq', 151, true);


--
-- TOC entry 3449 (class 0 OID 0)
-- Dependencies: 222
-- Name: qualidade_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.qualidade_seq', 51, true);


--
-- TOC entry 3450 (class 0 OID 0)
-- Dependencies: 223
-- Name: streaming_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.streaming_seq', 301, true);


--
-- TOC entry 3451 (class 0 OID 0)
-- Dependencies: 224
-- Name: tipo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_seq', 51, true);


--
-- TOC entry 3272 (class 2606 OID 17162)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);


--
-- TOC entry 3274 (class 2606 OID 17169)
-- Name: filme filme_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.filme
    ADD CONSTRAINT filme_pkey PRIMARY KEY (id);


--
-- TOC entry 3276 (class 2606 OID 17177)
-- Name: qualidade qualidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.qualidade
    ADD CONSTRAINT qualidade_pkey PRIMARY KEY (id);


--
-- TOC entry 3278 (class 2606 OID 17184)
-- Name: streaming streaming_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.streaming
    ADD CONSTRAINT streaming_pkey PRIMARY KEY (id);


--
-- TOC entry 3280 (class 2606 OID 17189)
-- Name: tipo tipo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo
    ADD CONSTRAINT tipo_pkey PRIMARY KEY (id);


--
-- TOC entry 3284 (class 2606 OID 17215)
-- Name: filme_streamings fk8arwdbgqcqifhlh1cq4l9fm02; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.filme_streamings
    ADD CONSTRAINT fk8arwdbgqcqifhlh1cq4l9fm02 FOREIGN KEY (filme_id) REFERENCES public.filme(id);


--
-- TOC entry 3281 (class 2606 OID 17200)
-- Name: filme fkaqjobppnl09b2nld7uqcy18o1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.filme
    ADD CONSTRAINT fkaqjobppnl09b2nld7uqcy18o1 FOREIGN KEY (qualidade_id) REFERENCES public.qualidade(id);


--
-- TOC entry 3282 (class 2606 OID 17195)
-- Name: filme fkcx54u8trn3dg3ekmn2pg4xbv9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.filme
    ADD CONSTRAINT fkcx54u8trn3dg3ekmn2pg4xbv9 FOREIGN KEY (categoria_id) REFERENCES public.categoria(id);


--
-- TOC entry 3283 (class 2606 OID 17205)
-- Name: filme fke9t799bn0w77q5sc5pat85n9x; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.filme
    ADD CONSTRAINT fke9t799bn0w77q5sc5pat85n9x FOREIGN KEY (tipo_id) REFERENCES public.tipo(id);


--
-- TOC entry 3285 (class 2606 OID 17210)
-- Name: filme_streamings fkiaeho8sb1ty5qt2yh8n2oc8u2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.filme_streamings
    ADD CONSTRAINT fkiaeho8sb1ty5qt2yh8n2oc8u2 FOREIGN KEY (streamings_id) REFERENCES public.streaming(id);


--
-- TOC entry 3446 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2025-06-18 09:43:51 -03

--
-- PostgreSQL database dump complete
--

