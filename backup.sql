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
    result text
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
-- Name: tradings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tradings (
    id text NOT NULL,
    cardid text,
    minimumdamage integer,
    type text
);


ALTER TABLE public.tradings OWNER TO postgres;

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
845f0dc7-37d0-426e-994e-43fc3ac83c08	WaterGoblin	kienboec	t	10
99f8f8dc-e25e-4a95-aa2c-782823f36e2a	Dragon	kienboec	t	50
e85e3976-7c86-4d06-9a80-641c2019a79f	WaterSpell	kienboec	t	20
1cb6ab86-bdb2-47e5-b6e4-68c5ab389334	Ork	kienboec	t	45
644808c2-f87a-4600-b313-122b02322fd5	WaterGoblin	altenhof	t	9
4a2757d6-b1c3-47ac-b9a3-91deab093531	Dragon	altenhof	t	55
91a6471b-1426-43f6-ad65-6fc473e16f9f	WaterSpell	altenhof	t	21
4ec8b269-0dfa-4f97-809a-2c63fe2a0025	Ork	altenhof	t	55
d7d0cb94-2cbf-4f97-8ccf-9933dc5354b8	WaterGoblin	kienboec	f	9
44c82fbc-ef6d-44ab-8c7a-9fb19a0e7c6e	Dragon	kienboec	f	55
2c98cd06-518b-464c-b911-8d787216cddd	WaterSpell	kienboec	f	21
951e886a-0fbf-425d-8df5-af2ee4830d85	Ork	kienboec	f	55
dcd93250-25a7-4dca-85da-cad2789f7198	FireSpell	kienboec	f	23
b017ee50-1c14-44e2-bfd6-2c0c5653a37c	WaterGoblin	kienboec	f	11
d04b736a-e874-4137-b191-638e0ff3b4e7	Dragon	kienboec	f	70
88221cfe-1f84-41b9-8152-8e36c6a354de	WaterSpell	kienboec	f	22
1d3f175b-c067-4359-989d-96562bfa382c	Ork	kienboec	f	40
171f6076-4eb5-4a7d-b3f2-2d650cc3d237	RegularSpell	kienboec	f	28
b2237eca-0271-43bd-87f6-b22f70d42ca4	WaterGoblin	kienboec	f	11
9e8238a4-8a7a-487f-9f7d-a8c97899eb48	Dragon	kienboec	f	70
d60e23cf-2238-4d49-844f-c7589ee5342e	WaterSpell	kienboec	f	22
fc305a7a-36f7-4d30-ad27-462ca0445649	Ork	kienboec	f	40
84d276ee-21ec-4171-a509-c1b88162831c	RegularSpell	kienboec	f	28
ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8	WaterGoblin	altenhof	f	10
65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5	Dragon	altenhof	f	50
55ef46c4-016c-4168-bc43-6b9b1e86414f	WaterSpell	altenhof	f	20
f3fad0f2-a1af-45df-b80d-2e48825773d9	Ork	altenhof	f	45
8c20639d-6400-4534-bd0f-ae563f11f57a	WaterSpell	altenhof	f	25
f8043c23-1534-4487-b66b-238e0c3c39b5	WaterSpell	kienboec	f	23
dfdd758f-649c-40f9-ba3a-8657f4b3439f	FireSpell	altenhof	f	25
\.


--
-- Data for Name: history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.history (id, type, users, "time", result) FROM stdin;
1fbadba1-c1a5-48f8-8c04-d553fe514438	buy	kienboec	2024-01-17 18:30:19.026148+01	User kienboec bought a package for 15 coins
677f7df4-2ae9-4157-8f52-4cabc0fc07da	buy	altenhof	2024-01-17 18:30:19.234024+01	User altenhof bought a package for 15 coins
36a99bbd-5167-4fe5-84bd-a56a79528b4a	buy	kienboec	2024-01-17 18:30:20.649752+01	User kienboec bought a package for 15 coins
59b14a2a-6e3c-4f0a-a83e-612023d5f343	buy	kienboec	2024-01-17 18:30:20.802974+01	User kienboec bought a package for 15 coins
eb17cdd2-a299-4289-9171-5298de2a9015	buy	kienboec	2024-01-17 18:30:20.912561+01	User kienboec bought a package for 15 coins
3b199662-9302-495f-bda5-c3edc70a280d	buy	altenhof	2024-01-17 18:30:21.514192+01	User altenhof bought a package for 15 coins
556936b0-2c02-4f04-96db-e8209597d8a1	battle	kienboec,altenhof	2024-01-17 18:30:28.70037+01	Battle between kienboec and altenhof\nCards of kienboec:\nWaterGoblin\nDragon\nWaterSpell\nOrk\nCards of altenhof:\nWaterGoblin\nDragon\nWaterSpell\nOrk\nRound 1\nCard Dragon vs Dragon\nCard Dragon from kienboec won\nNumber of cards of kienboec: 4\nNumber of cards of altenhof: 3\nRound 2\nCard Dragon vs WaterSpell\nCard Dragon from kienboec won\nNumber of cards of kienboec: 4\nNumber of cards of altenhof: 2\nRound 3\nCard WaterGoblin vs WaterGoblin\nCard WaterGoblin from kienboec won\nNumber of cards of kienboec: 4\nNumber of cards of altenhof: 1\nRound 4\nCard WaterSpell vs Ork\nCard Ork from kienboec won\nNumber of cards of kienboec: 3\nNumber of cards of altenhof: 1\nRound 5\nCard WaterGoblin vs Ork\nCard Ork from kienboec won\nNumber of cards of kienboec: 2\nNumber of cards of altenhof: 1\nRound 6\nCard Dragon vs Ork\nCard Ork from kienboec won\nNumber of cards of kienboec: 1\nNumber of cards of altenhof: 1\nRound 7\nCard Ork vs Ork\nCard Ork from kienboec won\nNumber of cards of kienboec: 1\nNumber of cards of altenhof: 0\nWinner is kienboec\n
41ffa8a7-4f5f-41be-a5c7-c6d05adb7807	trade	altenhof,kienboec	2024-01-17 18:30:34.86202+01	User altenhof traded card f8043c23-1534-4487-b66b-238e0c3c39b5 for card dfdd758f-649c-40f9-ba3a-8657f4b3439f
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
\.


--
-- Data for Name: sessions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sessions (id, token, username, created, expires) FROM stdin;
72150c0b-f32a-449b-ba82-f2b676633316	Bearer kienboec-mtcgToken	kienboec	2024-01-17 18:30:17.488436+01	2024-01-18 18:30:17.488447+01
f11bbf78-a60c-4eaa-9c8e-4c151b920dad	Bearer altenhof-mtcgToken	altenhof	2024-01-17 18:30:17.542885+01	2024-01-18 18:30:17.542892+01
41f723d4-d544-42cd-9921-3ea14ae9916a	Bearer admin-mtcgToken	admin	2024-01-17 18:30:17.578615+01	2024-01-18 18:30:17.578624+01
\.


--
-- Data for Name: tradings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tradings (id, cardid, minimumdamage, type) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, password, money, name, bio, image, wins, losses, elo) FROM stdin;
7fe77202-3891-42c1-8485-975d4d96f64b	admin	istrator	60	\N	\N	\N	0	0	1500
1ad5b128-3256-491d-9e0a-531b22fec3ed	kienboec	daniel	0	Kienboeck	me playin...	:-)	1	0	1503
79bdaae5-15d2-47c7-a9a9-c5b3365f39ed	altenhof	markus	30	Altenhofer	me codin...	:-D	0	1	1495
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
-- Name: tradings tradings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tradings
    ADD CONSTRAINT tradings_pkey PRIMARY KEY (id);


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
-- Name: tradings tradings_cardid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tradings
    ADD CONSTRAINT tradings_cardid_fkey FOREIGN KEY (cardid) REFERENCES public.cards(id);


--
-- PostgreSQL database dump complete
--

