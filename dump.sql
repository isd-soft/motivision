--
-- PostgreSQL database dump
--

-- Dumped from database version 10.3
-- Dumped by pg_dump version 10.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: activities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.activities (
    id integer NOT NULL,
    name character varying(20) NOT NULL,
    reward integer
);


ALTER TABLE public.activities OWNER TO postgres;

--
-- Name: activities_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.activities_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.activities_id_seq OWNER TO postgres;

--
-- Name: activities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.activities_id_seq OWNED BY public.activities.id;


--
-- Name: character; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."character" (
    id integer NOT NULL,
    player_id bigint NOT NULL,
    head_type integer,
    body_type integer,
    gender character varying(1) NOT NULL,
    points integer DEFAULT 0,
    name character varying(30) NOT NULL,
    team_id integer NOT NULL
);


ALTER TABLE public."character" OWNER TO postgres;

--
-- Name: character_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.character_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.character_id_seq OWNER TO postgres;

--
-- Name: character_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.character_id_seq OWNED BY public."character".id;


--
-- Name: character_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.character_item (
    id integer NOT NULL,
    character_id integer NOT NULL,
    item_id integer
);


ALTER TABLE public.character_item OWNER TO postgres;

--
-- Name: character_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.character_item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.character_item_id_seq OWNER TO postgres;

--
-- Name: character_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.character_item_id_seq OWNED BY public.character_item.id;


--
-- Name: items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.items (
    id integer NOT NULL,
    type character varying(30) NOT NULL,
    image_url character varying(30) NOT NULL,
    price integer
);


ALTER TABLE public.items OWNER TO postgres;

--
-- Name: items_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.items_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.items_id_seq OWNER TO postgres;

--
-- Name: items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.items_id_seq OWNED BY public.items.id;


--
-- Name: last_battle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.last_battle (
    id integer NOT NULL,
    team_id integer NOT NULL,
    enemy_power integer NOT NULL,
    team_power integer NOT NULL
);


ALTER TABLE public.last_battle OWNER TO postgres;

--
-- Name: last_battle_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.last_battle_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.last_battle_id_seq OWNER TO postgres;

--
-- Name: last_battle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.last_battle_id_seq OWNED BY public.last_battle.id;


--
-- Name: player; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.player (
    id integer NOT NULL,
    login character varying(30) NOT NULL,
    password character varying(50) NOT NULL
);


ALTER TABLE public.player OWNER TO postgres;

--
-- Name: player_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.player_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.player_id_seq OWNER TO postgres;

--
-- Name: player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.player_id_seq OWNED BY public.player.id;


--
-- Name: team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team (
    id integer NOT NULL,
    name character varying(30) NOT NULL,
    lider_id integer,
    team_logo character varying(50) NOT NULL,
    battle_frequency integer NOT NULL,
    team_wins integer DEFAULT 0,
    team_loss integer DEFAULT 0
);


ALTER TABLE public.team OWNER TO postgres;

--
-- Name: team_activities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team_activities (
    id integer NOT NULL,
    activity_id integer NOT NULL,
    team_id integer NOT NULL
);


ALTER TABLE public.team_activities OWNER TO postgres;

--
-- Name: team_activities_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_activities_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.team_activities_id_seq OWNER TO postgres;

--
-- Name: team_activities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.team_activities_id_seq OWNED BY public.team_activities.id;


--
-- Name: team_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.team_id_seq OWNER TO postgres;

--
-- Name: team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.team_id_seq OWNED BY public.team.id;


--
-- Name: activities id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activities ALTER COLUMN id SET DEFAULT nextval('public.activities_id_seq'::regclass);


--
-- Name: character id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."character" ALTER COLUMN id SET DEFAULT nextval('public.character_id_seq'::regclass);


--
-- Name: character_item id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.character_item ALTER COLUMN id SET DEFAULT nextval('public.character_item_id_seq'::regclass);


--
-- Name: items id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items ALTER COLUMN id SET DEFAULT nextval('public.items_id_seq'::regclass);


--
-- Name: last_battle id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.last_battle ALTER COLUMN id SET DEFAULT nextval('public.last_battle_id_seq'::regclass);


--
-- Name: player id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player ALTER COLUMN id SET DEFAULT nextval('public.player_id_seq'::regclass);


--
-- Name: team id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team ALTER COLUMN id SET DEFAULT nextval('public.team_id_seq'::regclass);


--
-- Name: team_activities id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_activities ALTER COLUMN id SET DEFAULT nextval('public.team_activities_id_seq'::regclass);


--
-- Data for Name: activities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.activities (id, name, reward) FROM stdin;
6	volleyball game	40
5	swim 1 km	10
1	1 push up	5
3	1 km Run	70
4	1 sit up	2
2	1 pull up	8
7	frontflip	200
8	Backflip	100
9	test	10000
\.


--
-- Data for Name: character; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."character" (id, player_id, head_type, body_type, gender, points, name, team_id) FROM stdin;
17	4	2	1	m	0	testcharacter	3
16	4	2	3	F	0	Power woman	3
20	5	1	2	F	0	Warria	4
\.


--
-- Data for Name: character_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.character_item (id, character_id, item_id) FROM stdin;
\.


--
-- Data for Name: items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.items (id, type, image_url, price) FROM stdin;
1	iron sword	1	333
2	steel sword	2	555
3	diamond sword	3	777
4	iron shield	4	333
5	steel shield	5	555
6	diamond shield	6	777
7	iron armor	7	333
8	steel armor	8	555
9	diamond armor	9	777
10	iron leggins	10	333
11	steel leggins	11	555
12	diamond leggins	12	777
\.


--
-- Data for Name: last_battle; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.last_battle (id, team_id, enemy_power, team_power) FROM stdin;
1	4	1465	0
\.


--
-- Data for Name: player; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.player (id, login, password) FROM stdin;
4	ab	123
5	log2	pas3
6	gmail	123456
7	abgar1332	123123
8	qwerty	xQYMg PKe0vDcWtmLz lFQ==
\.


--
-- Data for Name: team; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team (id, name, lider_id, team_logo, battle_frequency, team_wins, team_loss) FROM stdin;
3	Team1	17	logo	1	0	0
6	testname	\N	logo	1	0	0
4	Supa team	20	Top kek	1	2	6
\.


--
-- Data for Name: team_activities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team_activities (id, activity_id, team_id) FROM stdin;
1	3	3
2	2	3
4	1	3
5	7	3
12	6	6
13	5	6
14	1	6
15	3	6
16	4	6
17	2	6
18	8	6
19	7	6
20	9	4
\.


--
-- Name: activities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.activities_id_seq', 1, false);


--
-- Name: character_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.character_id_seq', 20, true);


--
-- Name: character_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.character_item_id_seq', 2, true);


--
-- Name: items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.items_id_seq', 1, false);


--
-- Name: last_battle_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.last_battle_id_seq', 1, false);


--
-- Name: player_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.player_id_seq', 5, true);


--
-- Name: team_activities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_activities_id_seq', 2, true);


--
-- Name: team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_id_seq', 4, true);


--
-- Name: activities activities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activities
    ADD CONSTRAINT activities_pkey PRIMARY KEY (id);


--
-- Name: character_item character_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.character_item
    ADD CONSTRAINT character_item_pkey PRIMARY KEY (id);


--
-- Name: character character_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."character"
    ADD CONSTRAINT character_pkey PRIMARY KEY (id);


--
-- Name: items items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items
    ADD CONSTRAINT items_pkey PRIMARY KEY (id);


--
-- Name: last_battle last_battle_id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.last_battle
    ADD CONSTRAINT last_battle_id_pk PRIMARY KEY (id);


--
-- Name: player player_id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player
    ADD CONSTRAINT player_id_pk PRIMARY KEY (id);


--
-- Name: player player_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player
    ADD CONSTRAINT player_login_key UNIQUE (login);


--
-- Name: team_activities team_activities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_activities
    ADD CONSTRAINT team_activities_pkey PRIMARY KEY (id);


--
-- Name: team team_id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_id_pk PRIMARY KEY (id);


--
-- Name: team team_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_name_key UNIQUE (name);


--
-- Name: character_name_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX character_name_uindex ON public."character" USING btree (name);


--
-- Name: items_type_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX items_type_uindex ON public.items USING btree (type);


--
-- Name: character character___fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."character"
    ADD CONSTRAINT character___fk FOREIGN KEY (player_id) REFERENCES public.player(id) ON DELETE CASCADE;


--
-- Name: character character___fk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."character"
    ADD CONSTRAINT character___fk_2 FOREIGN KEY (team_id) REFERENCES public.team(id);


--
-- Name: character_item character_item___fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.character_item
    ADD CONSTRAINT character_item___fk FOREIGN KEY (character_id) REFERENCES public."character"(id);


--
-- Name: character_item character_item___fk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.character_item
    ADD CONSTRAINT character_item___fk_2 FOREIGN KEY (item_id) REFERENCES public.items(id);


--
-- Name: last_battle last_battle___fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.last_battle
    ADD CONSTRAINT last_battle___fk FOREIGN KEY (team_id) REFERENCES public.team(id);


--
-- Name: team team___fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team___fk FOREIGN KEY (lider_id) REFERENCES public."character"(id) ON DELETE CASCADE;


--
-- Name: team_activities team_activities___fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_activities
    ADD CONSTRAINT team_activities___fk FOREIGN KEY (team_id) REFERENCES public.team(id);


--
-- Name: team_activities team_activities___fk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_activities
    ADD CONSTRAINT team_activities___fk_2 FOREIGN KEY (activity_id) REFERENCES public.activities(id);


--
-- PostgreSQL database dump complete
--

