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
    money integer DEFAULT 60
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: cards; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cards (id, name, owner, indeck, damage) FROM stdin;
d7d0cb94-2cbf-4f97-8ccf-9933dc5354b8	WaterGoblin	altenhof	f	9
44c82fbc-ef6d-44ab-8c7a-9fb19a0e7c6e	Dragon	altenhof	f	55
2c98cd06-518b-464c-b911-8d787216cddd	WaterSpell	altenhof	f	21
951e886a-0fbf-425d-8df5-af2ee4830d85	Ork	altenhof	f	55
dcd93250-25a7-4dca-85da-cad2789f7198	FireSpell	altenhof	f	23
b2237eca-0271-43bd-87f6-b22f70d42ca4	WaterGoblin	altenhof	f	11
9e8238a4-8a7a-487f-9f7d-a8c97899eb48	Dragon	altenhof	f	70
d60e23cf-2238-4d49-844f-c7589ee5342e	WaterSpell	altenhof	f	22
fc305a7a-36f7-4d30-ad27-462ca0445649	Ork	altenhof	f	40
84d276ee-21ec-4171-a509-c1b88162831c	RegularSpell	altenhof	f	28
70962948-2bf7-44a9-9ded-8c68eeac7793	WaterGoblin	altenhof	f	9
74635fae-8ad3-4295-9139-320ab89c2844	FireSpell	altenhof	f	55
ce6bcaee-47e1-4011-a49e-5a4d7d4245f3	Knight	altenhof	f	21
a6fde738-c65a-4b10-b400-6fef0fdb28ba	FireSpell	altenhof	f	55
a1618f1e-4f4c-4e09-9647-87e16f1edd2d	FireElf	altenhof	f	23
67f9048f-99b8-4ae4-b866-d8008d00c53d	WaterGoblin	altenhof	f	10
aa9999a0-734c-49c6-8f4a-651864b14e62	RegularSpell	altenhof	f	50
d6e9c720-9b5a-40c7-a6b2-bc34752e3463	Knight	altenhof	f	20
02a9c76e-b17d-427f-9240-2dd49b0d3bfd	RegularSpell	altenhof	f	45
2508bf5c-20d7-43b4-8c77-bc677decadef	FireElf	altenhof	f	25
2272ba48-6662-404d-a9a1-41a9bed316d9	WaterGoblin	admin	f	11
3871d45b-b630-4a0d-8bc6-a5fc56b6a043	Dragon	admin	f	70
166c1fd5-4dcb-41a8-91cb-f45dcd57cef3	Knight	admin	f	22
237dbaef-49e3-4c23-b64b-abf5c087b276	WaterSpell	admin	f	40
27051a20-8580-43ff-a473-e986b52f297a	FireElf	admin	f	28
b017ee50-1c14-44e2-bfd6-2c0c5653a37c	WaterGoblin	kienboec	f	11
d04b736a-e874-4137-b191-638e0ff3b4e7	Dragon	kienboec	f	70
88221cfe-1f84-41b9-8152-8e36c6a354de	WaterSpell	kienboec	f	22
1d3f175b-c067-4359-989d-96562bfa382c	Ork	kienboec	f	40
171f6076-4eb5-4a7d-b3f2-2d650cc3d237	RegularSpell	kienboec	f	28
644808c2-f87a-4600-b313-122b02322fd5	WaterGoblin	kienboec	f	9
4a2757d6-b1c3-47ac-b9a3-91deab093531	Dragon	kienboec	f	55
91a6471b-1426-43f6-ad65-6fc473e16f9f	WaterSpell	kienboec	f	21
4ec8b269-0dfa-4f97-809a-2c63fe2a0025	Ork	kienboec	f	55
f8043c23-1534-4487-b66b-238e0c3c39b5	WaterSpell	kienboec	f	23
845f0dc7-37d0-426e-994e-43fc3ac83c08	WaterGoblin	kienboec	f	10
99f8f8dc-e25e-4a95-aa2c-782823f36e2a	Dragon	kienboec	f	50
e85e3976-7c86-4d06-9a80-641c2019a79f	WaterSpell	kienboec	f	20
1cb6ab86-bdb2-47e5-b6e4-68c5ab389334	Ork	kienboec	f	45
dfdd758f-649c-40f9-ba3a-8657f4b3439f	FireSpell	kienboec	f	25
ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8	WaterGoblin	kienboec	f	10
65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5	Dragon	kienboec	f	50
55ef46c4-016c-4168-bc43-6b9b1e86414f	WaterSpell	kienboec	f	20
f3fad0f2-a1af-45df-b80d-2e48825773d9	Ork	kienboec	f	45
8c20639d-6400-4534-bd0f-ae563f11f57a	WaterSpell	kienboec	f	25
\.


--
-- Data for Name: packages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.packages (id, cards, price) FROM stdin;
03ce358f-87a1-4f56-a951-24722ae2c786	2272ba48-6662-404d-a9a1-41a9bed316d9,3871d45b-b630-4a0d-8bc6-a5fc56b6a043,166c1fd5-4dcb-41a8-91cb-f45dcd57cef3,237dbaef-49e3-4c23-b64b-abf5c087b276,27051a20-8580-43ff-a473-e986b52f297a	15
\.


--
-- Data for Name: sessions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sessions (id, token, username, created, expires) FROM stdin;
5077f02b-0cf3-46ed-9aff-016f155ce4ca	Bearer kienboec-mtcgToken	kienboec	2023-12-15 14:43:49.135962+01	2023-12-16 14:43:49.136289+01
54e035b4-493c-4cc4-87f9-9bd691a13554	Bearer altenhof-mtcgToken	altenhof	2023-12-15 14:43:49.212465+01	2023-12-16 14:43:49.212472+01
2917dbb2-acf5-49c7-8634-c88b7bd48ba6	Bearer admin-mtcgToken	admin	2023-12-15 14:43:49.261031+01	2023-12-16 14:43:49.261038+01
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, password, money) FROM stdin;
79861ffa-f2fb-44ce-b9a8-d7f4ac52b522	admin	istrator	60
774ed8d1-bcac-4619-8619-2583799c403f	kienboec	daniel	0
391ecdfd-d141-42f3-b9f3-4d5e352a905f	altenhof	markus	0
\.


--
-- Name: cards cards_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cards
    ADD CONSTRAINT cards_pkey PRIMARY KEY (id);


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

