--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

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
-- Name: achievements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.achievements (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    type character varying(256) NOT NULL,
    name text NOT NULL,
    description text NOT NULL,
    date character varying(20) NOT NULL
);


ALTER TABLE public.achievements OWNER TO postgres;

--
-- Name: achievements_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.achievements_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.achievements_id_seq OWNER TO postgres;

--
-- Name: achievements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.achievements_id_seq OWNED BY public.achievements.id;


--
-- Name: appid2personid; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.appid2personid (
    id integer NOT NULL,
    app_id text NOT NULL,
    profile_id bigint NOT NULL
);


ALTER TABLE public.appid2personid OWNER TO postgres;

--
-- Name: appid2personid_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.appid2personid_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.appid2personid_id_seq OWNER TO postgres;

--
-- Name: appid2personid_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.appid2personid_id_seq OWNED BY public.appid2personid.id;


--
-- Name: authorization; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."authorization" (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    is_admin boolean NOT NULL,
    is_moderator boolean NOT NULL,
    is_banned boolean NOT NULL
);


ALTER TABLE public."authorization" OWNER TO postgres;

--
-- Name: authorization_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.authorization_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.authorization_id_seq OWNER TO postgres;

--
-- Name: authorization_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.authorization_id_seq OWNED BY public."authorization".id;


--
-- Name: chats; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chats (
    id bigint NOT NULL,
    user1_id bigint NOT NULL,
    user2_id bigint NOT NULL,
    msg_cnt bigint NOT NULL
);


ALTER TABLE public.chats OWNER TO postgres;

--
-- Name: chats_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.chats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.chats_id_seq OWNER TO postgres;

--
-- Name: chats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.chats_id_seq OWNED BY public.chats.id;


--
-- Name: instructors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.instructors (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    degree character varying(256) NOT NULL
);


ALTER TABLE public.instructors OWNER TO postgres;

--
-- Name: instructors_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.instructors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.instructors_id_seq OWNER TO postgres;

--
-- Name: instructors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.instructors_id_seq OWNED BY public.instructors.id;


--
-- Name: jobs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jobs (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    place character varying(256) NOT NULL,
    "position" character varying(256) NOT NULL,
    from_date character varying(20) NOT NULL,
    to_date character varying(20) NOT NULL
);


ALTER TABLE public.jobs OWNER TO postgres;

--
-- Name: jobs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.jobs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.jobs_id_seq OWNER TO postgres;

--
-- Name: jobs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.jobs_id_seq OWNED BY public.jobs.id;


--
-- Name: messages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messages (
    id bigint NOT NULL,
    chat_id bigint NOT NULL,
    sender_id bigint NOT NULL,
    pos bigint NOT NULL,
    content text NOT NULL,
    "timestamp" text NOT NULL
);


ALTER TABLE public.messages OWNER TO postgres;

--
-- Name: messages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.messages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.messages_id_seq OWNER TO postgres;

--
-- Name: messages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.messages_id_seq OWNED BY public.messages.id;


--
-- Name: profiles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.profiles (
    id bigint NOT NULL,
    avatar_url text,
    email character varying(256) NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    patronymic character varying(50),
    is_active boolean NOT NULL,
    profile_type integer NOT NULL
);


ALTER TABLE public.profiles OWNER TO postgres;

--
-- Name: profiles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.profiles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.profiles_id_seq OWNER TO postgres;

--
-- Name: profiles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.profiles_id_seq OWNED BY public.profiles.id;


--
-- Name: researchworks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.researchworks (
    id bigint NOT NULL,
    instructor_id bigint NOT NULL,
    title character varying(256) NOT NULL,
    description text NOT NULL,
    details_url text NOT NULL
);


ALTER TABLE public.researchworks OWNER TO postgres;

--
-- Name: researchworks_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.researchworks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.researchworks_id_seq OWNER TO postgres;

--
-- Name: researchworks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.researchworks_id_seq OWNED BY public.researchworks.id;


--
-- Name: students; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.students (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    university character varying(256) NOT NULL,
    faculty character varying(256) NOT NULL,
    step character varying(50) NOT NULL,
    course integer NOT NULL,
    "from" character varying(256) NOT NULL,
    "to" character varying(256) NOT NULL,
    gpa numeric(3,2),
    cv_url text
);


ALTER TABLE public.students OWNER TO postgres;

--
-- Name: students_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.students_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.students_id_seq OWNER TO postgres;

--
-- Name: students_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.students_id_seq OWNED BY public.students.id;


--
-- Name: tags; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tags (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    tag character varying(50) NOT NULL
);


ALTER TABLE public.tags OWNER TO postgres;

--
-- Name: tags_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tags_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tags_id_seq OWNER TO postgres;

--
-- Name: tags_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tags_id_seq OWNED BY public.tags.id;


--
-- Name: temporaryauthtokens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.temporaryauthtokens (
    id integer NOT NULL,
    temporary_token text NOT NULL,
    access_token text,
    refresh_token text,
    "expiresAt" timestamp without time zone,
    profile_type integer,
    "timestamp" timestamp without time zone NOT NULL
);


ALTER TABLE public.temporaryauthtokens OWNER TO postgres;

--
-- Name: temporaryauthtokens_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.temporaryauthtokens_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.temporaryauthtokens_id_seq OWNER TO postgres;

--
-- Name: temporaryauthtokens_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.temporaryauthtokens_id_seq OWNED BY public.temporaryauthtokens.id;


--
-- Name: achievements id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.achievements ALTER COLUMN id SET DEFAULT nextval('public.achievements_id_seq'::regclass);


--
-- Name: appid2personid id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appid2personid ALTER COLUMN id SET DEFAULT nextval('public.appid2personid_id_seq'::regclass);


--
-- Name: authorization id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."authorization" ALTER COLUMN id SET DEFAULT nextval('public.authorization_id_seq'::regclass);


--
-- Name: chats id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats ALTER COLUMN id SET DEFAULT nextval('public.chats_id_seq'::regclass);


--
-- Name: instructors id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instructors ALTER COLUMN id SET DEFAULT nextval('public.instructors_id_seq'::regclass);


--
-- Name: jobs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jobs ALTER COLUMN id SET DEFAULT nextval('public.jobs_id_seq'::regclass);


--
-- Name: messages id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages ALTER COLUMN id SET DEFAULT nextval('public.messages_id_seq'::regclass);


--
-- Name: profiles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profiles ALTER COLUMN id SET DEFAULT nextval('public.profiles_id_seq'::regclass);


--
-- Name: researchworks id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.researchworks ALTER COLUMN id SET DEFAULT nextval('public.researchworks_id_seq'::regclass);


--
-- Name: students id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students ALTER COLUMN id SET DEFAULT nextval('public.students_id_seq'::regclass);


--
-- Name: tags id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tags ALTER COLUMN id SET DEFAULT nextval('public.tags_id_seq'::regclass);


--
-- Name: temporaryauthtokens id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.temporaryauthtokens ALTER COLUMN id SET DEFAULT nextval('public.temporaryauthtokens_id_seq'::regclass);


--
-- Data for Name: achievements; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.achievements (id, profile_id, type, name, description, date) VALUES (1, 2, 'Cool', 'TIWINNER', 'Yeah', '21.10.2022');
INSERT INTO public.achievements (id, profile_id, type, name, description, date) VALUES (2, 1, 'Unique', 'Закрылся', '', '23.01.2020');


--
-- Data for Name: appid2personid; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.appid2personid (id, app_id, profile_id) VALUES (1, '111515143763298986482', 1);
INSERT INTO public.appid2personid (id, app_id, profile_id) VALUES (2, '100172983051465618496', 2);


--
-- Data for Name: authorization; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."authorization" (id, profile_id, is_admin, is_moderator, is_banned) VALUES (1, 1, false, false, false);
INSERT INTO public."authorization" (id, profile_id, is_admin, is_moderator, is_banned) VALUES (3, 2, false, false, false);


--
-- Data for Name: chats; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: instructors; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.instructors (id, profile_id, degree) VALUES (1, 2, 'PhDhDhd');


--
-- Data for Name: jobs; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.jobs (id, profile_id, place, "position", from_date, to_date) VALUES (1, 2, 'Internet', 'Explorer', '', '');
INSERT INTO public.jobs (id, profile_id, place, "position", from_date, to_date) VALUES (2, 1, 'Пятерочка', 'Покупатель', '23.12.22', '21.11.21');


--
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: profiles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.profiles (id, avatar_url, email, first_name, last_name, patronymic, is_active, profile_type) VALUES (2, '', 'fyodorchernogor@gmail.com', 'Фёдор', 'Черногорский', 'Иванович', false, 1);
INSERT INTO public.profiles (id, avatar_url, email, first_name, last_name, patronymic, is_active, profile_type) VALUES (1, '', 'fecher51@gmail.com', 'Черногор', 'Федор', 'Петрович', false, 0);


--
-- Data for Name: researchworks; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.researchworks (id, instructor_id, title, description, details_url) VALUES (1, 1, 'HowToOpenRefrigerator', 'Bistro', '');


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.students (id, profile_id, university, faculty, step, course, "from", "to", gpa, cv_url) VALUES (1, 1, 'СПБСУ', 'ФКМН', 'Бачейлор', 0, '', '', 4.30, NULL);


--
-- Data for Name: tags; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tags (id, profile_id, tag) VALUES (1, 2, 'Economica');
INSERT INTO public.tags (id, profile_id, tag) VALUES (2, 2, 'Ditch');
INSERT INTO public.tags (id, profile_id, tag) VALUES (3, 2, 'Doter');
INSERT INTO public.tags (id, profile_id, tag) VALUES (4, 1, 'Дотер');
INSERT INTO public.tags (id, profile_id, tag) VALUES (5, 1, 'Лолер');
INSERT INTO public.tags (id, profile_id, tag) VALUES (6, 1, 'СхемнаяСложность');


--
-- Data for Name: temporaryauthtokens; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.temporaryauthtokens (id, temporary_token, access_token, refresh_token, "expiresAt", profile_type, "timestamp") VALUES (1, 'RYN2DYHNL4KPA3JRWJDOTCLU8QYQT8SJKE9J4P67C0PY3LTRU55J8FJXXIMA2OIF', 'ya29.a0ARrdaM8flQI5JDPU2PnAVRflwgc7GelU-wwBpL5KVDO9NxAj2yZ3fkWVV2174r-m1Y7105X3W6MdRrMrTgw6Ih1fVxakzWNWWE4cRSoz31xHn1UNDnuez_10SBT8AycV_UOK-u19GAH8DY9LLr65moT3pB9S', '1//0c0XZXCDo1sV6CgYIARAAGAwSNwF-L9IrCFl7FIYqvN5P7gblqn33XkIYEHCljhjNFi0VwsjpORqtqyF07wMhPVN0Ee9V0S2hJqE', '2021-12-22 23:42:52.266', 0, '2021-12-22 22:42:53.285806');
INSERT INTO public.temporaryauthtokens (id, temporary_token, access_token, refresh_token, "expiresAt", profile_type, "timestamp") VALUES (2, '0VQFJG3H43HD7SH7MHSTFPFF4IC5EMS9NPCPV3MSFJVA09GT4HHP1YL34S8IQ5UF', 'ya29.a0ARrdaM_gKWi3JMyTdt9dRGOJkSx0ppmy5XRuUa6t4ZdUqeXcRRXfucIuCIklXIOraJmoMmVMxQUIw8vkVbG6wdLED8CCkud6-lJWMh_av0nOa5TSGMpcYpBREopc6V1lKfxBvr_4zOTPb0wiBb-_K-b4mjk9', '1//0cT-RvWKKBME_CgYIARAAGAwSNwF-L9Ir2I8C2RnGs78Lp1M-i28kWRsVwGWxvPz5wbbKY8y0s5WAvKLTqHXznAg7nAG3Em7l-vk', '2021-12-22 23:53:15.176', 0, '2021-12-22 22:53:16.201184');
INSERT INTO public.temporaryauthtokens (id, temporary_token, access_token, refresh_token, "expiresAt", profile_type, "timestamp") VALUES (3, 'VOLL911PYI2PRYSMLCEA1OHKKRUR3D0GEKT2709KYFM9FCUQ0JLY9UN9L28UANFN', 'ya29.A0ARrdaM9m_beWA5btYwwvz6q_5TQXGJPJX6GPzoyuU1XfSjb6QFngOlPDXvjXukkJLgt71vUTr-ZaocWwSJ7DLC5_uVzUaqI-_I79xf0DAx9p9ZH5ItXtWfbc1Ed9GHSqDKKRyv-QGEd1bI20W6WgepEey_CZ', '1//0cETPaNUrrZoNCgYIARAAGAwSNwF-L9Irqj8wFA55JTNSHAw9p11qBCsoEe-dLGh1RhCrQn3YDoZ6dM4CRjlisE8653AHSXyQa68', '2021-12-22 23:53:35.133', 1, '2021-12-22 22:53:36.154356');
INSERT INTO public.temporaryauthtokens (id, temporary_token, access_token, refresh_token, "expiresAt", profile_type, "timestamp") VALUES (4, '4MMWTS5586YS2DYRC552990JOW5A53BYDZRTMYV0L9GP9JSY4WQB1WB6CYDZXT0T', 'ya29.A0ARrdaM_xNXEyyWaL2AIjq9LlHul2GaNYzhIOPbAbex4r8qKkclU3_EntIgW1SjwmbhHHwdPmNKfwEKiCZFW--xPW3H9Ijo77Dqkvjcx4har7VkAD6OFud-2C8r30cZCXZF3hWgHp9NlLme6PbW0iwz89Zrip', '1//0cNnpIE0bhZ_OCgYIARAAGAwSNwF-L9IrCSstrt4AITdpLfEV9oamgQxKpq3sLxy4FghuPeSTgiXCj92P0G3BFp2exd406SBlctA', '2021-12-22 23:56:32.42', NULL, '2021-12-22 22:56:33.44015');


--
-- Name: achievements_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.achievements_id_seq', 2, true);


--
-- Name: appid2personid_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.appid2personid_id_seq', 2, true);


--
-- Name: authorization_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.authorization_id_seq', 4, true);


--
-- Name: chats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chats_id_seq', 1, false);


--
-- Name: instructors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.instructors_id_seq', 1, true);


--
-- Name: jobs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.jobs_id_seq', 2, true);


--
-- Name: messages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.messages_id_seq', 1, false);


--
-- Name: profiles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.profiles_id_seq', 2, true);


--
-- Name: researchworks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.researchworks_id_seq', 1, true);


--
-- Name: students_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.students_id_seq', 1, true);


--
-- Name: tags_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tags_id_seq', 6, true);


--
-- Name: temporaryauthtokens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.temporaryauthtokens_id_seq', 4, true);


--
-- Name: achievements achievements_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.achievements
    ADD CONSTRAINT achievements_pkey PRIMARY KEY (id);


--
-- Name: appid2personid appid2personid_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appid2personid
    ADD CONSTRAINT appid2personid_pkey PRIMARY KEY (id);


--
-- Name: appid2personid appid2personid_profile_id_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appid2personid
    ADD CONSTRAINT appid2personid_profile_id_unique UNIQUE (profile_id);


--
-- Name: authorization authorization_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."authorization"
    ADD CONSTRAINT authorization_pkey PRIMARY KEY (id);


--
-- Name: authorization authorization_profile_id_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."authorization"
    ADD CONSTRAINT authorization_profile_id_unique UNIQUE (profile_id);


--
-- Name: chats chats_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT chats_pkey PRIMARY KEY (id);


--
-- Name: instructors instructors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instructors
    ADD CONSTRAINT instructors_pkey PRIMARY KEY (id);


--
-- Name: instructors instructors_profile_id_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instructors
    ADD CONSTRAINT instructors_profile_id_unique UNIQUE (profile_id);


--
-- Name: jobs jobs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT jobs_pkey PRIMARY KEY (id);


--
-- Name: messages messages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (id);


--
-- Name: profiles profiles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profiles
    ADD CONSTRAINT profiles_pkey PRIMARY KEY (id);


--
-- Name: researchworks researchworks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.researchworks
    ADD CONSTRAINT researchworks_pkey PRIMARY KEY (id);


--
-- Name: students students_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_pkey PRIMARY KEY (id);


--
-- Name: students students_profile_id_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_profile_id_unique UNIQUE (profile_id);


--
-- Name: tags tags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tags
    ADD CONSTRAINT tags_pkey PRIMARY KEY (id);


--
-- Name: temporaryauthtokens temporaryauthtokens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.temporaryauthtokens
    ADD CONSTRAINT temporaryauthtokens_pkey PRIMARY KEY (id);


--
-- Name: temporaryauthtokens temporaryauthtokens_temporary_token_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.temporaryauthtokens
    ADD CONSTRAINT temporaryauthtokens_temporary_token_unique UNIQUE (temporary_token);


--
-- Name: tags_tag; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX tags_tag ON public.tags USING btree (tag);


--
-- Name: achievements fk_achievements_profile_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.achievements
    ADD CONSTRAINT fk_achievements_profile_id_id FOREIGN KEY (profile_id) REFERENCES public.profiles(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: appid2personid fk_appid2personid_profile_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appid2personid
    ADD CONSTRAINT fk_appid2personid_profile_id_id FOREIGN KEY (profile_id) REFERENCES public.profiles(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: authorization fk_authorization_profile_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."authorization"
    ADD CONSTRAINT fk_authorization_profile_id_id FOREIGN KEY (profile_id) REFERENCES public.profiles(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: chats fk_chats_user1_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT fk_chats_user1_id_id FOREIGN KEY (user1_id) REFERENCES public.profiles(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: chats fk_chats_user2_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT fk_chats_user2_id_id FOREIGN KEY (user2_id) REFERENCES public.profiles(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: instructors fk_instructors_profile_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.instructors
    ADD CONSTRAINT fk_instructors_profile_id_id FOREIGN KEY (profile_id) REFERENCES public.profiles(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: jobs fk_jobs_profile_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT fk_jobs_profile_id_id FOREIGN KEY (profile_id) REFERENCES public.profiles(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: messages fk_messages_chat_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT fk_messages_chat_id_id FOREIGN KEY (chat_id) REFERENCES public.chats(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: messages fk_messages_sender_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT fk_messages_sender_id_id FOREIGN KEY (sender_id) REFERENCES public.profiles(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: researchworks fk_researchworks_instructor_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.researchworks
    ADD CONSTRAINT fk_researchworks_instructor_id_id FOREIGN KEY (instructor_id) REFERENCES public.instructors(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: students fk_students_profile_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT fk_students_profile_id_id FOREIGN KEY (profile_id) REFERENCES public.profiles(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: tags fk_tags_profile_id_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tags
    ADD CONSTRAINT fk_tags_profile_id_id FOREIGN KEY (profile_id) REFERENCES public.profiles(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- PostgreSQL database dump complete
--

