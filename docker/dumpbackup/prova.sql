--
-- PostgreSQL database dump
--

-- Dumped from database version 13.0 (Debian 13.0-1.pgdg100+1)
-- Dumped by pg_dump version 13.0 (Debian 13.0-1.pgdg100+1)

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
-- Name: prova; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA prova;


ALTER SCHEMA prova OWNER TO postgres;

--
-- Name: addingholdertorealty(); Type: FUNCTION; Schema: prova; Owner: postgres
--

CREATE FUNCTION prova.addingholdertorealty() RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
max_h integer;
current_h integer;
BEGIN
    SELECT max_holders, current_holders
    INTO max_h, current_h
    FROM prova.realty
    WHERE id = NEW.realty_id;

    IF current_h = max_h THEN
        RAISE EXCEPTION 'max holders reached';
    ELSE
        UPDATE prova.realty
        SET current_holders = current_holders + 1
        WHERE id = NEW.realty_id;
END IF;
RETURN NEW;
END;
$$;


ALTER FUNCTION prova.addingholdertorealty() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: check; Type: TABLE; Schema: prova; Owner: postgres
--

CREATE TABLE prova."check" (
    id integer NOT NULL,
    check_type character varying(255) NOT NULL,
    cost numeric(10,2) NOT NULL,
    expire date NOT NULL,
    rent_id integer NOT NULL,
    holder_id integer NOT NULL
);


ALTER TABLE prova."check" OWNER TO postgres;

--
-- Name: check_id_seq; Type: SEQUENCE; Schema: prova; Owner: postgres
--

ALTER TABLE prova."check" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME prova.check_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: insertion; Type: TABLE; Schema: prova; Owner: postgres
--

CREATE TABLE prova.insertion (
    id integer NOT NULL,
    description text NOT NULL,
    cost integer NOT NULL,
    publish_date date,
    images bytea[],
    is_visible boolean NOT NULL
);


ALTER TABLE prova.insertion OWNER TO postgres;

--
-- Name: insertion_id_seq; Type: SEQUENCE; Schema: prova; Owner: postgres
--

ALTER TABLE prova.insertion ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME prova.insertion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: realty; Type: TABLE; Schema: prova; Owner: postgres
--

CREATE TABLE prova.realty (
    id integer NOT NULL,
    type character varying(255) NOT NULL,
    square_meters integer NOT NULL,
    max_holders integer NOT NULL,
    owner_id integer NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    insertion_id integer,
    city character varying(255) NOT NULL,
    address character varying(255) NOT NULL,
    is_draft boolean NOT NULL,
    current_holders integer DEFAULT 0 NOT NULL
);


ALTER TABLE prova.realty OWNER TO postgres;

--
-- Name: realty_id_seq; Type: SEQUENCE; Schema: prova; Owner: postgres
--

ALTER TABLE prova.realty ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME prova.realty_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: rent; Type: TABLE; Schema: prova; Owner: postgres
--

CREATE TABLE prova.rent (
    id integer NOT NULL,
    cost integer NOT NULL,
    realty_id integer NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    holder_id integer NOT NULL
);


ALTER TABLE prova.rent OWNER TO postgres;

--
-- Name: rent_id_seq; Type: SEQUENCE; Schema: prova; Owner: postgres
--

ALTER TABLE prova.rent ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME prova.rent_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: saved_search; Type: TABLE; Schema: prova; Owner: postgres
--

CREATE TABLE prova.saved_search (
    id integer NOT NULL,
    location text NOT NULL,
    max_distance integer,
    min_price numeric(10,2),
    max_price numeric(10,2),
    user_id integer NOT NULL,
    holder_id integer
);


ALTER TABLE prova.saved_search OWNER TO postgres;

--
-- Name: user; Type: TABLE; Schema: prova; Owner: postgres
--

CREATE TABLE prova."user" (
    id integer NOT NULL,
    email text NOT NULL COLLATE pg_catalog."C.UTF-8",
    password text NOT NULL COLLATE pg_catalog."C.UTF-8"
);


ALTER TABLE prova."user" OWNER TO postgres;

--
-- Name: user_id_seq; Type: SEQUENCE; Schema: prova; Owner: postgres
--

ALTER TABLE prova."user" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME prova.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: check; Type: TABLE DATA; Schema: prova; Owner: postgres
--

COPY prova."check" (id, check_type, cost, expire, rent_id, holder_id) FROM stdin;
\.


--
-- Data for Name: insertion; Type: TABLE DATA; Schema: prova; Owner: postgres
--

COPY prova.insertion (id, description, cost, publish_date, images, is_visible) FROM stdin;
2       jnjjnj ahiudahd ads     12      \N      \N      t
3       casa di poco conto, grande il giusto e lussuosa,,,,, astenersi affaristi NO PERDITEMPO  31984   \N      \N      f
\.


--
-- Data for Name: realty; Type: TABLE DATA; Schema: prova; Owner: postgres
--

COPY prova.realty (id, type, square_meters, max_holders, owner_id, latitude, longitude, insertion_id, city, address, is_draft, current_holders) FROM stdin;
6       Appartamento    331     5       10      41.9680452      12.1343753      3       Cerveteri       Via di Ceri 127 f       0
9       Appartamento    1       1       7       41.8222822      12.7479728      \N      Monte Compatri  Via Valle Sole delle Carracce 1/2       f       0
3       Appartamento    123     12      7       12.5901465      12.5901465      \N      Rome            f       0
4       Appartamento    43      12      7       41.7822435      12.9362439      \N      Valmontone      Via Genazzano 102       f       1
12      Appartamento    10      10      11      41.7951256      12.5428023      \N      Roma    Via di Fioranello 172   f       1
11      Appartamento    12      3       9       42.3275595      13.4072547      \N      L'Aquila        Via della Gazza f       1
24      Appartamento    10      0       9       42.0031055      12.3402076      \N      Roma    Via Enrico Bemporad     t       0
25      Appartamento    1       0       9       42.0174366      12.6696762      \N      Mentana Via di Fonte Lettiga 69 t       0
22      Appartamento    55      3       9       41.9092513      12.4939532      \N      Roma    Via Sicilia 145 f       1
13      Appartamento    10      4       6       38.8682585      16.6035454      \N      Catanzaro       Via Umbria 18   f       2
26      Appartamento    1       2       13      41.7576286      12.6402384      \N      Marino  Strada Statale 140 Bis 71       f       1
27      Appartamento    30      4       14      41.6183833      13.3851313      \N      Torrice Via Gennare 104A        f       1
28      Villetta a schiera      11      1       7       41.644685       12.521359       \N      Pomezia Via dei Frassini 27     f       0
5       Appartamento    123     23      7       41.8640795      12.485901       2       Roma    Largo Giovanni Ansaldo 8        f       1
10      Appartamento    1       1       7       38.8891245      16.6282785      \N      Catanzaro       Via Costa Leone Nobile 8        f       1
\.


--
-- Data for Name: rent; Type: TABLE DATA; Schema: prova; Owner: postgres
--

COPY prova.rent (id, cost, realty_id, start_date, end_date, holder_id) FROM stdin;
26      10      13      2021-01-22      2021-03-22      13
27      10      26      2021-01-29      2021-04-29      6
28      50      27      2021-02-09      2021-07-09      13
29      40      5       2021-02-06      2022-01-06      16
30      55      10      2021-02-02      2021-04-02      16
\.


--
-- Data for Name: saved_search; Type: TABLE DATA; Schema: prova; Owner: postgres
--

COPY prova.saved_search (id, location, max_distance, min_price, max_price, user_id, holder_id) FROM stdin;
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: prova; Owner: postgres
--

COPY prova."user" (id, email, password) FROM stdin;
6       ciao@ciao.it    $2a$10$8tACrxqQZTapvdpTTrrTpu3p.LNU7GbNQp9m0XuHb6OcNCtcsZdtK
7       folders@fold.com        $2a$10$FX1WJydtyf2ZG10XGWknNeF1yASfmzOde0hkPVb1m6yynsa3vZQQ.
8       asd@asd.it      $2a$10$uXw.N6ZdmidK6xD927djje1VDZyIvJsxUBUjN82zf87jBqsdXsmZW
9       me@muraca.dev   $2a$10$abLMe.N89utcnzfmcOtOCODSI53virYWFNvopZOMh2vl3adKQisPG
10      ciaosonomike@mikesono.io        $2a$10$NabgbZe2QqmYys.UK3KZsuyP5ZAhH4Iz6V17x0QrM1y8UxXfU8FZS
11      prova1@prova.pr $2a$10$NUF80RM.4Qiz3YRKSP3kn.kA7GwXhh7wZv1e6FLP74HdTLHz.sYOi
12      prova2@prova.pr $2a$10$GmAum/Jfp1GD4S.RB/N8k.rs8i5zWXChaWMsGyAkXa5Jf99tk.i0e
13      me@me.it        $2a$10$936uOGvYo45W6BxPjXtju.k.zxrltt5xlqRQjSrRE0n.zXaiRnNbq
14      prova5@prova.it $2a$10$u92ff8423NfwVbxOJgFH3uZO1j.6TRQRwluIKvMNezZ/Mvx2S3m1S
15      mikesonoio@test.io      $2a$10$uIYC5dt3FJZUA0jpU.nhxuW7mthcbfkVKLyei8gB7U42BSuvhomla
16      hero@hero.it    $2a$10$Dd4kIyE0JUEAwYgWZaqiFu0MxcUoKfATcCsTyCRD5jXvCu/.CmvaC
18      bava@bava.it    $2a$10$kUcldJi1QLwxijFzIv9Ha.2rAUzyOi8dKUIUP.Wh2i3QLdW4QVBKK
19      asdasd@asdasd.com       $2a$10$CG5ZscHm6nz1kzUvKMtPlu0aE8o6k9CbhQmx7igyVz5uCDNVMQG26
20      ciaociao@ciao.com       $2a$10$rg2E7k2eOySREinRr4OgD.Y6EBSRZir8dTrRHdREbyObq0PcIfM3i
\.


--
-- Name: check_id_seq; Type: SEQUENCE SET; Schema: prova; Owner: postgres
--

SELECT pg_catalog.setval('prova.check_id_seq', 1, false);


--
-- Name: insertion_id_seq; Type: SEQUENCE SET; Schema: prova; Owner: postgres
--

SELECT pg_catalog.setval('prova.insertion_id_seq', 3, true);


--
-- Name: realty_id_seq; Type: SEQUENCE SET; Schema: prova; Owner: postgres
--

SELECT pg_catalog.setval('prova.realty_id_seq', 28, true);


--
-- Name: rent_id_seq; Type: SEQUENCE SET; Schema: prova; Owner: postgres
--

SELECT pg_catalog.setval('prova.rent_id_seq', 31, true);


--
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: prova; Owner: postgres
--

SELECT pg_catalog.setval('prova.user_id_seq', 20, true);


--
-- Name: check check_pkey; Type: CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova."check"
ADD CONSTRAINT check_pkey PRIMARY KEY (id);


--
-- Name: insertion insertion_pkey; Type: CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova.insertion
ADD CONSTRAINT insertion_pkey PRIMARY KEY (id);


--
-- Name: realty realty_pkey; Type: CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova.realty
ADD CONSTRAINT realty_pkey PRIMARY KEY (id);


--
-- Name: rent rent_pkey; Type: CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova.rent
ADD CONSTRAINT rent_pkey PRIMARY KEY (id);


--
-- Name: saved_search saved_search_pkey; Type: CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova.saved_search
ADD CONSTRAINT saved_search_pkey PRIMARY KEY (id);


--
-- Name: user unique_email; Type: CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova."user"
ADD CONSTRAINT unique_email UNIQUE (email);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova."user"
ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: email_index; Type: INDEX; Schema: prova; Owner: postgres
--

CREATE INDEX email_index ON prova."user" USING hash (email text_pattern_ops);


--
-- Name: fki_realty_id; Type: INDEX; Schema: prova; Owner: postgres
--

CREATE INDEX fki_realty_id ON prova.rent USING btree (realty_id);


--
-- Name: index1; Type: INDEX; Schema: prova; Owner: postgres
--

CREATE UNIQUE INDEX index1 ON prova.realty USING btree (id);


--
-- Name: index2; Type: INDEX; Schema: prova; Owner: postgres
--

CREATE UNIQUE INDEX index2 ON prova.insertion USING btree (id);


--
-- Name: index3; Type: INDEX; Schema: prova; Owner: postgres
--

CREATE UNIQUE INDEX index3 ON prova.rent USING btree (id);


--
-- Name: index4; Type: INDEX; Schema: prova; Owner: postgres
--

CREATE UNIQUE INDEX index4 ON prova."check" USING btree (id);


--
-- Name: index5; Type: INDEX; Schema: prova; Owner: postgres
--

CREATE UNIQUE INDEX index5 ON prova.saved_search USING btree (id);


--
-- Name: rent addholder; Type: TRIGGER; Schema: prova; Owner: postgres
--

CREATE TRIGGER addholder BEFORE INSERT ON prova.rent FOR EACH ROW EXECUTE FUNCTION prova.addingholdertorealty();


--
-- Name: saved_search fksvngm43511gbv6q48x63hsh56; Type: FK CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova.saved_search
ADD CONSTRAINT fksvngm43511gbv6q48x63hsh56 FOREIGN KEY (holder_id) REFERENCES prova."user"(id);


--
-- Name: check fkt3dmu2dj6ywpowfgqj5bmyj0g; Type: FK CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova."check"
ADD CONSTRAINT fkt3dmu2dj6ywpowfgqj5bmyj0g FOREIGN KEY (rent_id) REFERENCES prova.rent(id);


--
-- Name: rent holder_id; Type: FK CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova.rent
ADD CONSTRAINT holder_id FOREIGN KEY (holder_id) REFERENCES prova."user"(id) NOT VALID;


--
-- Name: realty insertion_key; Type: FK CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova.realty
ADD CONSTRAINT insertion_key FOREIGN KEY (insertion_id) REFERENCES prova.insertion(id);


--
-- Name: rent realty_id; Type: FK CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova.rent
ADD CONSTRAINT realty_id FOREIGN KEY (realty_id) REFERENCES prova.realty(id) NOT VALID;


--
-- Name: realty realty_key; Type: FK CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova.realty
ADD CONSTRAINT realty_key FOREIGN KEY (owner_id) REFERENCES prova."user"(id);


--
-- Name: CONSTRAINT realty_key ON realty; Type: COMMENT; Schema: prova; Owner: postgres
--

COMMENT ON CONSTRAINT realty_key ON prova.realty IS 'Collegamento tra owner_id e utente';


--
-- Name: saved_search user_id; Type: FK CONSTRAINT; Schema: prova; Owner: postgres
--

ALTER TABLE ONLY prova.saved_search
ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES prova."user"(id);


--
-- PostgreSQL database dump complete
--

