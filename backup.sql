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
845f0dc7-37d0-426e-994e-43fc3ac83c08	WaterGoblin	admin	f	10
99f8f8dc-e25e-4a95-aa2c-782823f36e2a	Dragon	admin	f	50
e85e3976-7c86-4d06-9a80-641c2019a79f	WaterSpell	admin	f	20
1cb6ab86-bdb2-47e5-b6e4-68c5ab389334	Ork	admin	f	45
dfdd758f-649c-40f9-ba3a-8657f4b3439f	FireSpell	admin	f	25
b2237eca-0271-43bd-87f6-b22f70d42ca4	WaterGoblin	admin	f	11
9e8238a4-8a7a-487f-9f7d-a8c97899eb48	Dragon	admin	f	70
d60e23cf-2238-4d49-844f-c7589ee5342e	WaterSpell	admin	f	22
fc305a7a-36f7-4d30-ad27-462ca0445649	Ork	admin	f	40
84d276ee-21ec-4171-a509-c1b88162831c	RegularSpell	admin	f	28
b017ee50-1c14-44e2-bfd6-2c0c5653a37c	WaterGoblin	Adrian	f	11
d04b736a-e874-4137-b191-638e0ff3b4e7	Dragon	Adrian	f	70
88221cfe-1f84-41b9-8152-8e36c6a354de	WaterSpell	Adrian	f	22
1d3f175b-c067-4359-989d-96562bfa382c	Ork	Adrian	f	40
171f6076-4eb5-4a7d-b3f2-2d650cc3d237	RegularSpell	Adrian	f	28
d7d0cb94-2cbf-4f97-8ccf-9933dc5354b8	WaterGoblin	Adrian	f	9
44c82fbc-ef6d-44ab-8c7a-9fb19a0e7c6e	Dragon	Adrian	f	55
2c98cd06-518b-464c-b911-8d787216cddd	WaterSpell	Adrian	f	21
951e886a-0fbf-425d-8df5-af2ee4830d85	Ork	Adrian	f	55
dcd93250-25a7-4dca-85da-cad2789f7198	FireSpell	Adrian	f	23
ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8	WaterGoblin	Adrian	f	10
65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5	Dragon	Adrian	f	50
55ef46c4-016c-4168-bc43-6b9b1e86414f	WaterSpell	Adrian	f	20
f3fad0f2-a1af-45df-b80d-2e48825773d9	Ork	Adrian	f	45
8c20639d-6400-4534-bd0f-ae563f11f57a	WaterSpell	Adrian	f	25
644808c2-f87a-4600-b313-122b02322fd5	WaterGoblin	Adrian	f	9
4a2757d6-b1c3-47ac-b9a3-91deab093531	Dragon	Adrian	f	55
91a6471b-1426-43f6-ad65-6fc473e16f9f	WaterSpell	Adrian	f	21
4ec8b269-0dfa-4f97-809a-2c63fe2a0025	Ork	Adrian	f	55
f8043c23-1534-4487-b66b-238e0c3c39b5	WaterSpell	Adrian	f	23
\.


--
-- Data for Name: packages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.packages (id, cards, price) FROM stdin;
9d2be12b-d346-4eea-a122-a92ed7e6eb88	845f0dc7-37d0-426e-994e-43fc3ac83c08,99f8f8dc-e25e-4a95-aa2c-782823f36e2a,e85e3976-7c86-4d06-9a80-641c2019a79f,1cb6ab86-bdb2-47e5-b6e4-68c5ab389334,dfdd758f-649c-40f9-ba3a-8657f4b3439f	15
bb8135f0-4cf5-4ae6-8ea4-e99aff5877a4	b2237eca-0271-43bd-87f6-b22f70d42ca4,9e8238a4-8a7a-487f-9f7d-a8c97899eb48,d60e23cf-2238-4d49-844f-c7589ee5342e,fc305a7a-36f7-4d30-ad27-462ca0445649,84d276ee-21ec-4171-a509-c1b88162831c	15
\.


--
-- Data for Name: sessions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sessions (id, token, username, created, expires) FROM stdin;
cc91442e-c59b-4a6e-9c6e-a8caf650fb17	Bearer kienboec-mtcgToken	kienboec	2023-12-12 23:13:24.484625+01	2023-12-13 23:13:24.485003+01
9d4a73b5-4546-4c7a-b79a-2b796834cab7	Bearer altenhof-mtcgToken	altenhof	2023-12-12 23:13:24.567062+01	2023-12-13 23:13:24.56707+01
b2235f79-29bc-4c28-bece-e66f70d46bc0	Bearer admin-mtcgToken	admin	2023-12-12 23:13:24.628006+01	2023-12-13 23:13:24.628014+01
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, password, money) FROM stdin;
e19b16e6-c1d1-44c2-8f54-2d594ebd3c2a	Sanchez	123456789	60
87f33491-ec7c-4b27-9950-a98c1c7e29da	Chiner	12345678910	60
b33fde92-613a-40a5-a9e8-c09f9f438591	kienboec	daniel	60
f246a10d-6a6a-4fbe-9086-a27fedabd9f1	altenhof	markus	60
758dbb26-b12c-4171-ba84-1c47a9b62ac1	admin	istrator	60
caea1133-1855-452b-b220-b95cb1f5232d	Adrian	12345	0
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

