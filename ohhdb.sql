--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

-- Started on 2017-03-09 17:18:28

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2156 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 188 (class 1259 OID 16460)
-- Name: event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE event (
    id integer NOT NULL,
    name character varying NOT NULL,
    description character varying,
    location character varying NOT NULL,
    "time" timestamp without time zone NOT NULL,
    disabled boolean,
    total_value numeric
);


ALTER TABLE event OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 16458)
-- Name: event_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE event_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE event_id_seq OWNER TO postgres;

--
-- TOC entry 2157 (class 0 OID 0)
-- Dependencies: 187
-- Name: event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE event_id_seq OWNED BY event.id;


--
-- TOC entry 190 (class 1259 OID 16471)
-- Name: event_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE event_user (
    id integer NOT NULL,
    id_event integer NOT NULL,
    id_user integer NOT NULL,
    confirmed boolean,
    custom_payment boolean,
    custom_payment_value numeric,
    paid numeric
);


ALTER TABLE event_user OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 16469)
-- Name: event_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE event_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE event_user_id_seq OWNER TO postgres;

--
-- TOC entry 2158 (class 0 OID 0)
-- Dependencies: 189
-- Name: event_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE event_user_id_seq OWNED BY event_user.id;


--
-- TOC entry 186 (class 1259 OID 16438)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "user" (
    id integer NOT NULL,
    name character varying NOT NULL,
    email character varying NOT NULL,
    balance numeric,
    disabled boolean
);


ALTER TABLE "user" OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 16436)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_id_seq OWNER TO postgres;

--
-- TOC entry 2159 (class 0 OID 0)
-- Dependencies: 185
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_id_seq OWNED BY "user".id;


--
-- TOC entry 192 (class 1259 OID 16480)
-- Name: user_transaction_historic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE user_transaction_historic (
    id integer NOT NULL,
    id_user integer NOT NULL,
    before_transaction numeric NOT NULL,
    transaction_value numeric NOT NULL,
    after_transaction numeric NOT NULL,
    date timestamp without time zone NOT NULL
);


ALTER TABLE user_transaction_historic OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 16478)
-- Name: user_transaction_historic_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_transaction_historic_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_transaction_historic_id_seq OWNER TO postgres;

--
-- TOC entry 2160 (class 0 OID 0)
-- Dependencies: 191
-- Name: user_transaction_historic_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_transaction_historic_id_seq OWNED BY user_transaction_historic.id;


--
-- TOC entry 2024 (class 2604 OID 16463)
-- Name: event id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY event ALTER COLUMN id SET DEFAULT nextval('event_id_seq'::regclass);


--
-- TOC entry 2025 (class 2604 OID 16474)
-- Name: event_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY event_user ALTER COLUMN id SET DEFAULT nextval('event_user_id_seq'::regclass);


--
-- TOC entry 2023 (class 2604 OID 16441)
-- Name: user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


--
-- TOC entry 2026 (class 2604 OID 16483)
-- Name: user_transaction_historic id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_transaction_historic ALTER COLUMN id SET DEFAULT nextval('user_transaction_historic_id_seq'::regclass);


--
-- TOC entry 2030 (class 2606 OID 16468)
-- Name: event event_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY event
    ADD CONSTRAINT event_pkey PRIMARY KEY (id);


--
-- TOC entry 2028 (class 2606 OID 16446)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 2032 (class 2606 OID 16488)
-- Name: user_transaction_historic user_transaction_historic_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_transaction_historic
    ADD CONSTRAINT user_transaction_historic_pkey PRIMARY KEY (id);


-- Completed on 2017-03-09 17:18:28

--
-- PostgreSQL database dump complete
--

