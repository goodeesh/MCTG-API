--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Debian 16.1-1.pgdg120+1)
-- Dumped by pg_dump version 16.1 (Debian 16.1-1.pgdg120+1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cards; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cards (
    id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    owner character varying(255) DEFAULT 'admin'::character varying,
    indeck boolean DEFAULT false,
    damage integer NOT NULL
);


ALTER TABLE public.cards OWNER TO postgres;

--
-- Name: history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.history (
    id character varying(255) NOT NULL,
    type character varying(255),
    users character varying(255),
    "time" character varying(255),
    result character varying(255)
);


ALTER TABLE public.history OWNER TO postgres;

--
-- Name: lobby; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lobby (
    date character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    card character varying(255),
    ready boolean DEFAULT false
);


ALTER TABLE public.lobby OWNER TO postgres;

--
-- Name: packages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.packages (
    id character varying(255) NOT NULL,
    cards text NOT NULL,
    price integer DEFAULT 15
);


ALTER TABLE public.packages OWNER TO postgres;

--
-- Name: sessions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sessions (
    id character varying(255) NOT NULL,
    token character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    created character varying(255) NOT NULL,
    expires character varying(255) NOT NULL
);


ALTER TABLE public.sessions OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    money integer DEFAULT 60,
    name character varying(255),
    bio text,
    image text,
    wins integer DEFAULT 0,
    losses integer DEFAULT 0,
    elo integer DEFAULT 1500
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: cards; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cards (id, name, owner, indeck, damage) FROM stdin;
ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8	WaterGoblin	kienboec	f	10
65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5	Dragon	kienboec	f	50
55ef46c4-016c-4168-bc43-6b9b1e86414f	WaterSpell	kienboec	f	20
f3fad0f2-a1af-45df-b80d-2e48825773d9	Ork	kienboec	f	45
8c20639d-6400-4534-bd0f-ae563f11f57a	WaterSpell	kienboec	f	25
b2237eca-0271-43bd-87f6-b22f70d42ca4	WaterGoblin	kienboec	f	11
9e8238a4-8a7a-487f-9f7d-a8c97899eb48	Dragon	kienboec	f	70
d60e23cf-2238-4d49-844f-c7589ee5342e	WaterSpell	kienboec	f	22
fc305a7a-36f7-4d30-ad27-462ca0445649	Ork	kienboec	f	40
dfdd758f-649c-40f9-ba3a-8657f4b3439f	FireSpell	kienboec	f	25
845f0dc7-37d0-426e-994e-43fc3ac83c08	WaterGoblin	kienboec	t	10
99f8f8dc-e25e-4a95-aa2c-782823f36e2a	Dragon	kienboec	t	50
e85e3976-7c86-4d06-9a80-641c2019a79f	WaterSpell	kienboec	t	20
1cb6ab86-bdb2-47e5-b6e4-68c5ab389334	Ork	kienboec	t	45
84d276ee-21ec-4171-a509-c1b88162831c	RegularSpell	kienboec	f	28
d7d0cb94-2cbf-4f97-8ccf-9933dc5354b8	WaterGoblin	altenhof	f	9
44c82fbc-ef6d-44ab-8c7a-9fb19a0e7c6e	Dragon	altenhof	f	55
2c98cd06-518b-464c-b911-8d787216cddd	WaterSpell	altenhof	f	21
951e886a-0fbf-425d-8df5-af2ee4830d85	Ork	altenhof	f	55
dcd93250-25a7-4dca-85da-cad2789f7198	FireSpell	altenhof	f	23
644808c2-f87a-4600-b313-122b02322fd5	WaterGoblin	altenhof	t	9
4a2757d6-b1c3-47ac-b9a3-91deab093531	Dragon	altenhof	t	55
91a6471b-1426-43f6-ad65-6fc473e16f9f	WaterSpell	altenhof	t	21
67f9048f-99b8-4ae4-b866-d8008d00c53d	WaterGoblin	altenhof	f	10
aa9999a0-734c-49c6-8f4a-651864b14e62	RegularSpell	altenhof	f	50
d6e9c720-9b5a-40c7-a6b2-bc34752e3463	Knight	altenhof	f	20
02a9c76e-b17d-427f-9240-2dd49b0d3bfd	RegularSpell	altenhof	f	45
2508bf5c-20d7-43b4-8c77-bc677decadef	FireElf	altenhof	f	25
4ec8b269-0dfa-4f97-809a-2c63fe2a0025	Ork	altenhof	t	55
f8043c23-1534-4487-b66b-238e0c3c39b5	WaterSpell	altenhof	f	23
70962948-2bf7-44a9-9ded-8c68eeac7793	WaterGoblin	admin	f	9
74635fae-8ad3-4295-9139-320ab89c2844	FireSpell	admin	f	55
ce6bcaee-47e1-4011-a49e-5a4d7d4245f3	Knight	admin	f	21
b017ee50-1c14-44e2-bfd6-2c0c5653a37c	WaterGoblin	admin	f	11
d04b736a-e874-4137-b191-638e0ff3b4e7	Dragon	admin	f	70
88221cfe-1f84-41b9-8152-8e36c6a354de	WaterSpell	admin	f	22
1d3f175b-c067-4359-989d-96562bfa382c	Ork	admin	f	40
171f6076-4eb5-4a7d-b3f2-2d650cc3d237	RegularSpell	admin	f	28
a6fde738-c65a-4b10-b400-6fef0fdb28ba	FireSpell	admin	f	55
a1618f1e-4f4c-4e09-9647-87e16f1edd2d	FireElf	admin	f	23
2272ba48-6662-404d-a9a1-41a9bed316d9	WaterGoblin	admin	f	11
3871d45b-b630-4a0d-8bc6-a5fc56b6a043	Dragon	admin	f	70
166c1fd5-4dcb-41a8-91cb-f45dcd57cef3	Knight	admin	f	22
237dbaef-49e3-4c23-b64b-abf5c087b276	WaterSpell	admin	f	40
27051a20-8580-43ff-a473-e986b52f297a	FireElf	admin	f	28
\.


--
-- Data for Name: history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.history (id, type, users, "time", result) FROM stdin;
f5223bf9-d397-4e35-b2c1-6fc15538d147	battle	kienboec,altenhof	2024-01-03 19:48:03.262067+01	kienboec
\.


--
-- Data for Name: lobby; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lobby (date, username, card, ready) FROM stdin;
\.


--
-- Data for Name: packages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.packages (id, cards, price) FROM stdin;
a5e637cf-b0ca-49b0-95fc-c06f736834df	845f0dc7-37d0-426e-994e-43fc3ac83c08,99f8f8dc-e25e-4a95-aa2c-782823f36e2a,e85e3976-7c86-4d06-9a80-641c2019a79f,1cb6ab86-bdb2-47e5-b6e4-68c5ab389334,dfdd758f-649c-40f9-ba3a-8657f4b3439f	15
1c84485a-7b59-4b43-a924-8a41a1a4bfdd	b017ee50-1c14-44e2-bfd6-2c0c5653a37c,d04b736a-e874-4137-b191-638e0ff3b4e7,88221cfe-1f84-41b9-8152-8e36c6a354de,1d3f175b-c067-4359-989d-96562bfa382c,171f6076-4eb5-4a7d-b3f2-2d650cc3d237	15
e8972734-9c34-4e3f-8e70-94e5f2771dc0	67f9048f-99b8-4ae4-b866-d8008d00c53d,aa9999a0-734c-49c6-8f4a-651864b14e62,d6e9c720-9b5a-40c7-a6b2-bc34752e3463,02a9c76e-b17d-427f-9240-2dd49b0d3bfd,2508bf5c-20d7-43b4-8c77-bc677decadef	15
58243552-28b0-4322-8144-b527ab0fae91	70962948-2bf7-44a9-9ded-8c68eeac7793,74635fae-8ad3-4295-9139-320ab89c2844,ce6bcaee-47e1-4011-a49e-5a4d7d4245f3,a6fde738-c65a-4b10-b400-6fef0fdb28ba,a1618f1e-4f4c-4e09-9647-87e16f1edd2d	15
8eeed532-15e4-415d-9778-8d5f3deab68e	2272ba48-6662-404d-a9a1-41a9bed316d9,3871d45b-b630-4a0d-8bc6-a5fc56b6a043,166c1fd5-4dcb-41a8-91cb-f45dcd57cef3,237dbaef-49e3-4c23-b64b-abf5c087b276,27051a20-8580-43ff-a473-e986b52f297a	15
\.


--
-- Data for Name: sessions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sessions (id, token, username, created, expires) FROM stdin;
ae55753b-ae72-48b3-8393-a761d320303e	Bearer kienboec-mtcgToken	kienboec	2024-01-03 19:47:09.056376+01	2024-01-04 19:47:09.056382+01
184a3ae8-63ed-45fc-99c1-e41c616759c2	Bearer altenhof-mtcgToken	altenhof	2024-01-03 19:47:09.127197+01	2024-01-04 19:47:09.1272+01
3733e9a2-5ad6-4801-9800-02a9b9513ff7	Bearer admin-mtcgToken	admin	2024-01-03 19:47:09.187394+01	2024-01-04 19:47:09.187398+01
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, password, money, name, bio, image, wins, losses, elo) FROM stdin;
11d5ba61-fd86-4a64-b928-b3f5f5a1167e	admin	istrator	60	\N	\N	\N	0	0	1500
af4f9575-651c-4ed6-80eb-cff2aa284957	kienboec	daniel	0	Kienboeck	me playin...	:-)	1	0	1503
1e8edb03-96b9-404c-a22f-1e46f8dd270d	altenhof	markus	0	Altenhofer	me codin...	:-D	0	1	1495
\.


--
-- Name: cards cards_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cards
    ADD CONSTRAINT cards_pkey PRIMARY KEY (id);


--
-- Name: history history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT history_pkey PRIMARY KEY (id);


--
-- Name: packages packages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.packages
    ADD CONSTRAINT packages_pkey PRIMARY KEY (id);


--
-- Name: sessions sessions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_pkey PRIMARY KEY (id);


--
-- Name: sessions sessions_token_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_token_key UNIQUE (token);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: cards cards_ownerUsername_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cards
    ADD CONSTRAINT "cards_ownerUsername_fkey" FOREIGN KEY (owner) REFERENCES public.users(username);


--
-- Name: sessions sessions_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_username_fkey FOREIGN KEY (username) REFERENCES public.users(username);


--
-- PostgreSQL database dump complete
--

