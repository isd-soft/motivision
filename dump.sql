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
    reward integer,
    team_id integer NOT NULL
);


ALTER TABLE public.activities OWNER TO postgres;

--
-- Name: activities_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.activities_id_seq
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
    item_id integer,
    equipped boolean DEFAULT false
);


ALTER TABLE public.character_item OWNER TO postgres;

--
-- Name: character_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.character_item_id_seq
    
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
    password character varying(50) NOT NULL,
    points integer DEFAULT 0
);


ALTER TABLE public.player OWNER TO postgres;

--
-- Name: player_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.player_id_seq
    
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
    team_loss integer DEFAULT 0,
    locked boolean DEFAULT false NOT NULL
);


ALTER TABLE public.team OWNER TO postgres;

--
-- Name: team_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_id_seq
    
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
-- Data for Name: activities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.activities (id, name, reward, team_id) FROM stdin;
1	test	100	12
3	supaTest	99	12
4	thanks again and	1221	8
\.


--
-- Data for Name: character; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."character" (id, player_id, head_type, body_type, gender, points, name, team_id) FROM stdin;
10	22	2	3	M	17	firstt	9
9	35	2	1	M	2518	Abgar1	8
17	35	1	1	M	0	adcchcucu	8
6	4	1	2	m	0	OurLad	2
2	27	1	3	M	5	njdcfvgbhnj	2
7	34	2	1	F	0	ebshdhd	6
11	22	3	2	M	0	second	10
13	22	1	2	M	0	thirdd	11
18	35	3	3	M	124	frolloe	8
4	32	2	1	M	549	Character	4
\.


--
-- Data for Name: character_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.character_item (id, character_id, item_id, equipped) FROM stdin;
7	9	4	t
1	4	4	f
4	9	12	t
3	4	2	f
2	4	5	f
6	9	1	t
5	9	9	t
9	18	1	t
8	18	7	t
\.


--
-- Data for Name: items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.items (id, type, image_url, price) FROM stdin;
6	diamond_shield	6	777
3	diamond_sword	3	777
8	steel_armor	8	555
12	diamond_leggins	12	777
9	diamond_armor	9	777
1	iron_sword	1	333
7	iron_armor	7	333
2	steel_sword	2	555
4	iron_shield	4	333
11	steel_leggins	11	555
10	iron_leggins	10	333
5	steel_shield	5	555
\.


--
-- Data for Name: last_battle; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.last_battle (id, team_id, enemy_power, team_power) FROM stdin;
\.


--
-- Data for Name: player; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.player (id, login, password, points) FROM stdin;
4	ab	123	0
19	vaseok	JYm8SJkl8FmXTN6WiWKDiA==	0
20	987654	zLkjsnk14xwDbvBxL41Skg==	0
6	alex	1V/HEhSFe8gsmlW5LOBqjQ==	0
22	lliviu	mvJjCtKEJiuYKQd/ipDuiw==	0
23	asdfgh	ZmogiXBxJ12bNP/VvYPRSQ==	0
24	sghgxbdgbf	oHiSJ1/B383o16oTC0BviQ==	0
25	nickname	LyCvC65jCgZ JY2o75YbDg==	0
26	lalala	r0WFlTKXjEbNkXfmACFduQ==	0
27	qwerty123	ATD qO82ZSmjO7uttJvMhA==	0
28	esport12	LyCvC65jCgZ JY2o75YbDg==	0
30	qweqwe	VoFYqqWDwy1kkkxkjwFMYg==	0
31	qwertq	JYm8SJkl8FmXTN6WiWKDiA==	0
32	login1	1kd7qVslmOLQFSwpWuVo1g==	0
33	12341_	rEvhdgrTUaxs/exc 9szkA==	0
34	alloalo	rEvhdgrTUaxs/exc 9szkA==	0
35	abgar1223	xQYMg PKe0vDcWtmLz lFQ==	0
\.


--
-- Data for Name: team; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team (id, name, lider_id, team_logo, battle_frequency, team_wins, team_loss, locked) FROM stdin;
4	sample	4	teamCastle3	7	0	0	f
6	Qwerty	7	teamCastle1	7	0	0	f
8	asdfqw	9	teamCastle1	7	0	0	f
9	oneteam	10	teamCastle1	7	0	0	f
10	twoteam	11	teamCastle2	7	0	0	f
11	threeteam	13	teamCastle3	7	0	0	f
2	qrfgth	2	teamCastle1	14	0	0	f
12	Olvo	\N	logo	2	0	0	f
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

SELECT pg_catalog.setval('public.character_item_id_seq', 6, true);


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
-- Name: activities activities___fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activities
    ADD CONSTRAINT activities___fk FOREIGN KEY (team_id) REFERENCES public.team(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: character character___fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."character"
    ADD CONSTRAINT character___fk FOREIGN KEY (player_id) REFERENCES public.player(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: character character___fk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."character"
    ADD CONSTRAINT character___fk_2 FOREIGN KEY (team_id) REFERENCES public.team(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: character_item character_item___fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.character_item
    ADD CONSTRAINT character_item___fk FOREIGN KEY (character_id) REFERENCES public."character"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: character_item character_item___fk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.character_item
    ADD CONSTRAINT character_item___fk_2 FOREIGN KEY (item_id) REFERENCES public.items(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: last_battle last_battle___fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.last_battle
    ADD CONSTRAINT last_battle___fk FOREIGN KEY (team_id) REFERENCES public.team(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: team team___fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team___fk FOREIGN KEY (lider_id) REFERENCES public."character"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

