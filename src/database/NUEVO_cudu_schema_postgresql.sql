--
-- PostgreSQL database dump
--

-- Started on 2011-08-10 20:10:07 CEST

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 1890 (class 1262 OID 17024)
-- Name: cudu; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE cudu WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'es_ES.utf8' LC_CTYPE = 'es_ES.utf8';


ALTER DATABASE cudu OWNER TO postgres;

\connect cudu

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 328 (class 2612 OID 17027)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

--
-- TOC entry 20 (class 1255 OID 17099)
-- Dependencies: 328 3
-- Name: actualizardatosauditoriaasociado(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION actualizardatosauditoriaasociado() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    new.fechaActualizacion = CURRENT_TIMESTAMP;
    return new;
END;
$$;


ALTER FUNCTION public.actualizardatosauditoriaasociado() OWNER TO postgres;

--
-- TOC entry 21 (class 1255 OID 17101)
-- Dependencies: 328 3
-- Name: actualizarfiltroasociacion(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION actualizarfiltroasociacion() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	new.asociacion = (SELECT asociacion from grupo where id = new.idgrupo limit 1);
	return new;
END;
$$;


ALTER FUNCTION public.actualizarfiltroasociacion() OWNER TO postgres;

--
-- TOC entry 22 (class 1255 OID 17102)
-- Dependencies: 3 328
-- Name: actualizarfiltroasociaciongrupo(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION actualizarfiltroasociaciongrupo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	UPDATE asociado SET asociacion = new.asociacion WHERE asociado.idgrupo = new.id;
	return new;
END;
$$;


ALTER FUNCTION public.actualizarfiltroasociaciongrupo() OWNER TO postgres;

--
-- TOC entry 19 (class 1255 OID 17097)
-- Dependencies: 3 328
-- Name: comprobarcantidadramasmenor(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION comprobarcantidadramasmenor() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    existentes int;
BEGIN
    if new.tipo <> 'J' then
        return new;
    end if;

    existentes := 0;
    if new.rama_colonia = true then existentes := existentes + 1; end if;
    if new.rama_manada = true then existentes := existentes + 1; end if;
    if new.rama_exploradores = true then existentes := existentes + 1; end if;
    if new.rama_pioneros = true then existentes := existentes + 1; end if;
    if new.rama_rutas = true then existentes := existentes + 1; end if;
    
    if existentes > 1 then
        raise exception 'Un niño no puede estar en más de dos ramas simultáneamente.';
        return null;
    else
        return new;
    end if;
END;
$$;


ALTER FUNCTION public.comprobarcantidadramasmenor() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1519 (class 1259 OID 17066)
-- Dependencies: 1808 1809 1810 1811 1812 1813 1814 1815 1816 1817 1818 1819 1820 1821 1822 1823 1824 1825 1826 3
-- Name: asociado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE asociado (
    id integer NOT NULL,
    tipo character(1) DEFAULT 'J'::bpchar NOT NULL,
    idgrupo character varying(20) NOT NULL,
    nombre character varying(30) NOT NULL,
    primerapellido character varying(50) NOT NULL,
    segundoapellido character varying(50),
    sexo character(1) NOT NULL,
    fechanacimiento date NOT NULL,
    dni character varying(10),
    seguridadsocial character varying(12),
    tieneseguroprivado boolean DEFAULT false NOT NULL,
    calle character varying(100) NOT NULL,
    numero character varying(5) NOT NULL,
    escalera character varying(3),
    puerta character varying(3),
    codigopostal integer NOT NULL,
    telefonocasa character varying(15),
    telefonomovil character varying(15),
    email character varying(100),
    idprovincia smallint DEFAULT 0 NOT NULL,
    provincia character varying(100) NOT NULL,
    idmunicipio integer DEFAULT 0 NOT NULL,
    municipio character varying(100) NOT NULL,
    tienetutorlegal boolean DEFAULT false NOT NULL,
    padresdivorciados boolean DEFAULT false NOT NULL,
    padre_nombre character varying(250),
    padre_telefono character varying(15),
    padre_email character varying(100),
    madre_nombre character varying(250),
    madre_telefono character varying(15),
    madre_email character varying(100),
    fechaalta timestamp without time zone DEFAULT now() NOT NULL,
    fechabaja timestamp without time zone,
    fechaactualizacion timestamp without time zone DEFAULT now() NOT NULL,
    ramas character varying(10) DEFAULT ''::character varying,
    rama_colonia boolean DEFAULT false NOT NULL,
    rama_manada boolean DEFAULT false NOT NULL,
    rama_exploradores boolean DEFAULT false NOT NULL,
    rama_pioneros boolean DEFAULT false NOT NULL,
    rama_rutas boolean DEFAULT false NOT NULL,
    jpa_version integer DEFAULT 0 NOT NULL,
    asociacion smallint DEFAULT 1 NOT NULL,
    textsearchindex tsvector,
    activo boolean DEFAULT true NOT NULL,
    usuario character varying(200),
    CONSTRAINT ck_enum_asociado_sexo CHECK ((sexo = ANY (ARRAY['M'::bpchar, 'F'::bpchar]))),
    CONSTRAINT ck_enum_asociado_tipo CHECK ((tipo = ANY (ARRAY['J'::bpchar, 'K'::bpchar, 'C'::bpchar, 'V'::bpchar])))
);


ALTER TABLE public.asociado OWNER TO postgres;

--
-- TOC entry 1518 (class 1259 OID 17064)
-- Dependencies: 3 1519
-- Name: asociado_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE asociado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.asociado_id_seq OWNER TO postgres;

--
-- TOC entry 1893 (class 0 OID 0)
-- Dependencies: 1518
-- Name: asociado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE asociado_id_seq OWNED BY asociado.id;


--
-- TOC entry 1894 (class 0 OID 0)
-- Dependencies: 1518
-- Name: asociado_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('asociado_id_seq', 27714, true);


--
-- TOC entry 1517 (class 1259 OID 17054)
-- Dependencies: 3
-- Name: authorities; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE authorities (
    username character varying(200) NOT NULL,
    authority character varying(50) NOT NULL
);


ALTER TABLE public.authorities OWNER TO postgres;

--
-- TOC entry 1520 (class 1259 OID 17321)
-- Dependencies: 3
-- Name: curso; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE curso (
    id integer NOT NULL,
    nombre character varying(50) NOT NULL,
    acronimo character varying(6) NOT NULL,
    anyo integer NOT NULL,
    precio double precision,
    descripcion character varying(100)
);


ALTER TABLE public.curso OWNER TO postgres;

--
-- TOC entry 1524 (class 1259 OID 17379)
-- Dependencies: 3
-- Name: faltamonografico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE faltamonografico (
    idasociado integer NOT NULL,
    idmonografico integer NOT NULL,
    idcurso integer,
    fechafalta date NOT NULL
);


ALTER TABLE public.faltamonografico OWNER TO postgres;

--
-- TOC entry 1515 (class 1259 OID 17028)
-- Dependencies: 1802 1803 1804 1805 1806 3
-- Name: grupo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE grupo (
    id character varying(20) NOT NULL,
    nombre character varying(50) NOT NULL,
    direccion character varying(300) DEFAULT '(desconocida)'::character varying NOT NULL,
    codigopostal integer NOT NULL,
    idprovincia integer DEFAULT 0 NOT NULL,
    provincia character varying(100) NOT NULL,
    idmunicipio integer DEFAULT 0 NOT NULL,
    municipio character varying(100) NOT NULL,
    aniversario date,
    telefono1 character varying(15) DEFAULT '(desconocido)'::character varying,
    telefono2 character varying(15),
    email character varying(100),
    web character varying(300),
    entidadpatrocinadora character varying(100),
    asociacion smallint NOT NULL,
    jpa_version integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.grupo OWNER TO postgres;

--
-- TOC entry 1523 (class 1259 OID 17355)
-- Dependencies: 1830 1831 1832 1833 1834 1835 1836 1837 1838 3
-- Name: inscripcioncurso; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE inscripcioncurso (
    idasociado integer NOT NULL,
    idcurso integer NOT NULL,
    idmonografico integer NOT NULL,
    fechainscripcion date NOT NULL,
    pagorealizado boolean NOT NULL,
    trabajo character(1) NOT NULL,
    fecha_entrega_trabajo date,
    calificacion character(1) DEFAULT 'N'::bpchar NOT NULL,
    idiomamateriales character(1) DEFAULT 'V'::bpchar NOT NULL,
    modocontacto character(1) DEFAULT 'E'::bpchar NOT NULL,
    formatomateriales character(1) DEFAULT 'E'::bpchar NOT NULL,
    CONSTRAINT ck_enum_calificacion_formatomateriales CHECK ((formatomateriales = ANY (ARRAY['E'::bpchar, 'P'::bpchar]))),
    CONSTRAINT ck_enum_calificacion_idiomamateriales CHECK ((idiomamateriales = ANY (ARRAY['V'::bpchar, 'C'::bpchar]))),
    CONSTRAINT ck_enum_calificacion_inscripcioncurso CHECK ((calificacion = ANY (ARRAY['E'::bpchar, 'A'::bpchar, 'N'::bpchar, 'P'::bpchar, 'O'::bpchar]))),
    CONSTRAINT ck_enum_calificacion_modocontacto CHECK ((modocontacto = ANY (ARRAY['E'::bpchar, 'P'::bpchar]))),
    CONSTRAINT ck_enum_trabajo_inscripcioncurso CHECK ((trabajo = ANY (ARRAY['N'::bpchar, 'P'::bpchar, 'A'::bpchar, 'O'::bpchar])))
);


ALTER TABLE public.inscripcioncurso OWNER TO postgres;

--
-- TOC entry 1521 (class 1259 OID 17330)
-- Dependencies: 3
-- Name: monografico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE monografico (
    id integer NOT NULL,
    nombre character varying(50) NOT NULL,
    fechainicio date NOT NULL,
    fechafin date NOT NULL,
    precio double precision,
    descripcion character varying(100),
    plazasdisponibles integer NOT NULL,
    plazastotales integer NOT NULL,
    listaespera integer NOT NULL,
    lugarprevisto character varying(100)
);


ALTER TABLE public.monografico OWNER TO postgres;

--
-- TOC entry 1522 (class 1259 OID 17337)
-- Dependencies: 1827 1828 1829 3
-- Name: monograficos_en_cursos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE monograficos_en_cursos (
    idcurso integer NOT NULL,
    idmonografico integer NOT NULL,
    bloque character(100),
    obligatorio boolean DEFAULT false NOT NULL,
    bloqueunico boolean DEFAULT false NOT NULL,
    bloque_numerominimo_monograficos integer DEFAULT 0
);


ALTER TABLE public.monograficos_en_cursos OWNER TO postgres;

--
-- TOC entry 1516 (class 1259 OID 17041)
-- Dependencies: 3
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    username character varying(200) NOT NULL,
    password character varying(200) NOT NULL,
    fullname character varying(200) NOT NULL,
    idgrupo character varying(20),
    enabled boolean NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 1807 (class 2604 OID 17069)
-- Dependencies: 1519 1518 1519
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE asociado ALTER COLUMN id SET DEFAULT nextval('asociado_id_seq'::regclass);


--
-- TOC entry 1882 (class 0 OID 17066)
-- Dependencies: 1519
-- Data for Name: asociado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY asociado (id, tipo, idgrupo, nombre, primerapellido, segundoapellido, sexo, fechanacimiento, dni, seguridadsocial, tieneseguroprivado, calle, numero, escalera, puerta, codigopostal, telefonocasa, telefonomovil, email, idprovincia, provincia, idmunicipio, municipio, tienetutorlegal, padresdivorciados, padre_nombre, padre_telefono, padre_email, madre_nombre, madre_telefono, madre_email, fechaalta, fechabaja, fechaactualizacion, ramas, rama_colonia, rama_manada, rama_exploradores, rama_pioneros, rama_rutas, jpa_version, asociacion, textsearchindex, activo, usuario) FROM stdin;
20	J	UP	Alan	Barron	Forbes	F	1991-11-10	17741286Z	\N	f	P.O. Box 740, 6450 Elit Rd.	7	\N	11	82841	490554661	490554661	nulla.Cras@aliquamerosturpis.ca	0	Sark	0	San Marino	f	f	Uriah Dale Gallagher	908196344	sagittis.Nullam@iaculisenimsit.org	Cassidy Bailey Riggs	908196344	Curabitur.ut.odio@tempusloremfringilla.com	2010-01-27 00:00:00	\N	2011-08-10 16:54:56.528743	R	f	f	f	f	t	0	1	'alan':1 'barron':2 'forb':3	t	albafo
3	K	UP	Jena	Strickland	Garner	F	1998-12-09	89771911M	\N	f	919-1828 Placerat St.	6	\N	16	89743	121208321	121208321	malesuada.id@mauriselit.edu	0	Fife	0	Trenton	f	f	\N	\N	\N	\N	\N	\N	2005-04-11 00:00:00	2010-09-06 10:30:48.561	2011-08-10 16:54:56.528743	M	f	t	f	f	f	0	1	'garn':3 'jen':1 'strickland':2	f	JESTRICKGA
126	C	UP	Jaquelyn	Bush	Melton	M	1990-09-27	19579371V	\N	f	P.O. Box 300, 7522 Non Avenue	7	\N	6	39418	818447088	818447088	tempor@Aliquamvulputate.edu	0	Maine	0	Littleton	f	f	Otto Crawford Cooley	635834508	sociis@dictumcursusNunc.edu	Nomlanga Munoz Fry	635834508	sit.amet.ultricies@elementumat.ca	2005-01-05 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	2	1	'bush':2 'jaquelyn':1 'melton':3	t	\N
182	J	UP	May	Goodman	Cooke	M	1991-10-17	59017539G	\N	f	Ap #990-7928 Euismod Street	2	\N	17	61448	213389204	213389204	sem@Suspendissenonleo.com	0	Dr.	0	Laconia	f	f	Walter Baird Ward	900140585	semper@lacusMaurisnon.ca	Britanni Jacobs Hardin	900140585	arcu.et.pede@diamvel.com	2009-11-16 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	3	1	'cook':3 'goodm':2 'may':1	t	\N
154	J	UP	Russell	Boyd	Boyer	F	1997-08-21	17844441F	\N	f	Ap #619-2125 Nec St.	5	\N	26	16434	466103583	466103583	consequat@idmollisnec.ca	0	ANS	0	Kokomo	f	f	Damon Hensley Ray	973481266	posuere@scelerisquemollis.ca	Sonya Schroeder Berry	973481266	facilisis.facilisis@metus.com	2003-01-04 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'boy':3 'boyd':2 'russell':1	t	\N
180	J	UP	Wallace	Lancaster	Long	F	1995-05-03	85841391J	\N	f	Ap #663-9323 Consectetuer Rd.	9	\N	21	4727	990775193	990775193	mollis.Phasellus@Pellentesque.org	0	IOW	0	Redlands	f	f	Joseph Lancaster Parker	450110554	tortor@vehicula.ca	Yoko Fowler Valdez	450110554	Curabitur.egestas@condimentumDonec.ca	2009-07-23 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'lancast':2 'long':3 'wallac':1	t	\N
147	J	UP	Catherine	Parker	Mills	F	1996-07-18	36312515N	\N	f	955-8240 Habitant Rd.	3	\N	12	37340	698916627	698916627	Cum.sociis.natoque@lacusQuisquepurus.ca	0	Utrecht	0	Cohoes	f	f	Edan Velez Rodriquez	227714762	turpis.non@consectetuereuismodest.edu	Ayanna Gallegos Collins	227714762	magnis@libero.ca	2011-02-09 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'catherin':1 'mills':3 'park':2	t	\N
123	J	UP	Harding	Bradford	Gamble	M	1995-08-21	91480119B	\N	f	815-340 Hendrerit St.	10	\N	7	5670	848412992	848412992	ac@famesacturpis.edu	0	Peebles-shire	0	Homer	f	f	Brock Lynch Nolan	930837886	nec.urna.et@Suspendissecommodo.com	McKenzie Craig James	930837886	ipsum@Aliquamultrices.edu	2002-05-13 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'bradford':2 'gambl':3 'harding':1	t	\N
145	J	UP	Hannah	Oliver	Mcmahon	F	2004-10-24	33264631P	\N	f	Ap #372-3907 Odio. Ave	2	\N	3	11943	621028583	621028583	egestas.lacinia.Sed@hendreritnequeIn.org	0	Texas	0	North Charleston	f	f	Barclay Dillon Bernard	111966303	metus.In.nec@duiin.org	Clio Chang Key	111966303	mi.fringilla.mi@viverraDonectempus.ca	2010-08-19 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'hannah':1 'mcmahon':3 'oliv':2	t	\N
148	K	UP	Brennan	Hodges	Park	M	1999-07-11	15543475C	\N	f	Ap #979-6070 Eget St.	6	\N	2	85634	407837640	407837640	ligula.Aenean@apurus.com	0	SKa	0	Cody	f	f	Driscoll Hinton Bailey	997123279	ultricies@arcuiaculis.org	Tasha Barron Hahn	997123279	tincidunt.pede.ac@nonummy.org	2005-05-11 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	1	1	'brenn':1 'hodg':2 'park':3	t	\N
133	J	UP	Ina	Richardson	Patrick	F	1993-04-28	71883143E	\N	f	Ap #391-139 Nisl. Rd.	7	\N	19	17583	670522852	670522852	adipiscing.elit@a.org	0	WI	0	Providence	f	f	Yoshio Chapman Wilcox	185841122	at.arcu@nequetellus.com	Reagan Norton Shannon	185841122	ac.mattis@luctusvulputate.edu	2009-09-23 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'ina':1 'patrick':3 'richardson':2	t	\N
153	J	UP	Damon	Wilkinson	Mcgowan	F	1999-06-23	54851877L	\N	f	P.O. Box 312, 6700 Aliquet Ave	10	\N	3	82110	921153942	921153942	dolor@vehicularisus.com	0	Ross and Cromarty	0	North Adams	f	f	Hyatt Potts Jacobs	250330069	lacinia.vitae@Sedmolestie.ca	Quin Trevino Mccoy	250330069	consectetuer.euismod@ultrices.org	2007-06-04 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'damon':1 'mcgow':3 'wilkinson':2	t	\N
132	J	UP	Olympia	Bryan	Oconnor	M	2001-01-16	69275628C	\N	f	8794 Tempus, St.	4	\N	12	47699	475966901	475966901	Donec.egestas@auctorMauris.ca	0	AK	0	Needham	f	f	Jameson Decker Gentry	443326053	Phasellus@rutrumnon.com	Galena Torres Donaldson	443326053	dolor@egetvolutpatornare.edu	2008-11-12 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'bryan':2 'oconnor':3 'olympi':1	t	\N
186	J	UP	Mannix	Hill	Weeks	F	1997-06-24	81259282D	\N	f	P.O. Box 836, 7180 Amet, St.	4	\N	4	73044	361540411	361540411	imperdiet@Duismienim.com	0	CAE	0	Chandler	f	f	Harper Donaldson Fuentes	118311689	Maecenas.ornare@enimdiamvel.edu	Rhiannon Franks Rojas	118311689	Vestibulum.ut@risusvariusorci.org	2005-05-10 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'hill':2 'mannix':1 'weeks':3	t	\N
144	J	UP	Yvonne	Dalton	Maddox	F	1994-08-26	27884434S	\N	f	Ap #414-8115 Blandit Road	2	\N	25	16807	115941399	115941399	velit.justo@scelerisque.com	0	Noord Brabant	0	Truth or Consequences	f	f	Walter Patel Little	968746576	facilisis.facilisis@arcuMorbi.org	MacKensie Marshall Mcdowell	968746576	tellus@erategettincidunt.edu	2006-12-16 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'dalton':2 'maddox':3 'yvonn':1	t	\N
157	J	UP	Andrew	Haynes	Rowe	F	1996-11-21	81829471U	\N	f	P.O. Box 255, 8225 Mauris Road	2	\N	14	32511	389592676	389592676	Mauris.blandit@semsemper.org	0	NV	0	Kearney	f	f	Marshall Cross Gibson	107870475	sit@Donecegestas.com	Stacey Sampson Hubbard	107870475	sapien.cursus@purusMaecenas.edu	2009-06-16 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'andrew':1 'hayn':2 'row':3	t	\N
155	J	UP	Lyle	Woods	Moss	F	2000-12-04	29910561T	\N	f	Ap #698-7346 Arcu St.	3	\N	12	4881	508132920	508132920	odio.sagittis@iaculis.edu	0	Noord Holland	0	Saint Louis	f	f	Brendan Barton Johns	59493815	sociis@necquamCurabitur.edu	Marcia Solomon Gamble	59493815	volutpat.Nulla@conubianostra.edu	2011-03-16 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'lyle':1 'moss':3 'woods':2	t	\N
142	J	UP	Shaine	Page	Kinney	F	2003-03-11	86317644M	\N	f	786-5271 Cubilia St.	10	\N	22	19761	342776467	342776467	quis@ProinmiAliquam.ca	0	Florida	0	Davenport	f	f	Lucian Velazquez Spencer	601404303	ante@eutelluseu.edu	Lenore Mullins Lancaster	601404303	felis@ipsumportaelit.com	2008-06-21 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'kinney':3 'pag':2 'shain':1	t	\N
169	J	UP	Emma	Bird	Ford	M	1992-06-16	73975197R	\N	f	P.O. Box 681, 3644 Nullam St.	10	\N	11	73135	388980697	388980697	sem@sitamet.com	0	Westmorland	0	Muskogee	f	f	Dalton Foley French	905352912	sed.dui.Fusce@telluslorem.edu	Megan Jefferson Owens	905352912	dis@tellus.com	2010-05-24 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'bird':2 'emma':1 'ford':3	t	\N
166	J	UP	Marcia	Mcbride	Sloan	F	1992-08-19	43839191L	\N	f	Ap #944-6764 Volutpat Rd.	9	\N	20	83287	596617660	596617660	sem.molestie@est.ca	0	CAM	0	Taunton	f	f	Alec Sykes Marsh	606680953	pharetra.sed.hendrerit@enimsit.com	Haviva Campbell Hughes	606680953	ante.blandit@Lorem.ca	2009-03-20 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'marci':1 'mcbrid':2 'slo':3	t	\N
167	J	UP	Hakeem	Thomas	Gaines	M	2003-05-29	92662276R	\N	f	265 Vel Road	7	\N	28	36921	484364217	484364217	Nam@eutellus.edu	0	AL	0	Santa Clarita	f	f	Upton Sandoval Frost	664404605	justo.Praesent@mi.com	Lisandra Ramirez Lang	664404605	ornare.sagittis@sedliberoProin.ca	2010-07-28 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'gain':3 'hakeem':1 'thom':2	t	\N
195	J	UP	Orlando	Franks	Munoz	F	1993-08-07	34239408I	\N	f	P.O. Box 666, 8073 Eget St.	1	\N	20	23218	357110981	357110981	ultrices.Duis.volutpat@consectetueradipiscingelit.edu	0	Zld.	0	West Hollywood	f	f	Abraham Roberts Mcclure	906473952	sagittis.lobortis@eratvolutpatNulla.org	Georgia Zimmerman Osborn	906473952	Quisque.tincidunt.pede@Crasinterdum.edu	2002-04-25 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'franks':2 'munoz':3 'orland':1	t	\N
152	J	UP	Fleur	Rocha	Clay	M	2003-02-14	04017063X	\N	f	1118 Consectetuer Av.	2	\N	21	65719	792454137	792454137	cursus.diam@ametnullaDonec.org	0	Prince Edward Island	0	Plattsburgh	f	f	Elton Daniel Rosario	938112120	mi.enim.condimentum@ultricesposuere.edu	Tamara Wall Valencia	938112120	est.vitae.sodales@nequeetnunc.com	2008-02-04 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'clay':3 'fleur':1 'roch':2	t	\N
194	J	UP	Arden	Figueroa	Holman	M	1997-01-22	68857648X	\N	f	Ap #202-4098 Lorem Road	9	\N	28	947	617833472	617833472	eget.odio.Aliquam@nostra.com	0	Wyoming	0	Rolling Hills Estates	f	f	Lewis Cummings Sloan	961602888	rhoncus.Proin@diam.edu	Nita Caldwell Emerson	961602888	Etiam.bibendum@nectempusmauris.edu	2002-05-30 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'arden':1 'figuero':2 'holm':3	t	\N
134	J	UP	Joseph	Walls	Tucker	F	2002-07-03	47931273L	\N	f	146-2048 Morbi Rd.	1	\N	5	83850	241043020	241043020	sapien.imperdiet@Morbi.ca	0	MS	0	Laurel	f	f	Ira Keith Mcintosh	669491832	in.cursus.et@Aenean.org	Savannah Goff Cameron	669491832	feugiat.Sed@turpisvitae.org	2004-10-06 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'joseph':1 'tuck':3 'walls':2	t	\N
141	J	UP	Hedda	Whitley	Rosa	M	2000-10-02	94979399V	\N	f	6368 Aliquam Street	10	\N	12	17702	210072547	210072547	posuere@mattisornare.ca	0	Dr.	0	Paducah	f	f	Cole Hendricks Mercer	880986854	aliquet.libero.Integer@Morbi.edu	Xyla Bullock Oneal	880986854	molestie.arcu@torquent.edu	2002-10-22 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'hedd':1 'ros':3 'whitley':2	t	\N
231	K	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 12:56:04.822283	\N	2011-08-10 16:31:43.5992	M,P	f	t	f	t	f	1	1	'jack':1 'pearl':3 'sparrow':2	t	\N
77	J	UP	Molly	Obrien	Pope	M	1997-12-17	45801724J	\N	f	Ap #961-5391 Lorem, Av.	6	\N	25	75070	647706865	647706865	Mauris@Suspendisseac.edu	0	Flevoland	0	City of Industry	f	f	Stone Le Blair	63809559	sit@duiCras.org	Iris Mclean Schultz	63809559	tellus@erat.org	2004-01-14 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'molly':1 'obri':2 'pop':3	t	\N
63	J	UP	Urielle	Chan	Daniels	F	1992-05-28	99542213F	\N	f	1435 Sem Rd.	10	\N	15	79753	487628979	487628979	malesuada.ut.sem@eteuismodet.edu	0	LOU	0	Hermosa Beach	f	f	Ralph Steele Gentry	231072527	nibh@quamelementumat.org	Nita Moore Slater	231072527	adipiscing.lobortis@nonleo.com	2008-05-23 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'chan':2 'daniels':3 'uriell':1	t	\N
158	J	UP	Myra	Mercer	Cunningham	F	1991-09-11	04630343S	\N	f	7148 Sed Avenue	6	\N	13	80275	236732061	236732061	vel@nasceturridiculus.edu	0	Overijssel	0	Gardner	f	f	Lane Stevens Cleveland	57986367	In.scelerisque@consequatdolorvitae.org	Nina Boyd Whitaker	57986367	augue.id.ante@Cumsociis.ca	2008-10-09 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'cunningham':3 'merc':2 'myra':1	t	\N
193	J	UP	Xanthus	Downs	Noble	F	2001-02-12	02463128O	\N	f	522-6127 Fusce St.	3	\N	14	40290	149022022	149022022	varius.orci@utipsum.com	0	Alberta	0	Titusville	f	f	Daniel Merrill Miles	873387617	ut@Mauris.ca	Gloria Stein Mclean	873387617	auctor@Curabitur.org	2007-07-24 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'downs':2 'nobl':3 'xanthus':1	t	\N
151	J	UP	Sonya	West	Haley	M	1995-09-12	29173017G	\N	f	Ap #543-906 In Rd.	10	\N	8	20736	847208600	847208600	neque.Nullam@nec.edu	0	WYK	0	Tyler	f	f	Colt Prince Schultz	912859548	lorem@Quisque.ca	Farrah Jenkins Davenport	912859548	elit.sed.consequat@ipsumPhasellus.com	2010-05-28 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'haley':3 'sony':1 'west':2	t	\N
178	J	UP	Veda	Mendoza	Black	F	1990-09-09	38459290P	\N	f	8790 Ut Av.	8	\N	2	46266	481747578	481747578	quam.quis@euismodenim.org	0	Mississippi	0	El Monte	f	f	Holmes Gilmore Hurley	453403117	felis.adipiscing.fringilla@sitamet.edu	Ayanna Mason Carrillo	453403117	velit@ascelerisque.ca	2006-04-04 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'black':3 'mendoz':2 'ved':1	t	\N
174	J	UP	Ira	Griffin	Dorton	F	2002-01-19	99741637Z	\N	f	P.O. Box 564, 2520 Mollis St.	9	\N	9	52744	39180289	39180289	enim.commodo.hendrerit@nibhlacinia.org	0	Texas	0	Lomita	f	f	Castor Sargent Kirby	601705429	consectetuer.rhoncus.Nullam@Donecegestas.ca	Jolene Lang Washington	601705429	metus.urna.convallis@eleifendegestasSed.com	2009-06-10 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	1	1	'dorton':3 'griffin':2 'ira':1	t	\N
150	J	UP	Dean	Robertson	Freeman	M	1999-07-16	27684668Y	\N	f	P.O. Box 792, 5675 Montes, Rd.	7	\N	17	94229	877822263	877822263	odio.vel.est@augue.edu	0	Ov.	0	Kenner	f	f	Lester Bailey Gregory	456550569	id.mollis@amet.com	Allegra Dejesus Miller	456550569	sed.dui@magna.com	2008-01-27 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'dean':1 'freem':3 'robertson':2	t	\N
172	J	UP	Candace	Prince	Summers	F	1994-11-26	93231797E	\N	f	Ap #425-1360 Eget Rd.	5	\N	13	48773	64322292	64322292	nunc.sed@asollicitudin.edu	0	GAL	0	Appleton	f	f	Octavius Burris Bates	421522485	Morbi@egetipsum.ca	Larissa Turner Sharpe	421522485	venenatis@Aliquamultrices.org	2008-08-08 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'candac':1 'princ':2 'summers':3	t	\N
175	J	UP	Akeem	Hill	Kelley	F	1991-11-24	94013236Z	\N	f	P.O. Box 818, 1803 Diam Road	4	\N	5	7508	610638183	610638183	lacinia@diamvelarcu.org	0	Saskatchewan	0	Redondo Beach	f	f	Kenyon Brewer Dalton	496812192	a.feugiat.tellus@non.ca	Halee Miller Dixon	496812192	pede.Nunc.sed@morbitristique.edu	2009-11-03 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'akeem':1 'hill':2 'kelley':3	t	\N
138	J	UP	Ifeoma	Boone	Cortez	M	1999-07-06	91110679K	\N	f	P.O. Box 948, 8113 Nec St.	10	\N	18	9501	732334372	732334372	justo.nec@pellentesquemassalobortis.ca	0	Dr.	0	Santa Cruz	f	f	Bert Morse Howell	237432185	eu@malesuadaut.com	Alyssa Delaney Le	237432185	a.feugiat@magna.com	2008-02-29 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'boon':2 'cortez':3 'ifeom':1	t	\N
120	J	UP	Slade	Roberts	Velazquez	F	1998-01-26	30692113L	\N	f	3826 Vitae St.	6	\N	6	70439	316506797	316506797	pede@eget.com	0	Arkansas	0	Great Falls	f	f	Chaney Smith Nielsen	10994615	amet.consectetuer@pharetrafeliseget.com	Erin Good Adkins	10994615	Proin@pede.com	2004-07-16 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'roberts':2 'slad':1 'velazquez':3	t	\N
143	J	UP	Hedley	Dickerson	Parrish	M	1997-10-04	16178782A	\N	f	419-1466 Sit Rd.	10	\N	14	72230	533392259	533392259	libero@vel.ca	0	LAN	0	Mankato	f	f	Dorian Mcpherson Hyde	4270615	ut@Curabitur.com	Lavinia Simpson Sargent	4270615	Etiam@Suspendissecommodotincidunt.ca	2008-12-29 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'dickerson':2 'hedley':1 'parrish':3	t	\N
135	J	UP	Yuri	Cleveland	Tyson	F	1997-10-11	49048949V	\N	f	Ap #208-3491 Eget, St.	2	\N	6	85663	338183766	338183766	sed@metusVivamuseuismod.edu	0	WAT	0	Kansas City	f	f	Gary Harrison Hull	32354089	enim@hendrerit.com	Wendy Alford Booker	32354089	nisi.a.odio@et.edu	2006-07-30 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'cleveland':2 'tyson':3 'yuri':1	t	\N
86	J	UP	Aline	Acosta	Erickson	F	2003-03-13	40077094K	\N	f	841-9559 Felis St.	10	\N	24	14832	887003874	887003874	nisi@Integer.org	0	Overijssel	0	Nacogdoches	f	f	Kasper Bradley Henderson	374254260	leo@Donecconsectetuer.com	Xyla Trevino Rowe	374254260	vitae@dolor.edu	2003-12-31 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'acost':2 'alin':1 'erickson':3	t	\N
176	J	UP	Quynn	Griffin	Perkins	F	1993-04-25	55460848A	\N	f	P.O. Box 479, 1163 Nec St.	7	\N	10	83601	118379515	118379515	non.quam.Pellentesque@malesuada.edu	0	Limburg	0	Farmer City	f	f	Fuller Lawson Davenport	641774044	pede.ultrices.a@nectellusNunc.ca	Nevada Cline Everett	641774044	ornare.tortor.at@maurisidsapien.edu	2010-10-06 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'griffin':2 'perkins':3 'quynn':1	t	\N
164	J	UP	Jeremy	Ward	Myers	M	1990-10-10	66621536F	\N	f	Ap #142-2247 Pellentesque St.	10	\N	11	12157	934525101	934525101	ante@acrisus.org	0	South Dakota	0	Clearwater	f	f	Asher Weber Bean	178427659	facilisis.eget.ipsum@Curabiturmassa.com	Gisela Bradshaw Molina	178427659	dapibus.rutrum.justo@Fuscefermentumfermentum.ca	2004-03-21 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'jeremy':1 'myers':3 'ward':2	t	\N
170	J	UP	Xenos	Bradshaw	Finley	M	2005-06-06	29570622B	\N	f	P.O. Box 243, 2910 Erat, Av.	5	\N	1	75043	158771045	158771045	ligula.Aenean@enimNunc.ca	0	Lanarkshire	0	Orem	f	f	Tyler Sellers England	882534511	nibh.Donec@risusvarius.com	Kathleen Stanton Pruitt	882534511	elit.Nulla@nec.org	2003-02-15 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'bradshaw':2 'finley':3 'xen':1	t	\N
171	J	UP	Flavia	Delaney	Peterson	F	2003-01-09	65819185G	\N	f	Ap #414-6157 In, Rd.	6	\N	19	60221	88676925	88676925	Aenean.eget.magna@eget.edu	0	Noord Brabant	0	North Chicago	f	f	Lamar Schneider Whitaker	416113443	et.arcu.imperdiet@duiSuspendisse.com	Nayda Battle Collins	416113443	in.consequat.enim@FuscefeugiatLorem.com	2003-03-06 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'delaney':2 'flavi':1 'peterson':3	t	\N
129	J	UP	Fiona	Young	Hurst	M	1998-02-05	05059334A	\N	f	P.O. Box 703, 342 Donec Street	10	\N	2	37610	446563528	446563528	adipiscing.non.luctus@enimnectempus.org	0	Noord Holland	0	Rensselaer	f	f	Herman Horn Richards	95853542	pellentesque@gravidamaurisut.edu	Cassidy Bond Walter	95853542	sit.amet.faucibus@fermentumarcu.com	2003-01-20 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'fion':1 'hurst':3 'young':2	t	\N
146	J	UP	Stewart	Nunez	David	M	2006-01-29	88658604W	\N	f	Ap #750-6136 Ornare Rd.	8	\N	24	38204	894707828	894707828	Cras.dolor@blandit.edu	0	U.	0	Rialto	f	f	Daniel Sosa Morse	537283699	nec.euismod.in@Nunccommodo.com	Anne Blankenship Osborne	537283699	et@Nunc.com	2004-02-15 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'dav':3 'nunez':2 'stewart':1	t	\N
149	J	UP	Deanna	Spears	English	M	1997-02-11	22610300Z	\N	f	1791 Tortor. Av.	5	\N	1	39632	430853352	430853352	vestibulum.massa.rutrum@tempuseu.org	0	Saskatchewan	0	Green Bay	f	f	Jeremy Hughes Green	858391877	et.euismod.et@Phasellus.edu	Jennifer Blake Price	858391877	neque@velitjustonec.ca	2002-08-22 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'deann':1 'english':3 'spears':2	t	\N
233	K	UP	Joachim	Stuttgart	\N	M	1967-07-13	45737474X	\N	t	c/ Reyes Católicos	36	1	4	3003	966477367	966477367	joach@gmail.com	0	Alicante	0	Alicante	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 12:57:21.655759	\N	2011-08-10 16:31:43.5992	M,R	f	t	f	f	t	0	1	'joachim':1 'stuttgart':2	t	\N
70	J	UP	Kiara	Campos	Montoya	M	1998-04-13	00573898T	\N	f	425-361 Cubilia Street	3	\N	18	63800	599425990	599425990	hendrerit@CrasinterdumNunc.org	0	Quebec	0	Cudahy	f	f	Gavin Garrett Pratt	768159632	Nunc.mauris.elit@scelerisqueneque.com	Quinn Cooke Rivers	768159632	ut@nuncsed.ca	2010-01-28 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'camp':2 'kiar':1 'montoy':3	t	\N
83	J	UP	Mira	Blevins	Preston	F	1991-03-19	71355900H	\N	f	Ap #338-3427 Sit Avenue	2	\N	30	94937	158290458	158290458	molestie.tortor@ligula.com	0	New Hampshire	0	Alhambra	f	f	Neville Hutchinson Reed	256157735	Nulla@felis.org	Colette Hernandez Benton	256157735	Donec.est@tempus.org	2003-11-08 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'blevins':2 'mir':1 'preston':3	t	\N
64	J	UP	Amy	Johnson	Mccoy	M	1999-01-27	17482942A	\N	f	663-7251 Lacus, Ave	5	\N	13	72094	66544811	66544811	consectetuer.adipiscing.elit@tellus.org	0	NE	0	Malden	f	f	Hoyt Nguyen Patton	990412654	id.enim.Curabitur@Duis.ca	Nerea Perkins Olson	990412654	gravida@magnaDuis.org	2008-12-14 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'amy':1 'johnson':2 'mccoy':3	t	\N
179	J	UP	Jocelyn	Nichols	Stevenson	F	2002-01-14	63375934L	\N	f	Ap #344-9239 Nibh St.	4	\N	6	62243	4047553	4047553	a.ultricies@temporarcu.edu	0	District of Columbia	0	Bismarck	f	f	Shad Jones Hughes	628120126	ac.risus.Morbi@vitaesodalesnisi.com	Calista Riddle Sullivan	628120126	scelerisque@orci.ca	2009-07-22 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'jocelyn':1 'nichols':2 'stevenson':3	t	\N
163	J	UP	Frances	Mcconnell	Levine	M	1996-01-16	92034812G	\N	f	P.O. Box 498, 5044 Massa Av.	5	\N	14	99353	499551169	499551169	tellus.lorem.eu@metusVivamus.org	0	JSY	0	Huntsville	f	f	Timothy Chang Lowe	423985428	velit.eu@atpedeCras.ca	Shaeleigh Lloyd Booker	423985428	risus.quis@egestasurnajusto.org	2005-10-14 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'franc':1 'levin':3 'mcconnell':2	t	\N
177	J	UP	Fritz	Wagner	Ford	M	1994-04-17	97049704I	\N	f	1036 Pretium St.	2	\N	18	75252	163007624	163007624	arcu.Sed@tinciduntnibh.edu	0	WGM	0	Rancho Santa Margarita	f	f	Keane Pollard Gallegos	218678516	Nunc@amet.com	Bertha Wiley Thompson	218678516	faucibus.Morbi.vehicula@pedeCrasvulputate.org	2006-12-24 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'ford':3 'fritz':1 'wagn':2	t	\N
161	J	UP	Joelle	Downs	Gonzales	F	2000-05-15	85804575P	\N	f	P.O. Box 595, 6332 Non Road	1	\N	11	1512	216565709	216565709	Duis.gravida.Praesent@erat.org	0	Worcestershire	0	Connellsville	f	f	Cade Goodman Patel	181881197	auctor.vitae.aliquet@aliquamarcuAliquam.org	Ginger Terrell Todd	181881197	velit.eget.laoreet@consectetuer.org	2009-02-06 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'downs':2 'gonzal':3 'joell':1	t	\N
183	J	UP	Florence	Hays	Perez	M	1996-09-06	44837855O	\N	f	393-2840 Diam Rd.	1	\N	14	22762	528819833	528819833	Proin@arcuCurabiturut.com	0	Dr.	0	Chino Hills	f	f	Jerry Terrell Morse	362447839	velit.Cras@InfaucibusMorbi.org	Rachel Nielsen Gentry	362447839	magnis@purus.com	2006-09-24 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'florenc':1 'hays':2 'perez':3	t	\N
234	C	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 12:59:21.054396	\N	2011-08-10 16:31:43.5992	C,M,E,P,R	t	t	t	t	t	0	1	'jack':1 'pearl':3 'sparrow':2	t	\N
119	J	UP	Keiko	Howard	Barr	M	1991-09-07	80873621M	\N	f	Ap #942-6122 Vulputate Avenue	7	\N	29	11915	649129678	649129678	congue.In@tellusfaucibusleo.org	0	ID	0	Johnson City	f	f	Yardley Carney Whitley	578222927	lorem@consectetuermauris.ca	Celeste Battle Bray	578222927	dignissim.Maecenas.ornare@ipsumsodalespurus.edu	2007-03-22 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'barr':3 'howard':2 'keik':1	t	\N
165	J	UP	Noble	Conley	Hampton	F	1999-01-16	41898427J	\N	f	Ap #865-1952 Cras Avenue	7	\N	21	82221	280199120	280199120	mollis@dignissimMaecenasornare.ca	0	AB	0	Liberal	f	f	Ciaran Baxter Washington	987203565	tempor.arcu.Vestibulum@velit.edu	Kelsie Foley Coleman	987203565	egestas.blandit@nislNulla.ca	2004-10-18 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'conley':2 'hampton':3 'nobl':1	t	\N
14	C	UP	Jakeem	Garza	Stone	F	2001-08-29	82326185H	\N	f	397 Eu Av.	2	\N	19	92020	723672105	723672105	Quisque.ornare@luctus.edu	0	Jersey	0	Ashland	f	f	\N	\N	\N	\N	\N	\N	2010-05-29 00:00:00	\N	2011-08-10 16:31:43.5992		f	f	f	f	f	1	1	'garz':2 'jakeem':1 'ston':3	t	\N
173	J	UP	Hayfa	Robbins	Crane	M	1998-05-19	95719471H	\N	f	P.O. Box 656, 5727 Gravida. Avenue	10	\N	3	5814	534115264	534115264	turpis.Aliquam@tinciduntDonecvitae.edu	0	PE	0	Carson City	f	f	Ian Bruce Cannon	857275146	turpis@erat.ca	Zelda Waller Gallagher	857275146	Mauris@adipiscinglobortis.ca	2002-12-17 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'cran':3 'hayf':1 'robbins':2	t	\N
162	J	UP	Kirk	Burgess	Rowland	F	2000-10-25	07045244G	\N	f	470-864 Curabitur Street	2	\N	5	55722	254967606	254967606	Quisque.libero.lacus@amet.edu	0	NB	0	Evanston	f	f	Justin Cantu Mcfadden	570616472	turpis@Suspendisse.org	Ayanna Silva Schneider	570616472	sem.Nulla.interdum@mollis.edu	2002-10-06 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'burgess':2 'kirk':1 'rowland':3	t	\N
191	J	UP	Demetria	Pearson	Mccall	M	1999-07-29	39269132V	\N	f	6542 Sem, Avenue	10	\N	26	52163	139289730	139289730	massa.Quisque.porttitor@Morbimetus.com	0	ABD	0	Monroe	f	f	Raphael Wong Fisher	512576309	Sed@bibendumsedest.edu	Alana Christensen Woodward	512576309	tincidunt@cursus.com	2006-11-20 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'demetri':1 'mccall':3 'pearson':2	t	\N
136	J	UP	Sharon	Carter	Durham	M	1999-10-10	06369324O	\N	f	P.O. Box 258, 1503 Sociis Ave	3	\N	21	15360	988522022	988522022	lorem.vehicula@malesuada.edu	0	Montana	0	Flagstaff	f	f	Giacomo Rodriguez Albert	751141639	risus.at@molestie.com	Urielle Bruce Mcneil	751141639	et.euismod.et@eteuismodet.org	2003-06-22 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'cart':2 'durham':3 'sharon':1	t	\N
117	J	UP	Teegan	Mays	Davenport	M	2001-11-17	58500421Y	\N	f	6635 Primis Street	1	\N	29	24453	100498935	100498935	ipsum@turpisNulla.ca	0	Friesland	0	Selma	f	f	Igor Armstrong Noble	518543519	nibh.sit@tellusimperdietnon.ca	Teegan Kim Dillard	518543519	mattis@libero.ca	2010-10-13 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'davenport':3 'mays':2 'teeg':1	t	\N
66	J	UP	Ria	Stanton	Roth	F	2005-02-10	67483088O	\N	f	2014 Tortor, Av.	3	\N	20	58845	625044801	625044801	est.tempor.bibendum@euplacerat.edu	0	Maine	0	Fitchburg	f	f	Jasper Maddox Graves	922363266	Morbi.vehicula@magna.org	Ima Hardy Reynolds	922363266	metus.Vivamus@dignissimMaecenasornare.com	2005-10-17 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'ria':1 'roth':3 'stanton':2	t	\N
106	J	UP	Clementine	Tyson	Cantu	F	1992-04-21	34371428T	\N	f	P.O. Box 608, 6398 Risus Ave	10	\N	30	71390	267100195	267100195	Curabitur.vel@Cras.com	0	Utah	0	Gallup	f	f	Basil Herring Padilla	721859371	Nulla@vitae.edu	Cynthia Ray Miranda	721859371	leo.in.lobortis@laoreet.ca	2009-10-07 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'cantu':3 'clementin':1 'tyson':2	t	\N
93	J	UP	Petra	Coffey	Hardy	M	2003-01-13	70011434J	\N	f	129-8871 Est St.	2	\N	1	485	441718644	441718644	quam@congueInscelerisque.com	0	MB	0	Evansville	f	f	Rudyard Phelps Cummings	267439304	auctor.velit@Donecelementumlorem.org	Shay Carter Beck	267439304	nulla.magna.malesuada@nulla.edu	2009-09-21 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'coffey':2 'hardy':3 'petr':1	t	\N
200	J	UP	Clementine	Stanley	Rollins	M	2000-02-27	92378383C	\N	f	Ap #105-3639 Consequat Av.	9	\N	13	43032	898386206	898386206	nascetur@suscipitnonummyFusce.com	0	Co. Kildare	0	Fort Collins	f	f	Ashton Stein Cole	440527869	commodo.ipsum@dolor.com	Illana Torres Lott	440527869	odio.a@Cumsociisnatoque.com	2009-01-17 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'clementin':1 'rollins':3 'stanley':2	t	\N
31	J	UP	Adena	Sandoval	Morrow	F	1995-05-07	57670581E	\N	f	P.O. Box 814, 9564 Integer St.	10	\N	4	51514	555447007	555447007	dolor@urnaNunc.edu	0	MN	0	Roseville	f	f	Joshua Cunningham Greene	4810139	purus@fringillami.ca	Morgan Poole Knowles	4810139	turpis.Aliquam.adipiscing@sodalesnisi.edu	2006-02-15 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'aden':1 'morrow':3 'sandoval':2	t	\N
159	J	UP	Roanna	Kerr	Barrett	F	1996-04-16	57195019B	\N	f	Ap #828-7368 Donec Street	2	\N	13	43502	974333404	974333404	egestas@adipiscingligulaAenean.org	0	Utrecht	0	Moreno Valley	f	f	Gary Mclean Rodriquez	548842389	eu@Suspendisse.ca	Olivia Bush Rosales	548842389	enim.Curabitur@sed.ca	2005-10-20 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'barrett':3 'kerr':2 'roann':1	t	\N
188	J	UP	Jada	Quinn	Neal	M	1998-06-24	85581834W	\N	f	P.O. Box 848, 1273 Dictum Street	5	\N	15	78287	407700361	407700361	arcu.Morbi@ornareplaceratorci.org	0	Minnesota	0	Cohoes	f	f	Cedric Lyons Mack	502425053	Aenean.eget.magna@aliquetlibero.edu	Amaya Raymond Gregory	502425053	tellus.Aenean.egestas@arcu.org	2007-11-25 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'jad':1 'neal':3 'quinn':2	t	\N
192	J	UP	Pascale	Patrick	Haynes	F	1996-02-24	57251423Z	\N	f	P.O. Box 606, 8320 Non, Av.	10	\N	14	11638	197877179	197877179	Aenean.massa.Integer@laciniaorci.ca	0	LA	0	Pasco	f	f	Rahim Fulton Atkinson	921513941	montes.nascetur@fringillaeuismodenim.edu	Yeo Workman Gillespie	921513941	Duis.volutpat@FuscemollisDuis.com	2008-07-28 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'hayn':3 'pascal':1 'patrick':2	t	\N
128	J	UP	Jermaine	Lara	Molina	M	2004-11-14	48585868W	\N	f	Ap #680-1226 Fringilla Av.	4	\N	7	94778	975877631	975877631	molestie.orci.tincidunt@loremfringillaornare.edu	0	NB	0	Nevada City	f	f	Brenden Petty Atkins	308502373	Nam@sociisnatoquepenatibus.org	Mari Kane Reynolds	308502373	urna@ami.com	2003-06-23 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'jermain':1 'lar':2 'molin':3	t	\N
125	J	UP	Josiah	Taylor	Garrison	F	1995-03-29	12729876J	\N	f	Ap #886-3304 Dictum Av.	10	\N	13	1066	240372738	240372738	sem.magna@ipsumSuspendisse.com	0	SRY	0	Dover	f	f	Dillon Wiggins Mooney	548548071	Vivamus@risusvarius.com	Justine Barker Foreman	548548071	porttitor@Etiam.com	2007-02-02 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'garrison':3 'josiah':1 'taylor':2	t	\N
131	J	UP	Denton	Golden	Beasley	M	2000-09-21	53973626M	\N	f	537-5961 A, St.	8	\N	18	54329	22552135	22552135	ligula.Aenean@faucibusutnulla.com	0	MO	0	Covington	f	f	Amos Bolton Hooper	42022297	in.molestie@ad.ca	Kirby Holcomb Phillips	42022297	egestas@acmattis.edu	2003-04-17 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'beasley':3 'denton':1 'gold':2	t	\N
236	J	UP	Pilar	Fernandez	Altava	F	1990-01-12	\N	\N	f	Juan de Austria	5	\N	\N	12005	\N	\N	\N	0	Alicante	0	Guardamar del Segura	t	f	\N	\N	\N	\N	\N	\N	2010-10-02 13:01:30.514014	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	1	1	'altav':3 'fernandez':2 'pil':1	t	\N
52	J	UP	Laurel	Faulkner	Hodges	F	2001-06-21	17782603U	\N	f	1630 Dictum St.	6	\N	30	74702	917249370	917249370	pellentesque@eu.com	0	CT	0	Diamond Bar	f	f	Ulric Conley Livingston	751560658	et.commodo@inconsectetueripsum.ca	Hadley Trevino Logan	751560658	mi.eleifend@nec.com	2004-02-06 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'faulkn':2 'hodg':3 'laurel':1	t	\N
37	J	UP	Miranda	Lawrence	King	M	1993-02-26	65586072A	\N	f	765-716 Vitae Rd.	2	\N	4	52166	589800189	589800189	dis.parturient.montes@Proinvelarcu.edu	0	Manitoba	0	La Puente	f	f	Raymond Collins Paul	918390214	pretium@acurna.org	Amelia Gordon Giles	918390214	mus@vitaeorciPhasellus.com	2004-10-02 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'king':3 'lawrenc':2 'mirand':1	t	\N
45	J	UP	Gil	Pedro	Mcbride	M	1993-09-09	87462770R	\N	f	250-6707 Dolor. Street	6	\N	13	37590	759704292	759704292	convallis@molestiepharetranibh.edu	0	Delaware	0	Fargo	f	f	Aquila Horne Fisher	163792853	Vivamus.euismod@erosProinultrices.ca	Sara Willis Eaton	163792853	convallis@Fuscedolor.org	2005-03-07 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	1	1	'gil':1 'mcbrid':3 'pedr':2	t	\N
26	J	UP	Calvin	Simmons	Duncan	F	2003-10-18	80885898C	\N	f	P.O. Box 382, 3385 Consectetuer St.	7	\N	2	89847	892773680	892773680	enim@venenatis.com	0	WMD	0	Hamilton	f	f	Lee Humphrey Landry	67446940	neque@neque.ca	Mallory Gaines Wilcox	67446940	non@quisarcuvel.edu	2009-08-24 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'calvin':1 'dunc':3 'simmons':2	t	\N
4	K	UP	Haley	Cochran	Hart	M	2002-09-01	48743870H	\N	f	848-7468 Nisi Road	10	\N	15	94128	729483954	729483954	magna.nec@semPellentesque.edu	0	QCLK	0	Starkville	f	f	\N	\N	\N	\N	\N	\N	2003-11-26 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	1	1	'cochr':2 'haley':1 'hart':3	t	\N
40	J	UP	Rina	Hewitt	Rodriquez	M	2003-09-29	18085320Z	\N	f	8879 Euismod Ave	3	\N	3	99633	161375855	161375855	Fusce.mi@risusIn.ca	0	Strathclyde	0	Helena	f	f	Amery York Nolan	766720662	lacinia.Sed@torquentperconubia.org	Aileen Moses Willis	766720662	arcu.ac@feugiatSed.org	2006-06-07 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'hewitt':2 'rin':1 'rodriquez':3	t	\N
46	J	UP	Axel	Cantrell	Warner	F	2001-12-11	18882118M	\N	f	5626 Sed Av.	1	\N	7	12320	576766194	576766194	ipsum@Sedmolestie.edu	0	Lanarkshire	0	East St. Louis	f	f	Jonah Hull Sheppard	508961642	amet.consectetuer.adipiscing@duilectusrutrum.ca	Quail Robinson Blake	508961642	libero@nec.ca	2009-07-07 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'axel':1 'cantrell':2 'warn':3	t	\N
16	J	UP	Lee	Campbell	Mason	F	2002-03-29	91853777J	\N	f	Ap #336-4917 Duis Street	7	\N	21	43120	99189847	99189847	scelerisque@Suspendisseeleifend.org	0	Noord Holland	0	Florence	f	f	Todd Rhodes Trevino	494500529	et.pede@velit.com	Gemma Trujillo Cline	494500529	Integer.urna@blanditviverra.edu	2006-12-26 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'campbell':2 'lee':1 'mason':3	t	\N
22	J	UP	Autumn	Collins	Robinson	F	1998-03-04	07417887N	\N	f	2884 Arcu. Ave	9	\N	11	98837	221930892	221930892	a.odio.semper@elementumdui.com	0	Peebles-shire	0	Jackson	f	f	Tanek Buchanan Hurley	559649094	dictum@dui.edu	Olga Vega Reynolds	559649094	ipsum@fringillacursus.com	2010-02-22 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'autumn':1 'collins':2 'robinson':3	t	\N
48	J	UP	Brent	Duncan	Kidd	F	1993-09-28	69825903N	\N	f	635-6137 Amet Rd.	3	\N	2	79813	850085943	850085943	feugiat.tellus.lorem@odioNam.com	0	Mississippi	0	Sutter Creek	f	f	Keefe Stevenson Macdonald	756162650	risus.Donec@aliquetdiam.org	Liberty Payne Petty	756162650	blandit.at.nisi@egetmassaSuspendisse.edu	2010-10-13 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'brent':1 'dunc':2 'kidd':3	t	\N
60	J	UP	Jeremy	Perry	Reyes	F	1994-12-10	65501632L	\N	f	2942 Conubia St.	5	\N	15	25192	806456940	806456940	fames@iaculisodioNam.com	0	Ontario	0	Shelton	f	f	Colin Barber Good	540874450	rutrum@atarcuVestibulum.edu	Portia Cherry Fields	540874450	non@Duisdignissimtempor.com	2006-11-21 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'jeremy':1 'perry':2 'rey':3	t	\N
43	J	UP	Sydnee	Peterson	Pruitt	F	1992-08-20	09694667K	\N	f	Ap #559-9456 Habitant Rd.	5	\N	28	96766	241509136	241509136	aptent.taciti.sociosqu@eliteratvitae.ca	0	Quebec	0	Florence	f	f	Ashton Mccullough Cabrera	739072850	montes.nascetur.ridiculus@liberonec.com	Pamela Fulton Rodgers	739072850	tellus@Craslorem.org	2006-02-03 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'peterson':2 'pruitt':3 'sydne':1	t	\N
47	J	UP	Idona	Weber	Porter	M	2005-01-23	83241648V	\N	f	Ap #584-8938 Venenatis Rd.	7	\N	22	72389	18763640	18763640	congue@pede.com	0	New Brunswick	0	Peekskill	f	f	Evan Kidd Vaughn	568499574	neque.Nullam@orciin.ca	Lydia Stephens Rice	568499574	morbi@suscipitest.edu	2005-04-23 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'idon':1 'port':3 'web':2	t	\N
49	J	UP	Bruno	Norris	Hancock	M	2003-04-25	34934941H	\N	f	P.O. Box 127, 5973 Nunc. Rd.	10	\N	5	91027	910779226	910779226	Aliquam@diamnunc.com	0	Flevoland	0	Ketchikan	f	f	Benedict Mcleod Goodman	615964009	et.arcu@gravida.com	Fay Pace Mcintosh	615964009	augue.ac@odio.org	2003-12-20 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'brun':1 'hancock':3 'norris':2	t	\N
53	J	UP	Noelle	Oneil	Acevedo	M	2003-10-26	62893790P	\N	f	P.O. Box 150, 3259 Ornare, St.	1	\N	11	50167	624416955	624416955	vitae@gravidanuncsed.ca	0	MB	0	Bowie	f	f	Emmanuel Schwartz Mullins	335261209	neque.venenatis@Nunccommodoauctor.edu	Inez Atkinson Edwards	335261209	tincidunt@ullamcorperDuiscursus.ca	2010-01-19 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'aceved':3 'noell':1 'oneil':2	t	\N
42	J	UP	Holly	Simpson	Harper	F	2004-10-13	07323388S	\N	f	Ap #231-9738 Bibendum Rd.	7	\N	22	36810	724848576	724848576	elementum.dui@risusNulla.ca	0	Limburg	0	Alpharetta	f	f	Leo Sherman Mcknight	496993260	Suspendisse.commodo@etarcu.org	Elaine Moreno Richard	496993260	Duis.cursus@mattissemper.edu	2010-11-23 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	1	1	'harp':3 'holly':1 'simpson':2	t	\N
8	K	UP	Caleb	Huffman	Lamb	M	1997-10-12	38456878U	\N	f	Ap #228-7428 Eleifend Rd.	10	\N	10	78704	230981644	230981644	fermentum@ipsumacmi.ca	0	Gr.	0	Laguna Niguel	f	f	\N	\N	\N	\N	\N	\N	2006-03-13 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	2	1	'caleb':1 'huffm':2 'lamb':3	t	\N
50	J	UP	Ivy	Ingram	Pollard	M	1994-09-10	36850318Q	\N	f	P.O. Box 596, 3653 In Road	6	\N	5	57923	132549976	132549976	mi.fringilla@a.edu	0	Nova Scotia	0	Gu√°nica	f	f	Conan Gibson Knight	372612135	parturient.montes@CurabiturdictumPhasellus.com	Abigail Garner Robinson	372612135	ullamcorper.eu@pellentesqueeget.org	2005-10-18 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'ingram':2 'ivy':1 'pollard':3	t	\N
28	J	UP	Igor	Vega	Hopkins	M	1990-12-22	84047548N	\N	f	394-585 Libero Street	5	\N	19	60833	688628496	688628496	vel@metusInnec.com	0	DC	0	Gardena	f	f	Jamal Roberts Mills	297953625	Proin.eget@orci.com	Lara Mccormick Wagner	297953625	enim.diam.vel@orci.org	2010-08-05 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'hopkins':3 'igor':1 'veg':2	t	\N
57	J	UP	Talon	Morin	Hinton	M	1994-06-16	12979921K	\N	f	P.O. Box 476, 403 Ultrices. Ave	1	\N	23	39463	666926323	666926323	litora.torquent@necligula.ca	0	Limburg	0	Middlebury	f	f	Slade Newman Ross	502475560	Donec.fringilla.Donec@non.org	Rachel Buckley Hood	502475560	Phasellus@auctorullamcorper.com	2005-04-19 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'hinton':3 'morin':2 'talon':1	t	\N
36	J	UP	Amela	Haney	Cardenas	F	1995-08-07	75238191M	\N	f	5784 Orci. Ave	1	\N	17	96716	965183191	965183191	lectus.pede@faucibus.com	0	Groningen	0	Arcadia	f	f	Bruno Boyle Ferguson	469558529	placerat.eget.venenatis@Inscelerisquescelerisque.org	Roanna Schultz Hawkins	469558529	condimentum@Donecsollicitudinadipiscing.edu	2008-01-24 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'amel':1 'carden':3 'haney':2	t	\N
27	J	UP	Ora	Fitzgerald	Howard	M	2000-09-17	46465567E	\N	f	Ap #867-1041 Sed St.	2	\N	17	72644	665229882	665229882	quis.turpis.vitae@tortordictum.edu	0	NL	0	Fairfax	f	f	Gray Bender Rowe	442200281	sagittis@Fusce.org	Molly Phelps Cooke	442200281	ipsum@accumsanconvallis.ca	2009-03-20 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'fitzgerald':2 'howard':3 'ora':1	t	\N
38	J	UP	Brian	Holloway	Joyner	F	2004-03-12	63089810C	\N	f	Ap #236-8398 Amet Rd.	6	\N	28	66921	511693396	511693396	Vivamus.sit@ligula.org	0	Zuid Holland	0	Santa Clara	f	f	Orlando Nichols Adkins	577437267	malesuada@nonummyac.edu	Indira Miller Curtis	577437267	arcu.et.pede@mattis.org	2008-03-12 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'bri':1 'holloway':2 'joyn':3	t	\N
19	J	UP	Stone	Gould	Weeks	F	1993-09-21	16728714U	\N	f	Ap #923-9270 Fringilla, Avenue	8	\N	26	15394	216622640	216622640	nulla.Donec.non@sitametmetus.org	0	Nova Scotia	0	Lockport	f	f	Lane Harrison Cash	852598020	eu.nibh.vulputate@sitametante.com	Brynne Randall Burton	852598020	facilisi@Donecest.org	2008-05-27 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'gould':2 'ston':1 'weeks':3	t	\N
41	J	UP	Ina	Trevino	Gonzalez	M	2000-04-04	68540626S	\N	f	3951 Leo, Rd.	1	\N	9	50306	579939909	579939909	aliquet.metus.urna@mauris.edu	0	NU	0	Passaic	f	f	Chaney Holden Parks	315490811	tellus@Donec.org	Destiny Richards Parsons	315490811	sit@vitaemauris.ca	2003-03-02 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'gonzalez':3 'ina':1 'trevin':2	t	\N
33	J	UP	Jenna	Barker	Ayala	F	1993-07-03	45219933R	\N	f	P.O. Box 790, 7641 Mauris St.	6	\N	16	90525	282581674	282581674	lectus@atlacusQuisque.org	0	N.-H.	0	Vicksburg	f	f	Clinton Callahan Forbes	267427736	nisi.Mauris@velvenenatisvel.ca	Emma Ortega Mccullough	267427736	Nam.consequat.dolor@lectuspede.com	2005-11-08 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'ayal':3 'bark':2 'jenn':1	t	\N
58	J	UP	Lacy	Charles	Watts	F	2002-07-04	55744594H	\N	f	P.O. Box 773, 8862 Sociis St.	6	\N	17	91755	232729500	232729500	dapibus.quam@convallisligulaDonec.edu	0	WIS	0	Victoria	f	f	Brennan Frost Kennedy	874674888	enim@ultriciesornare.edu	Donna Barrera York	874674888	sem.Pellentesque.ut@nonhendrerit.edu	2004-04-05 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'charl':2 'lacy':1 'watts':3	t	\N
7	K	UP	Jane	Mathews	Sampson	F	2001-02-05	81833722K	\N	f	556-9086 Lacus. Road	1	\N	24	91137	12345656	12345656	\N	0	Co. Offaly	0	Caguas	f	f	\N	\N	\N	\N	\N	\N	2004-01-28 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	7	1	'jan':1 'mathews':2 'sampson':3	t	\N
32	J	UP	Linus	Freeman	Warner	M	2003-12-10	73115020X	\N	f	225-1409 A, Road	5	\N	24	79224	65817285	65817285	Donec@sitametlorem.ca	0	Zld.	0	Brockton	f	f	Wang Wolf Barry	801507418	tempor.lorem@Integerid.edu	Sopoline Douglas George	801507418	mi.ac@aliquamenim.org	2004-09-18 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'freem':2 'linus':1 'warn':3	t	\N
55	J	UP	Maryam	Kelly	Knight	F	2004-02-16	88147771U	\N	f	P.O. Box 392, 1219 Eu Street	6	\N	17	84846	567373971	567373971	sem@luctus.org	0	QC	0	Columbia	f	f	Brenden Burke Waters	399540695	lorem@semper.com	MacKenzie Wolfe Hays	399540695	aliquet@sodales.org	2004-10-23 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'kelly':2 'knight':3 'maryam':1	t	\N
39	J	UP	Nola	Santos	Sullivan	M	1992-07-29	63089811C	\N	f	P.O. Box 301, 964 Semper Road	9	\N	29	66569	190477413	190477413	lorem.ut.aliquam@pedeCumsociis.edu	0	NLV	0	Ardmore	f	f	Gage Luna Ruiz	190098209	aliquet.vel@Quisqueliberolacus.edu	Ashely	190098209	scelerisque.neque.sed@massa.com	2006-04-19 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	1	1	'nol':1 'sant':2 'sulliv':3	t	\N
51	J	UP	Donovan	Woods	Wong	F	1991-07-03	59708188N	\N	f	Ap #912-3533 Lectus Rd.	2	\N	15	99445	630664167	630664167	porttitor.vulputate.posuere@rhoncusDonec.com	0	Zeeland	0	Torrington	f	f	Chase Barton Duncan	713317593	ut.molestie@metusurnaconvallis.org	Britanni Humphrey Orr	713317593	arcu.Curabitur@aliquet.ca	2004-05-01 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'donov':1 'wong':3 'woods':2	t	\N
56	J	UP	Joel	Sosa	Williams	M	1994-03-31	14987218M	\N	f	Ap #285-2830 Nec, Ave	8	\N	11	14656	752863723	752863723	semper@luctus.ca	0	Noord Brabant	0	Warren	f	f	Jonas Wise Hood	537914722	erat.neque@Maurisnon.org	Alma Moore Whitehead	537914722	libero.Proin.sed@orci.edu	2005-12-22 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'joel':1 'sos':2 'williams':3	t	\N
29	J	UP	Lamar	Fleming	Sellers	F	2004-11-29	80702669T	\N	f	2762 Sit Ave	8	\N	29	58748	38233998	38233998	egestas.lacinia@eu.org	0	AB	0	Truth or Consequences	f	f	Reese Austin Odonnell	522569118	laoreet@at.edu	Nichole Santos Bruce	522569118	a.arcu.Sed@eros.com	2010-07-14 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'fleming':2 'lam':1 'sellers':3	t	\N
30	J	UP	Audra	Patton	Murray	M	1996-06-27	48081621V	\N	f	Ap #842-5423 Molestie Ave	4	\N	18	66894	766847515	766847515	imperdiet.nec@at.org	0	NY	0	Santa Cruz	f	f	Ulric Chan Lott	701728325	ultrices.posuere.cubilia@morbitristique.edu	Mara Anderson Logan	701728325	a.dui@tristiquesenectuset.ca	2010-02-15 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'audr':1 'murray':3 'patton':2	t	\N
13	K	UP	Amery	Pittman	Mcintyre	F	1920-11-10	20735651F	\N	f	3595 Donec St.	10	\N	4	74391	313349146	313349146	Nullam@malesuadavel.edu	0	Valencia	0	Oklahoma City	f	f	\N	\N	\N	\N	\N	\N	2010-04-19 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	10	1	'amery':1 'mcintyr':3 'pittm':2	t	\N
15	C	UP	Dennis	Norris	Gates	F	1999-04-30	36400502Z	\N	f	P.O. Box 196, 2779 Tempor Av.	3	\N	9	57793	539162715	539162715	ante.Vivamus.non@nondapibus.ca	0	MBI	0	Montpelier	f	f	\N	\N	\N	\N	\N	\N	2009-09-07 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	2	1	'dennis':1 'gat':3 'norris':2	t	\N
59	J	UP	Nash	Patel	Davenport	M	1997-04-06	58333576X	\N	f	195-2229 Eu Av.	2	\N	4	33716	325838368	325838368	molestie.Sed@nasceturridiculus.edu	0	Dr.	0	Sioux Falls	f	f	Zeus Kelley Stone	363808751	eu.enim@Proin.ca	Inga Fitzgerald Bean	363808751	eu@Curabitursed.com	2002-07-12 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'davenport':3 'nash':1 'patel':2	t	\N
5	K	UP	Laurel	Burke	Calhoun	M	2001-06-15	99510291D	\N	f	Ap #191-7151 Luctus Avenue	7	\N	21	25578	887813999	887813999	fdsf@dasg.com	0	Zeeland	0	New Haven	f	f	\N	\N	\N	\N	\N	\N	2004-03-21 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	1	1	'burk':2 'calhoun':3 'laurel':1	t	\N
44	J	UP	Jasper	Mccarty	Parsons	M	2002-10-02	56363805C	\N	f	P.O. Box 554, 3753 Ligula Road	5	\N	5	44368	323539602	323539602	quam.vel@ad.org	0	Alberta	0	Lahaina	f	f	Igor Lambert Johns	454391065	Donec@tellusfaucibus.edu	Willa Shaffer Bryan	454391065	vestibulum.neque.sed@Integervulputate.edu	2006-10-28 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'jasp':1 'mccarty':2 'parsons':3	t	\N
81	J	UP	Neve	Page	Whitfield	M	2000-03-06	57909328Q	\N	f	968-2495 Nulla St.	5	\N	7	64238	207609863	207609863	mi@non.edu	0	Zeeland	0	Rancho Cordova	f	f	Neville Holden Chen	241677523	sed.facilisis.vitae@velitduisemper.edu	Claudia Norman Lucas	241677523	nec.tellus.Nunc@consectetuer.edu	2004-09-08 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'nev':1 'pag':2 'whitfield':3	t	\N
34	J	UP	Geraldine	Shannon	Mcmillan	F	2005-10-12	83012915P	\N	f	8010 Tempor St.	1	\N	19	17500	653295427	653295427	non.sapien.molestie@Etiamimperdietdictum.com	0	Manitoba	0	San Bernardino	f	f	Asher Sellers Cruz	46355398	Mauris.vestibulum.neque@tinciduntcongueturpis.edu	Sophia Hendrix Farrell	46355398	rhoncus@tortorNunc.edu	2008-06-16 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'geraldin':1 'mcmill':3 'shannon':2	t	\N
199	J	UP	Kameko	Ayala	Mcdowell	F	1997-09-07	40717724G	\N	f	P.O. Box 135, 5142 Blandit Ave	8	\N	12	32240	186509202	186509202	velit@dapibusgravida.com	0	DOR	0	Mequon	f	f	Xenos Rasmussen Spencer	356028669	eros.non@auctorMaurisvel.org	Cheyenne Alvarez Everett	356028669	faucibus@condimentumeget.org	2004-11-19 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'ayal':2 'kamek':1 'mcdowell':3	t	\N
189	J	UP	Alea	Alexander	Beach	F	1996-05-28	00935779Q	\N	f	Ap #123-1362 Ridiculus Ave	4	\N	19	85225	155971478	155971478	nulla@sagittisaugueeu.edu	0	New Brunswick	0	Monterey	f	f	Ulric Sloan Alston	80999248	magna.Duis@nisidictum.ca	Jolie Booker Douglas	80999248	arcu@Quisque.com	2002-06-05 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'ale':1 'alexand':2 'beach':3	t	\N
190	J	UP	Kieran	Brady	Curtis	F	2002-07-31	48053733M	\N	f	8326 Aliquet Road	8	\N	5	321	169179073	169179073	dolor.Fusce.feugiat@SuspendisseduiFusce.com	0	Noord Brabant	0	Brockton	f	f	Allen Jennings Maxwell	331376046	hendrerit@acmieleifend.org	Aphrodite Ruiz Schmidt	331376046	mi.pede@Proin.edu	2005-11-14 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'brady':2 'curtis':3 'kier':1	t	\N
197	J	UP	Yetta	Faulkner	Hubbard	M	1992-03-22	97620053T	\N	f	5633 Erat, Avenue	7	\N	30	53686	568136473	568136473	ornare@Nullasempertellus.ca	0	WI	0	New Kensington	f	f	Honorato Erickson Hensley	767283334	mollis@consectetueripsum.edu	Robin Summers Evans	767283334	Nunc@velitdui.ca	2008-01-03 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'faulkn':2 'hubbard':3 'yett':1	t	\N
187	J	UP	Caesar	Lara	Ewing	M	1996-12-14	32492353T	\N	f	1086 Vehicula. Rd.	1	\N	30	36885	693757085	693757085	sapien.Aenean@nuncQuisque.com	0	N.-H.	0	Two Rivers	f	f	Adam Delacruz Mcgowan	66216598	ut@Morbiaccumsan.org	Medge Brady Hayes	66216598	orci@utpharetrased.org	2008-05-06 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'caes':1 'ewing':3 'lar':2	t	\N
160	J	UP	Ira	Nelson	Miranda	F	1995-08-28	56970930O	\N	f	P.O. Box 532, 9565 Eros St.	10	\N	7	98517	71317188	71317188	neque.Nullam@sagittisDuis.edu	0	L.	0	Saint Paul	f	f	Ishmael Todd Cline	188049149	ornare.egestas.ligula@in.com	Iliana Horne Gallegos	188049149	commodo@tinciduntDonec.edu	2010-08-31 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'ira':1 'mirand':3 'nelson':2	t	\N
181	J	UP	Chastity	Pickett	Briggs	F	2002-01-27	46223943M	\N	f	9237 Orci. Street	9	\N	5	84759	825980550	825980550	adipiscing@velitCras.edu	0	Yukon	0	Port Orford	f	f	Neil Molina Stewart	645486777	tincidunt.nunc@orci.com	Christen Hicks Hendricks	645486777	40489	2009-07-24 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'briggs':3 'chastity':1 'pickett':2	t	\N
168	J	UP	Colleen	Rodriguez	Hunt	F	1998-03-13	76035665A	\N	f	5321 Neque Avenue	8	\N	19	1453	442531367	442531367	facilisis@massalobortis.com	0	Co. Dublin	0	Fitchburg	f	f	Trevor David Murphy	746309585	tempor.diam@risusNullaeget.ca	Bianca Payne Chambers	746309585	lectus.a.sollicitudin@duiCumsociis.org	2005-09-18 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'coll':1 'hunt':3 'rodriguez':2	t	\N
124	J	UP	Yardley	Bruce	Hunter	M	1997-03-30	57190043I	\N	f	Ap #718-3166 Dui Street	8	\N	15	13501	486002764	486002764	enim.condimentum@netus.org	0	Saskatchewan	0	Agat	f	f	David Casey Good	416327428	penatibus.et.magnis@Etiamligula.org	Martha Nunez Gutierrez	416327428	nec.malesuada.ut@etultrices.edu	2008-06-30 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'bruc':2 'hunt':3 'yardley':1	t	\N
137	J	UP	Armando	Petty	Snider	F	2001-10-02	62510575H	\N	f	P.O. Box 101, 3543 Nonummy St.	9	\N	4	45567	918856857	918856857	tellus.Aenean@orcilacusvestibulum.org	0	Perth	0	Turlock	f	f	Vaughan Collier Barker	620223579	dui.Cras.pellentesque@porttitor.edu	Jolene Rivers Osborne	620223579	odio.Aliquam.vulputate@blanditat.org	2002-11-20 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'armand':1 'petty':2 'snid':3	t	\N
65	J	UP	Astra	Vargas	Luna	F	1993-12-31	87430892U	\N	f	P.O. Box 217, 3792 In Road	4	\N	12	57919	149195862	149195862	amet.diam@temporeratneque.org	0	YT	0	Alpharetta	f	f	Valentine Payne Newton	184903433	ut@ornaretortor.ca	Nyssa Patton Rosales	184903433	Curabitur@penatibus.ca	2005-09-14 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'astra':1 'lun':3 'varg':2	t	\N
87	J	UP	Amery	Campbell	Wooten	M	1997-10-26	08738279R	\N	f	716-2066 Quis, Av.	7	\N	16	32189	848109508	848109508	Donec.fringilla@nuncsed.ca	0	L.	0	Springdale	f	f	Kane Swanson Mcneil	689350394	enim@Cras.com	Riley Barrett Holloway	689350394	eu.euismod@ultricesVivamus.edu	2006-10-25 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'amery':1 'campbell':2 'woot':3	t	\N
130	J	UP	Sasha	Boyle	Robbins	M	2001-07-03	71208917N	\N	f	P.O. Box 634, 1186 Aliquet Av.	7	\N	29	96483	887221942	887221942	augue@adui.org	0	MER	0	roñado	t	t	Cole Waters Lawrence	433937377	vitae.orci@egestasrhoncusProin.com	Scarlett Grant Harris	433937377	libero.est.congue@mauriselit.edu	2009-02-10 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	6	1	'boyl':2 'robbins':3 'sash':1	t	\N
61	J	UP	Irene	Macdonald	Ewing	F	1995-06-09	46010538O	\N	f	6719 Ultrices St.	2	\N	24	68435	404416453	404416453	feugiat@risus.com	0	Co. Carlow	0	Diamond Bar	f	f	Carson Jimenez Dejesus	501002735	est@Classaptenttaciti.org	Zoe Sweeney Mcmahon	501002735	Pellentesque@arcuCurabitur.ca	2010-06-12 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'ewing':3 'iren':1 'macdonald':2	t	\N
2	C	UP	John	Lewis	Brennan	M	2002-04-20	88053974X	\N	f	649-7187 Lorem Road	1	\N	28	94524	580727338	580727338	\N	0	British Columbia	0	Kalamazoo	f	f	\N	\N	\N	\N	\N	\N	2007-10-27 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	7	1	'brenn':3 'john':1 'lewis':2	t	\N
79	J	UP	Shaine	Stein	Nielsen	F	1994-05-08	10475428E	\N	f	P.O. Box 259, 217 Vitae Ave	9	\N	20	4275	682499487	682499487	sociis.natoque@interdumNuncsollicitudin.edu	0	Zld.	0	Rhinelander	f	f	Nathaniel Whitley Dyer	398508640	metus@egestasSed.com	Briar Craig Becker	398508640	lorem.vehicula.et@dui.edu	2007-03-06 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'niels':3 'shain':1 'stein':2	t	\N
91	J	UP	Hop	Mendoza	Morrow	M	1992-04-03	58847759G	\N	f	8178 Justo. Rd.	2	\N	22	58601	820758088	820758088	adipiscing@pedeblandit.ca	0	NMs	0	Clovis	f	f	Grady Hoffman Flores	240193989	Cras@facilisisvitae.org	Ebony Bowers Greer	240193989	diam@Phasellus.com	2008-05-27 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	1	1	'hop':1 'mendoz':2 'morrow':3	t	\N
89	J	UP	Donovan	Eaton	Anthony	F	2003-04-27	77881618M	\N	f	P.O. Box 578, 5891 Quam. Road	4	\N	22	75939	686977574	686977574	massa.lobortis@semNullainterdum.edu	0	ON	0	Aspen	f	f	Adrian Melendez Tyler	422720048	Vivamus.nisi@in.edu	Lee Levine Dunlap	422720048	sed.dolor@vitaeorci.com	2010-12-27 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'anthony':3 'donov':1 'eaton':2	t	\N
118	J	UP	Melissa Alejandra	William44	Robinson	M	2003-02-22	66364470N	\N	f	755 Egestas Rd.	9	567	2	95990	22466436	22466436	justo@elit.com	0	ANT	0	Marshfield	t	t	Scott Yates King	158669081	et@luctus.ca	Jemima Travis Kaufman	158669081	sapien.molestie.orci@euismod.ca	2002-06-30 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	2	1	'alejandr':2 'meliss':1 'robinson':4 'william44':3	t	\N
121	J	UP	Cade	Rice	Rose	F	1997-01-19	93213952J	\N	f	420-594 Duis Rd.	6	\N	16	22744	651579203	651579203	metus.In@Curabituregestas.com	0	L.	0	Twin Falls	f	f	Declan Townsend Navarro	140172206	erat.eget@molestie.ca	Kim Nguyen Bishop	140172206	sodales@sedturpisnec.org	2004-01-10 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'cad':1 'ric':2 'ros':3	t	\N
127	J	UP	Sean	Chase	Gill	M	1999-01-31	53929377D	\N	f	Ap #671-5645 Ante Avenue	9	\N	5	85156	559321598	559321598	ultricies.ornare@estNuncullamcorper.ca	0	Massachusetts	0	Laramie	f	f	Allistair Donaldson Riley	251955664	rutrum.justo@faucibusut.com	Latifah Grant Fletcher	251955664	amet.metus@utodiovel.org	2008-05-16 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'chas':2 'gill':3	t	\N
239	K	UP	Perico	Pérez	\N	M	2008-09-26	\N	\N	f	Algemesi	9	\N	\N	52000	\N	\N	\N	0	Alicante	0	Guardamar del Segura	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 13:03:45.077588	\N	2011-08-10 16:31:43.5992	M,P	f	t	f	t	f	3	1	'perez':2 'peric':1	t	\N
241	J	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 13:05:45.440741	\N	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	t	\N
90	J	UP	Griffin	Whitfield	Knapp	M	2005-03-01	48942796D	\N	f	P.O. Box 721, 310 Interdum Rd.	9	\N	19	11269	801907852	801907852	euismod.urna@sitamet.edu	0	Albacete	0	Manassas Park	f	f	Richard Dillon Greer	37853038	enim.sit.amet@nullaanteiaculis.ca	Vivian Koch Wilson	37853038	molestie.in@pedeac.com	2005-05-23 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	1	1	'griffin':1 'knapp':3 'whitfield':2	t	\N
110	J	UP	Malik	Gamble	Hernandez	F	1997-02-11	17095828Y	\N	f	1936 Elementum Rd.	4	\N	2	49992	197210049	197210049	ornare@aliquet.com	0	NU	0	Rapid City	f	f	Dillon Fuller Mcconnell	993921778	diam@luctusut.com	Emily Espinoza Mathis	993921778	odio.Nam@eratVivamus.com	2008-09-30 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'gambl':2 'hernandez':3 'malik':1	t	\N
99	J	UP	Shea	Carey	Rios	F	1997-10-21	11820920S	\N	f	Ap #624-1242 Nunc. Rd.	10	\N	15	68008	622184226	622184226	tincidunt.pede.ac@etrisusQuisque.com	0	DOR	0	Laiuoiuoiuoiuoiu	f	f	Owen Stokes Cortez	941143180	cursus.in@dignissimtemporarcu.com	Lucy Whitaker Everett	941143180	torquent.per@natoquepenatibuset.ca	2003-01-17 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	1	1	'carey':2 'rios':3 'she':1	t	\N
95	J	UP	Palmer	Gordon	Madden	F	1994-04-14	23883861G	\N	f	1704 Vitae Av.	2	\N	8	92695	798035688	798035688	placerat.eget.venenatis@sapienmolestie.org	0	ANS	0	Cruz Bay	f	f	Cruz Villarreal Morin	59628600	Cras.interdum.Nunc@ametrisusDonec.edu	Josephine Massey Madden	59628600	enim@natoquepenatibuset.edu	2006-07-15 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'gordon':2 'madd':3 'palm':1	t	\N
104	J	UP	Rudyard	Michael	Petty	M	2003-11-01	19526219T	\N	f	598-3633 Nisi Rd.	3	\N	17	19895	299219959	299219959	a.auctor.non@aceleifend.ca	0	U.	0	Scarborough	f	f	Jonah Benjamin Huff	17146067	urna.justo.faucibus@InfaucibusMorbi.edu	Kylee Ware Guerrero	17146067	non@non.edu	2002-11-26 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'michael':2 'petty':3 'rudyard':1	t	\N
114	K	UP	Zeph	Cannon	Guerra	F	2001-05-12	29113707L	\N	t	6075 Quisque Avenue	9	2	23	6629	635271862	635271862	aliquet.Proin.velit@Sedidrisus.com	0	N.-Br.	0	Oklahoma City	f	f	Wyatt Whitney Hensley	865760845	et.magnis@nonhendrerit.edu	Macey Mcintyre Meyers	865760845	nulla.ante@tortordictum.ca	2008-11-23 00:00:00	2011-01-04 18:19:03.133	2011-08-10 16:31:43.5992	M,P	f	t	f	t	f	4	1	'cannon':2 'guerr':3 'zeph':1	f	\N
113	K	UP	Yen	Cross	Salas	M	1992-11-10	36804075A	\N	f	789-2022 Ullamcorper. Rd.	9	\N	17	44612	258638179	258638179	sed.facilisis@inceptoshymenaeosMauris.org	0	Noord Brabant	0	Geneva	f	f	Lucian Mccall Stokes	38226681	Fusce.mi@egetvolutpatornare.com	Bertha Colon Wise	38226681	Cum@Etiamligulatortor.com	2009-12-21 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'cross':2 'sal':3 'yen':1	t	\N
75	J	UP	Sacha	Delaney	Morris	F	2001-11-18	73478155M	\N	f	194-1454 Mauris Rd.	2	\N	15	36448	725127751	725127751	velit.in.aliquet@inceptoshymenaeos.edu	0	N.-H.	0	Nenana	f	f	Gregory Russell Newton	862562047	dui@cursuspurusNullam.com	Amy Forbes Bradford	862562047	sapien.gravida.non@imperdieterat.com	2004-09-02 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'delaney':2 'morris':3 'sach':1	t	\N
80	J	UP	Carl	Ward	Travis	F	2004-10-09	00333584Q	\N	f	328-3145 Egestas. St.	8	\N	6	66271	115384696	115384696	malesuada.ut@imperdiet.edu	0	Fr.	0	Grass Valley	f	f	Alden Clements Caldwell	385130321	Cum@Inat.com	Mechelle Hurst Spencer	385130321	dui.nec@lectusasollicitudin.com	2010-09-27 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'carl':1 'travis':3 'ward':2	t	\N
240	K	UP	hhhhhhh	gjyhj	yjhj	F	1991-07-12	587875685	568878	f	ghjh	hgj	ghj	gjh	88990	779906	779906	576876@gmail.com	0	Castellón	0	La Morería	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 13:04:27.210922	\N	2011-08-10 16:31:43.5992	M,E	f	t	t	f	f	2	1	'gjyhj':2 'hhhhhhh':1 'yjhj':3	t	\N
94	J	UP	Aquila	Powell	Montgomery	F	1993-11-28	64569071C	\N	f	155-2844 In Avenue	2	\N	4	14785	845922103	845922103	lorem@ipsum.edu	0	Limburg	0	Kent	f	f	Peter Medina Langley	596701054	tellus.faucibus@ullamcorpernislarcu.edu	Deborah Guerra Hanson	596701054	Fusce.fermentum@a.ca	2004-05-09 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'aquil':1 'montgomery':3 'powell':2	t	\N
109	J	UP	Vaughan	Bradford	Oconnor	M	1991-09-12	90928856M	\N	f	362-6423 Suspendisse Ave	7	\N	12	5621	672434187	672434187	vulputate@Aliquamvulputate.org	0	NB	0	Vicksburg	f	f	Evan Saunders Durham	70479379	in.consectetuer@posuerevulputate.edu	TaShya Hood Long	70479379	Nam.nulla@vitae.org	2004-09-03 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'bradford':2 'oconnor':3 'vaugh':1	t	\N
97	J	UP	Yoko	Summers	Trujillo	M	1997-05-04	39118987P	\N	f	251-5103 Tellus St.	2	\N	2	5590	927344993	927344993	morbi.tristique.senectus@nonenim.edu	0	Nunavut	0	Anaconda	f	f	Garth Boyd Gilliam	104548797	a.scelerisque@Loremipsumdolor.com	Tanya Oliver Gibbs	104548797	augue.ac.ipsum@acrisus.edu	2006-11-27 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'summers':2 'trujill':3 'yok':1	t	\N
105	J	UP	Kellie	Pruitt	Macdonald	M	2004-07-17	32013841E	\N	f	Ap #368-661 Sed Avenue	6	\N	18	4152	921272731	921272731	eu@nullavulputate.org	0	DE	0	West Springfield	f	f	Hall Forbes Andrews	358510708	Integer.aliquam@cursus.com	Sonia Hunt Meadows	358510708	Cum.sociis.natoque@atpretium.edu	2011-02-03 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'kelli':1 'macdonald':3 'pruitt':2	t	\N
108	J	UP	Oleg	Brennan	Marks	F	2002-07-13	44939980A	\N	f	6579 Egestas. Rd.	3	\N	16	88022	942607415	942607415	enim.Mauris@senectus.edu	0	NY	0	Paducah	f	f	Lucas Cruz Palmer	528623840	massa@nequepellentesquemassa.com	Jenette Espinoza Villarreal	528623840	Sed.nec@Aliquamgravidamauris.ca	2011-01-25 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'brenn':2 'marks':3 'oleg':1	t	\N
78	J	UP	Alika	Knapp	Lopez	M	1996-10-04	11310914E	\N	f	899-4192 Cubilia St.	4	\N	26	18166	803299235	803299235	Maecenas.iaculis.aliquet@et.com	0	FL	0	Lowell	f	f	Aquila Pena Webster	625642986	torquent.per.conubia@amalesuada.edu	Tanya Skinner Mays	625642986	vestibulum.lorem.sit@Namporttitorscelerisque.com	2005-05-13 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'alik':1 'knapp':2 'lopez':3	t	\N
71	J	UP	Sloane	Mccray	Wilder	M	1996-07-23	82935473Z	\N	f	Ap #465-1396 Mauris Road	9	\N	20	67803	984027809	984027809	Aliquam.fringilla@hendreritDonec.edu	0	Newfoundland and Labrador	0	Detroit	f	f	Bruce Horn Patton	909647525	Quisque.tincidunt.pede@euligulaAenean.ca	Ursula Fletcher Robbins	909647525	magnis@eleifendegestasSed.org	2010-06-24 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'mccray':2 'sloan':1 'wild':3	t	\N
102	J	UP	Quincy	Cunningham	Parrish	M	2002-08-08	81238366G	\N	f	112-4290 Ante Street	4	\N	3	57868	844503713	844503713	et@facilisiSedneque.edu	0	N.-H.	0	Chicopee	f	f	Ryan Romero Smith	32134561	eget.ipsum.Donec@inaliquet.com	Laurel Mckee Kelly	32134561	elit@Phaselluslibero.edu	2008-02-16 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'cunningham':2 'parrish':3 'quincy':1	t	\N
103	J	UP	Stone	Sherman	Head	M	2005-07-03	12113992C	\N	f	763-5095 Est, Road	6	\N	3	62049	949227183	949227183	lacus.Cras.interdum@id.com	0	SK	0	New Orleans	f	f	Charles Willis Olson	861245907	natoque@nibhAliquam.com	Jocelyn Arnold Castillo	861245907	molestie.dapibus@at.com	2007-06-06 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'head':3 'sherm':2 'ston':1	t	\N
82	J	UP	Devin	Barry	Gray	M	2000-02-13	32880168U	\N	f	260-5725 Aenean Road	7	\N	26	11613	926490617	926490617	nunc.In.at@lacusQuisque.com	0	QC	0	West Lafayette	f	f	Hall Crosby Ewing	193286572	nostra.per.inceptos@cursuspurus.ca	Leilani Sosa Humphrey	193286572	sit@sociosquad.ca	2003-11-23 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'barry':2 'devin':1 'gray':3	t	\N
85	J	UP	Tatyana	Stokes	Tillman	F	2006-02-19	53188991Q	\N	f	2066 Massa. St.	2	\N	5	74539	329408628	329408628	Quisque@egetmetus.ca	0	Alberta	0	Oil City	f	f	Colton Mcclure Taylor	302103347	non.egestas.a@ullamcorper.org	Noelani Brock Craig	302103347	in.cursus.et@tempuseuligula.com	2002-11-07 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'stok':2 'tatyan':1 'tillm':3	t	\N
72	J	UP	Galvin	Melton	Copeland	M	1990-12-20	94408647C	\N	f	251-7250 Facilisis Avenue	8	\N	9	26118	622746439	622746439	ac.fermentum@tempusmauris.ca	0	Nebraska	0	Seal Beach	f	f	Xenos Kemp Baker	937392032	Aenean@massarutrummagna.ca	Madison Moss Carroll	937392032	ut@acfermentum.ca	2010-09-07 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'copeland':3 'galvin':1 'melton':2	t	\N
84	J	UP	Gannon	Holman	Foreman	F	1993-03-20	72891144V	\N	f	177-6841 Lectus St.	1	\N	14	41806	722597311	722597311	lectus.justo@odiovel.com	0	CA	0	Berlin	f	f	Thaddeus Curry Cummings	520180183	luctus.ut@metusVivamus.org	Abra Armstrong Dorsey	520180183	malesuada@posuerecubiliaCurae	1900-03-24 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'forem':3 'gannon':1 'holm':2	t	\N
67	J	UP	Macon	Barker	Stafford	F	1991-10-30	37661561J	\N	f	3969 Vitae, Rd.	9	\N	18	78608	388773307	388773307	ipsum.dolor.sit@vitaerisusDuis.com	0	Washington	0	Frederiksted	f	f	Drew Mosley Larson	766250338	Pellentesque.habitant@orciUtsagittis.ca	Cara Farmer Long	766250338	litora@eratsemper.edu	2004-01-29 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'bark':2 'macon':1 'stafford':3	t	\N
68	J	UP	Ignatius	Koch	Orr	M	2003-01-26	25474959E	\N	f	589-8606 Arcu Street	1	\N	19	72799	877604127	877604127	urna.nec.luctus@id.ca	0	Fr.	0	Morrison	f	f	Jamal Marsh Allison	176881495	nibh.vulputate.mauris@Phasellus.org	Quynn Glover Weeks	176881495	Cras@eueratsemper.ca	2006-09-08 00:00:00	\N	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'ignatius':1 'koch':2 'orr':3	t	\N
62	J	UP	wert	Craft	Rush	F	1992-02-05	88730555D	\N	f	Ap #646-6460 Aliquet, St.	1	\N	21	467	627241440	627241440	tempor@Maurisnulla.org	0	Fr.	0	Bossier City	f	f	Barrett Foreman Ingram	937132427	ultrices.posuere.cubilia@actellusSuspendisse.ca	Ayanna Rodriguez Cantrell	937132427	risus@cursusvestibulumMauris.edu	2010-10-20 00:00:00	\N	2011-08-10 16:31:43.5992	M	f	t	f	f	f	2	1	'craft':2 'rush':3 'wert':1	t	\N
139	J	UP	Herrod	Vega	Cooley	F	2005-07-03	17559736W	\N	f	641 Dolor Ave	2	\N	29	749	673114755	673114755	eu.tellus.Phasellus@Aliquam.org	0	N.-Br.	0	Saginaw	f	f	Colin Cabrera Gould	641751321	venenatis@ante.com	Ginger Shelton Simpson	641751321	vulputate.risus@massa.edu	2010-01-12 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'cooley':3 'herrod':1 'veg':2	t	\N
116	C	UP	Valentine	Dillard	Love	M	1991-11-14	65024076P	\N	f	P.O. Box 713, 7700 Non Av.	6	\N	2	48772	911644732	911644732	aliquet.vel@rhoncusid.ca	0	IN	0	New Rochelle	f	f	Randall Landry Haney	81250690	risus.Duis.a@erat.org	Nelle Douglas Brooks	81250690	dui.nec.urna@Nullaeuneque.com	2005-07-31 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'dillard':2 'lov':3 'valentin':1	t	\N
88	J	UP	Audra	Summers	Lee	M	2001-12-03	58230721U	\N	f	P.O. Box 617, 458 Odio. St.	2	\N	8	31658	910269849	910269849	metus.In.nec@libero.edu	0	Ov.	0	Darlington	f	f	Rogan Best Mclean	746028825	neque.sed.dictum@dapibus.org	Zenaida Pickett Cohen	746028825	id.libero@diamlorem.edu	2006-05-17 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	1	1	'audr':1 'lee':3 'summers':2	t	\N
18	J	UP	Tanya	Stevens	Rios	M	1991-05-04	55864652E	\N	f	765-5261 Quis, Street	6	\N	20	49059	793127799	793127799	eu.lacus.Quisque@ipsumDonec.edu	0	Zld.	0	Sanford	f	f	Cameron Nash Vaughn	538268097	sagittis.lobortis@porttitortellusnon.ca	Bryar Ramirez Kane	538268097	vitae.posuere.at@porttitorvulputate.com	2004-03-28 00:00:00	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'rios':3 'stevens':2 'tany':1	t	\N
242	C	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 13:07:49.432638	\N	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	t	\N
246	K	UP	Sir Lord Robert	Stephenson Smith	\N	M	1857-02-22	48528715F	445499764053	f	C/ Falsa	123	\N	\N	3013			badenpowell@hotmail.com	0	Inglaterra	0	Isla de Brownsea	f	f	\N	\N	\N	\N	\N	\N	2010-10-04 16:34:53.255621	\N	2011-08-10 16:31:43.5992	R	f	f	f	f	t	1	1	'lord':2 'robert':3 'sir':1 'smith':5 'stephenson':4	t	\N
243	J	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 13:18:22.372532	\N	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	t	\N
73	J	UP	David	Parrish	Vega	F	2002-04-13	93596926I	\N	f	437-2189 Maecenas Road	1	\N	12	60511	607492458	607492458	eget@orciin.com	0	Zuid Holland	0	Ruston	f	f	Gareth Hester Barron	466553024	ac.nulla.In@Sedauctorodio.edu	Ima Vincent Davis	466553024	velit@et.org	2002-12-21 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'dav':1 'parrish':2 'veg':3	t	\N
244	J	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 13:19:30.182218	\N	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	t	\N
245	J	UP	Alejandro	Valverde	De La Torre	M	2010-08-13	48694599X	567678568565	f	Aguila 43	4	A	Izq	3007	885685784	885685784	yo_69@hotmail.com	0	Alacant	0	Alacant	f	f	hjghkghmkg	\N	hola@gmail.com	hrgg	\N	adios@hotmail.com	2010-10-02 13:23:21.52571	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'alejandr':1 'torr':5 'valverd':2	t	\N
24	J	UP	Elton	Warren	Reilly	F	1998-12-17	04236796B	\N	f	P.O. Box 201, 120 Libero Av.	9	\N	15	56779	205755432	205755432	tristique.senectus@lorem.ca	0	East Riding of Yorkshire	0	Frisco	f	f	Raja Cohen Ellis	381766315	a@sedpede.ca	Jemima Ware Ayala	381766315	Aliquam.tincidunt.nunc@ipsumPhasellusvitae.com	2010-05-11 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'elton':1 'reilly':3 'warr':2	t	\N
107	J	UP	Blythe	Dillard	Berger	M	1996-08-28	05004216P	\N	f	Ap #583-533 Est Street	3	\N	9	52546	369273990	369273990	id@Etiam.com	0	Merionethshire	0	West Hollywood	f	f	Joseph Sampson Chambers	980605992	neque@liberolacusvarius.org	Rowan Hardin Mcmillan	980605992	parturient.montes.nascetur@nibhPhasellus.org	2003-02-09 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'berg':3 'blythe':1 'dillard':2	t	\N
122	J	UP	Bryar	Morrow	Ewing	F	1991-06-02	25999605X	\N	f	5170 Dolor St.	5	\N	26	81567	707240105	707240105	nulla@ac.org	0	Humberside	0	Huntington Park	f	f	Murphy Hebert Hurley	560784746	eu.tellus@vitae.ca	Shaine Turner Cabrera	560784746	ipsum.Suspendisse@tellusAeneanegestas.edu	2009-10-08 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'bryar':1 'ewing':3 'morrow':2	t	\N
5611	J	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-11-13 10:12:13.702922	\N	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	t	\N
23	J	UP	Emerald	Bray	fsdf	M	1997-06-23	50147555V	\N	f	Ap #227-4497 Ligula Ave	1	\N	4	11745	302994363	302994363	Suspendisse@Donec.com	0	Cumbria	0	Virginia Beach	f	f	Keefe Valenzuela Noel	819088863	magnis.dis.parturient@lobortisquam.edu	Pascale Page Boyer	819088863	est@eu.edu	2010-01-16 00:00:00	\N	2011-08-10 16:31:43.5992	P	f	f	f	t	f	1	1	'bray':2 'emerald':1 'fsdf':3	t	\N
5647	K	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-11-21 14:51:04.295135	\N	2011-08-10 16:31:43.5992	M,P	f	t	f	t	f	0	1	'jack':1 'pearl':3 'sparrow':2	t	\N
25441	J	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2011-01-16 15:27:46.367124	\N	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	t	\N
111	K	UP	Wilma	Francis	Pace	F	2001-07-01	45721701J	\N	f	Ap #383-2365 Non, Avenue	4	\N	16	92441	616849371	616849371	justo.nec.ante@eleifendnunc.ca	0	AB	0	Dubuque	f	f	Bert Robinson Booker	621579869	massa.Mauris.vestibulum@Aliquamvulputateullamcorper.com	Inga Dale Massey	621579869	ligula.tortor@erosnon.org	2008-09-20 00:00:00	2011-01-16 15:28:21.694	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'francis':2 'pac':3 'wilm':1	f	\N
115	K	UP	Derek	Calderon	Brown	F	1996-04-11	31826833A	\N	f	159-7075 Risus. St.	5	\N	3	45466	934333799	934333799	ultrices@diam.ca	0	TYR	0	Marquette	f	f	Galvin Galloway Bowman	758885850	dapibus@euduiCum.org	Susan Rocha Pierce	758885850	parturient.montes.nascetur@velvulputate.ca	2008-06-03 00:00:00	2011-01-16 15:28:27.19	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'brown':3 'calderon':2 'derek':1	f	\N
6285	K	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2011-01-04 18:10:27.207945	\N	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	t	\N
21	C	UP	Zelenia	Mcmillan	Rios	F	1997-06-30	21090454P	\N	f	P.O. Box 663, 8844 Purus. Street	1	\N	3	42123	899381601	899381601	lectus.convallis@sociis.org	0	NU55	0	Columbia	f	f	Timothy Stephens Mccarthy	301823683	interdum.Sed.auctor@Quisquepurus.org	Charde Bauer Stokes	301823683	sit.amet@Phasellusdapibus.edu	2006-06-11 00:00:00	\N	2011-08-10 16:31:43.5992	C	t	f	f	f	f	1	1	'mcmill':2 'rios':3 'zeleni':1	t	\N
11	K	UP	Brian	Vaughn	Rowe	F	1994-12-01	46500964D	\N	f	901-1091 A Rd.	6	\N	26	61970	182910685	182910685	lobortis.quam@sodales.ca	0	YTV	0	Leominster	f	f	\N	\N	\N	\N	\N	\N	2009-04-29 00:00:00	2010-09-06 10:56:01.797	2011-08-10 16:31:43.5992	R	f	f	f	f	t	1	1	'bri':1 'row':3 'vaughn':2	f	\N
12	K	UP	Catherine	Garrett	Melton	M	1996-03-17	63574019F	\N	f	750-5663 Ultricies Street	9	\N	29	60999	808028966	808028966	Fusce.aliquam@euismod.ca	0	Ov.	0	Hollywood	f	f	\N	\N	\N	\N	\N	\N	2008-11-12 00:00:00	2010-09-06 10:19:39.274	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'catherin':1 'garrett':2 'melton':3	f	\N
235	K	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 13:00:04.810345	2011-01-04 18:13:55.464	2011-08-10 16:31:43.5992	P,R	f	f	f	t	t	3	1	'jack':1 'pearl':3 'sparrow':2	f	\N
112	K	UP	Jemima	Watts	Mcknight	M	2003-08-29	32619745N	\N	f	Ap #224-6984 Urna St.	3	\N	20	5183	252360402	252360402	faucibus@purusactellus.org	0	Fl.	0	Fallon	f	f	Ralph Woodward Hoover	530757861	in.faucibus.orci@Etiam.ca	Imogene Bright Figueroa	530757861	nunc@orciluctus.org	2009-10-23 00:00:00	2011-01-04 18:14:27.036	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'jemim':1 'mcknight':3 'watts':2	f	\N
237	K	UP	baloo	pérez	\N	M	1984-07-08	\N	\N	f	àusias march	3	\N	\N	46980	\N	\N	\N	0	Castellón	0	El Perelló	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 13:01:49.961832	2010-10-02 13:02:48.052	2011-08-10 16:31:43.5992	C,M,E,P,R	t	t	t	t	t	1	1	'balo':1 'perez':2	f	\N
184	J	UP	Wesley	Waller	Preston	M	1996-11-27	72533284S	\N	f	753-2237 Ac Rd.	9	\N	21	51243	97546122	97546122	Donec.egestas@sapienimperdiet.org	0	Co. Limerick	0	Valdosta	f	f	Lane Matthews Copeland	782061617	vulputate@Intinciduntcongue.org	Rinah Snow Oneil	782061617	velit@augueSedmolestie.ca	2004-11-26 00:00:00	2006-05-13 00:00:00	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'preston':3 'wall':2 'wesley':1	f	\N
229	J	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 08:53:30.791507	2010-10-02 08:53:38.866	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	f	\N
230	K	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 11:42:03.03212	2010-10-02 11:42:10.769	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	f	\N
156	J	UP	Kane	Gilliam	Lindsay	M	1999-02-07	58577360L	\N	f	6027 Quisque Street	6	\N	19	87976	945996932	945996932	tristique.pharetra.Quisque@bibendum.com	0	YT	0	Grand Forks	f	f	Barry Guerra Bell	695515187	ligula.consectetuer.rhoncus@auctornuncnulla.org	Rose Goff Reid	695515187	ornare@laciniavitae.org	2005-09-04 00:00:00	2010-10-02 13:06:07.697	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'gilliam':2 'kan':1 'lindsay':3	f	\N
198	J	UP	Fatima	Fry	Hammond	M	1995-05-09	01666164C	\N	f	Ap #396-1192 Neque Av.	3	\N	4	48831	900753132	900753132	sit.amet.consectetuer@atliberoMorbi.org	0	Gelderland	0	Los Alamitos	f	f	Carter Goff Ramirez	807007645	rutrum.urna@Morbi.com	Alexis Dillard Beard	807007645	sit.amet@posuerevulputatelacus.org	2005-05-09 00:00:00	2010-10-02 13:00:42.155	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'fatim':1 'fry':2 'hammond':3	f	\N
6	K	UP	Timothy	Flowers	Ramsey	M	1995-03-14	98645894S	\N	f	P.O. Box 981, 7514 Imperdiet St.	5	\N	4	61552	506815370	506815370	eu.euismod@duilectusrutrum.edu	0	California	0	Bowie	f	f	\N	\N	\N	\N	\N	\N	2004-11-11 00:00:00	2010-11-21 14:51:40.688	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'flowers':2 'ramsey':3 'timothy':1	f	\N
5563	C	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-10-29 13:10:53.368417	2010-10-29 13:10:57.185	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	f	\N
185	J	UP	Bryar	Cross	Melendez	F	1996-09-13	16310236V	\N	f	Ap #156-8287 Nam St.	3	\N	26	70134	141806085	141806085	in@Quisqueporttitor.ca	0	NU	0	Cerritos	f	f	Oleg Velez Vang	859192039	laoreet@dictummagnaUt.org	Portia Boone Dawson	859192039	quis@ut.org	2008-09-11 00:00:00	2007-07-02 00:00:00	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'bryar':1 'cross':2 'melendez':3	f	\N
232	K	UP	jorge	martinez	\N	M	1967-04-09	\N	\N	f	del olvido	13	\N	\N	46897	\N	\N	\N	0	Castellón	0	El Perelló	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 12:56:17.978454	2010-10-02 12:56:23.053	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jorg':1 'martinez':2	f	\N
100	J	UP	Britanney	Matthews	Conway	F	1992-02-05	56023606N	\N	f	390-9871 Magna St.	2	\N	11	65031	682161310	682161310	tempus.risus.Donec@tinciduntnequevitae.com	0	Noord Brabant	0	Connellsville	f	f	Gabriel Trevino Bradshaw	150469676	neque.Morbi.quis@MaurismagnaDuis.com	Yeo Washington Jordan	150469676	vehicula.Pellentesque@metusfacilisis.ca	2005-01-21 00:00:00	2010-10-02 12:59:22.697	2011-08-10 16:31:43.5992	M	f	t	f	f	f	0	1	'britanney':1 'conway':3 'matthews':2	f	\N
25	J	UP	Mufutau	Marshall	Grimes	F	1996-10-27	22937850I	\N	f	4642 Tempor Rd.	4	\N	4	2531	929997121	929997121	pellentesque@etrutrum.ca	0	WA	0	Uniontown	f	f	Ross Bright Valentine	136362158	luctus.felis@consequatenimdiam.com	Ann Nunez Delacruz	136362158	torquent@aliquetmolestietellus.edu	2005-10-20 00:00:00	2004-06-28 00:00:00	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'grim':3 'marshall':2 'mufutau':1	f	\N
54	J	UP	Dana	Velazquez	Paul	F	1995-05-14	45315014O	\N	f	328-7518 Cursus, St.	4	\N	15	79236	600848488	600848488	mi.felis.adipiscing@justoPraesent.edu	0	ND	0	Weirton	f	f	Basil Madden Mcconnell	864791607	mauris@neque.org	Ann Savage Wagner	864791607	quis.accumsan@enimEtiamimperdiet.org	2009-02-18 00:00:00	2005-04-21 00:00:00	2011-08-10 16:31:43.5992	R	f	f	f	f	t	0	1	'dan':1 'paul':3 'velazquez':2	f	\N
9	K	UP	Macon	Obrien	Dodson	F	2000-05-27	26026812G	\N	f	9360 In, Street	7	\N	12	70423	851589794	851589794	ante.Nunc.mauris@sitamet.edu	0	FLIU	0	Omaha	f	f	\N	\N	\N	\N	\N	\N	2007-04-25 00:00:00	2010-09-06 10:30:39.097	2011-08-10 16:31:43.5992	P	f	f	f	t	f	1	1	'dodson':3 'macon':1 'obri':2	f	\N
10	K	UP	Daquan	Hatfield	Todd	M	2000-11-08	48477130U	\N	f	895-7777 In Rd.	5	\N	23	83635	439385946	439385946	odio.tristique@imperdieterat.ca	0	DEA	0	Anchorage	f	f	\N	\N	\N	\N	\N	\N	2011-01-06 00:00:00	2008-12-07 00:00:00	2011-08-10 16:31:43.5992	E	f	f	t	f	f	1	1	'daqu':1 'hatfield':2 'todd':3	f	\N
35	J	UP	Neil	Wise	Haley	M	2005-09-19	53088070A	\N	f	460 Nec St.	5	\N	11	90747	748759578	748759578	tempus.eu.ligula@sapienmolestieorci.com	0	Newfoundland and Labrador	0	Carson City	f	f	Phillip Baldwin Colon	321444359	nascetur@Etiam.edu	Rinah Salas Nelson	321444359	ac.libero.nec@diamloremauctor.ca	2009-10-18 00:00:00	2004-10-15 00:00:00	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'haley':3 'neil':1 'wis':2	f	\N
196	J	UP	Rebecca	Torres	Ewing	F	1991-12-07	70426050Z	\N	f	Ap #247-4166 Nonummy Ave	8	\N	30	67736	920338968	920338968	porttitor@nibhsitamet.edu	0	SGM	0	Laguna Hills	f	f	Simon Greene Gaines	178472456	ac.arcu.Nunc@Duisvolutpatnunc.ca	Melanie Burgess Mills	178472456	augue.id.ante@disparturient.edu	2009-05-14 00:00:00	2009-05-02 00:00:00	2011-08-10 16:31:43.5992	E	f	f	t	f	f	0	1	'ewing':3 'rebecc':1 'torr':2	f	\N
238	J	UP	Amy	Johnson	Mccoy	F	1999-01-27	17482942A	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Alicante	0	Guardamar del Segura	f	f	\N	\N	\N	\N	\N	\N	2010-10-02 13:02:19.34627	2010-10-02 13:06:55.878	2011-08-10 16:31:43.5992	P	f	f	f	t	f	1	1	'amy':1 'johnson':2 'mccoy':3	f	\N
98	J	UP	Iona	Reese	Mclean	M	2004-09-02	93320194S	\N	f	Ap #234-5990 Id Street	9	\N	2	78912	863094481	863094481	sollicitudin.orci.sem@nequeet.org	0	Ov.	0	Belleville	f	f	Kasimir Day Cohen	303493268	diam.Proin.dolor@utcursus.org	Isabella Chapman Gould	303493268	pede.blandit.congue@fermentum.org	2007-11-28 00:00:00	2010-10-02 13:02:59.377	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'ion':1 'mcle':3 'rees':2	f	\N
101	J	UP	Ingrid	Boyd	Patterson	M	2003-09-20	27409133R	\N	f	Ap #767-2173 Praesent Rd.	3	\N	28	53965	590939028	590939028	quam@Integerin.ca	0	MDX	0	Annapolis	f	f	Marsden Velazquez Curry	525762827	habitant@tinciduntnunc.edu	Ann Velazquez Sargent	525762827	cursus.et.eros@atarcuVestibulum.ca	2005-06-05 00:00:00	2010-10-02 13:03:23.972	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'boyd':2 'ingrid':1 'patterson':3	f	\N
96	J	UP	Tara	Osborne	Huffman	M	2004-12-08	50344424Y	\N	f	171-8932 Elit. Road	6	\N	13	31141	449179714	449179714	elementum.at.egestas@ligulaDonec.com	0	Groningen	0	Wilmington	f	f	Damian Pittman Ochoa	379218830	adipiscing.Mauris@rhoncusNullam.com	Jennifer Spencer Buckner	379218830	lacus@NulladignissimMaecenas.org	2003-09-19 00:00:00	2010-10-02 13:03:39.544	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'huffm':3 'osborn':2 'tar':1	f	\N
140	J	UP	Gloria	Figueroa	Mills	F	2001-09-27	77006868H	567898756	f	P.O. Box 723, 1181 Cursus St.	1	\N	6	46056	637058386	637058386	Duis.gravida@nullavulputate.edu	0	CMN	0	Little Rock	f	f	Deacon Kent Robertson	321829287	quis@convallisin.org	Jaquelyn Melton Durham	321829287	Sed.auctor@tinciduntaliquam.org	2004-11-21 00:00:00	2010-10-02 13:18:03.883	2011-08-10 16:31:43.5992	M	f	t	f	f	f	3	1	'figuero':2 'glori':1 'mills':3	f	\N
76	J	UP	Scott	Page	Bond	F	1999-05-28	99383565Y	\N	f	836-9448 Aenean St.	4	\N	10	972	66304907	66304907	sapien.Aenean.massa@elitCurabitur.edu	0	BC	0	San Rafael	f	f	Kieran Walls Byers	616397942	Sed.nec@semperauctor.com	Marcia Stone Tillman	616397942	consequat.lectus@purus.org	2006-02-27 00:00:00	2006-08-28 00:00:00	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'bond':3 'pag':2 'scott':1	f	\N
74	J	UP	Martin	Anthony	Bradford	F	2001-12-22	41460948N	\N	f	Ap #346-8613 Elementum, Rd.	1	\N	19	14106	403848987	403848987	porta.elit.a@parturientmontes.com	0	Ov.	0	Cedar Falls	f	f	Xander Collins Mathews	41011284	odio@metus.edu	India Gardner Melton	41011284	cubilia@Phasellusinfelis.edu	2003-01-24 00:00:00	2010-10-02 12:59:43.522	2011-08-10 16:31:43.5992	P	f	f	f	t	f	0	1	'anthony':2 'bradford':3 'martin':1	f	\N
92	J	UP	Finn	Sutton	Holman	F	2002-11-13	77227196O	\N	f	152-3658 Elementum Rd.	1	\N	30	66841	259994225	259994225	consequat.lectus.sit@liberoet.com	0	L.	0	Sheboygan	f	f	Marshall Reese Walker	339806409	Pellentesque@eratSednunc.ca	Hedy Mccray Hodge	339806409	ut.eros.non@Vestibulumut.ca	2007-04-02 00:00:00	2010-10-02 13:01:12.926	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'finn':1 'holm':3 'sutton':2	f	\N
69	J	UP	Jolie	Randolph	Richmond	M	2006-01-09	86848158H	\N	f	P.O. Box 242, 7939 Ipsum. Road	3	\N	8	54027	346692675	346692675	nunc@convallis.com	0	Wisconsin	0	Glens Falls	f	f	Dexter Lang Hurst	272021523	Suspendisse.commodo@aceleifendvitae.ca	Nita Levine Pace	272021523	a.nunc.In@Phasellus.org	2003-05-05 00:00:00	2010-10-02 13:00:47.862	2011-08-10 16:31:43.5992	C	t	f	f	f	f	0	1	'joli':1 'randolph':2 'richmond':3	f	\N
17	J	UP	Britanney	Jarvis	Mullen	F	2001-02-15	58862082X	\N	f	P.O. Box 489, 7860 Lacus. Rd.	8	\N	10	14922	598086915	598086915	fermentum@quamelementumat.org	0	Gr.	0	Tampa	f	f	Dorian Goodman Livingston	830378141	augue@enim.com	Phyllis Dunlap Ratliff	830378141	Integer.sem@eleifend.ca	2005-03-05 00:00:00	2010-10-02 13:06:06.575	2011-08-10 16:31:43.5992	M	f	t	f	f	f	4	1	'britanney':1 'jarvis':2 'mull':3	f	\N
1153	K	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2010-10-29 13:00:54.393156	2010-10-29 13:00:59.747	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	f	\N
6286	J	UP	Jack	Sparrow	Pearl	M	2001-10-21	\N	\N	f	Amarradero de la perla negra	13	\N	\N	46015	\N	\N	\N	0	Valencia	0	Valencia	f	f	\N	\N	\N	\N	\N	\N	2011-01-05 00:48:06.18926	\N	2011-08-10 16:31:43.5992		f	f	f	f	f	0	1	'jack':1 'pearl':3 'sparrow':2	t	\N
\.


--
-- TOC entry 1881 (class 0 OID 17054)
-- Dependencies: 1517
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY authorities (username, authority) FROM stdin;
cudu	ROLE_USER
cudu	ROLE_ADMIN
sda	SdA
sda	ROLE_USER
albafo	PERMISO_H
PACO	ROLE_USER
PACO	ROLE_ADMIN
PACO	ROLE_PERMISO_A
HECTOR	ROLE_USER
HECTOR	ROLE_ADMIN
HECTOR	ROLE_PERMISO_B
JESTRICKGA	ROLE_USER
JESTRICKGA	ROLE_PERMISO_F
TECSDC	ROLE_USER
TECSDC	ROLE_PERMISO_C1
\.


--
-- TOC entry 1883 (class 0 OID 17321)
-- Dependencies: 1520
-- Data for Name: curso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY curso (id, nombre, acronimo, anyo, precio, descripcion) FROM stdin;
1	AnimadorJuvenil	AJ	2011	20.100000000000001	Animador Juvenil / Ensenya de fusta
2	Formació Continua	FC	2011	20.100000000000001	Formació Continua
\.


--
-- TOC entry 1887 (class 0 OID 17379)
-- Dependencies: 1524
-- Data for Name: faltamonografico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY faltamonografico (idasociado, idmonografico, idcurso, fechafalta) FROM stdin;
\.


--
-- TOC entry 1879 (class 0 OID 17028)
-- Dependencies: 1515
-- Data for Name: grupo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY grupo (id, nombre, direccion, codigopostal, idprovincia, provincia, idmunicipio, municipio, aniversario, telefono1, telefono2, email, web, entidadpatrocinadora, asociacion, jpa_version) FROM stdin;
AK	Ain-Karen	Plaza Diputado Lluis Lucía	46015	0	Valencia	0	Valencia	1985-11-30	635823391	\N	ainkaren@gmail.com	http://ainkaren.blogspot.com	Parroquia San Ignacio de Loyola	2	8
ALZ	Alezeya	C/ Cano 85	46130	0	València	0	Massamagrell	\N	654515616	\N	capdegrup@alezeya.com	http://www.alezeya.com	\N	2	1
CHN	Cohinoor	c/ Engay 13	46800	0	València	0	Xàtiva	\N	627910146	\N	grupscoutcohinoor@hotmail.com	http://www.cohinoor.tk	Parroquia La Seu	2	1
GRN	Granerers	C/ Valencia 35	46900	0	València	0	Torrent	\N	675097472	\N	granerers@msn.com	\N	Parroquia el Buen consejo de Torrent	2	1
SNV	Sant Vicent	Fadrell, 3	12005	0	Castelló	0	Castelló	\N	699 203 208	\N	silviankara4@hotmail.com	\N	Parroquia de San Vicente Ferrer	1	1
AIT	Aitana	c/ Reyes Católicos 36	3003	0	Alacant	0	Alacant	\N	622225598	\N	aitanagruposcout@gmail.com	sites.google.com/site/aitanagruposcout	Parroquia de San Pascual Bailón	0	1
ARG	Argila	c/ Perolers 1	46970	0	València	0	Alaquás	1983-08-01	000000000	\N	vifemi@alumni.uv.es	ttp://www.aeargila.org	Parròquia Mare de Déu de l'Olivar	2	4
AGL	Águilas	c/ Ciudad Real 14	3005	0	Alacant	0	Alacant	\N	\N	\N	\N	\N	Parroquia de Sant Esteban Protomartir	0	0
AMK	Amarok	Iglesia Santa Isabel Barrio Santa Isabel	3590	0	Alacant	0	Sant Vicent del Raspeig	\N	\N	\N	\N	\N	Iglesia Santa Isabel	0	0
BWS	Brownsea	av. Isla de Corfú	3005	0	Alacant	0	Alacant	\N	\N	\N	\N	\N	Colegio Hermanos Maristas	0	0
GWL	Gilwell	c/ Médico Vicente Reyes s/n	3015	0	Alacant	0	Alacant	\N	\N	\N	\N	\N	Parroquia el Salvador	0	0
KNY	Kenya	Calle del Pintor Pedro Camacho 1	3016	0	Alacant	0	Alacant	\N	\N	\N	gskenya@hotmail.com	http://www.gskenya.com	Colegio San Agustín	0	0
MFK	Mafeking	av. de Denia 98	3015	0	Alacant	0	Alacant	\N	\N	\N	mafeking@navegalia.com	http://mafeking.turincon.com	Colegio de la Inmaculada	0	0
NME	Nome	av.. del Mediterráneo	3600	0	Alacant	0	Elda	\N	\N	\N	\N	\N	Colegio Sagrada Familia	0	0
NYE	Nyeri	pl. de España 2	3590	0	Alacant	0	Sant Vicent del Raspeig	\N	\N	\N	contacto@gsnyeri.com	http://www.gsnyeri.com	Parroquia de San Vicente Ferrer	0	0
PCN	Pelícanos-Cóndor	c/ Tubería 5	3005	0	Alacant	0	Alacant	\N	\N	\N	contacto@pelicanoscondor.com	http://www.pelicanoscondor.com	\N	0	0
SN1	Seenoee	c/ Madre Elisea Oliver s/n	0	0	Alacant	0	Sant Joan d'Alacant	\N	\N	\N	victorjose.medi@terra.es	http://msc.scouts-es.net/seeonee	Colegio Carmelitas de San Juan	0	0
ANT	Antares	c/ Menéndez y Pelayo s/n	12005	0	Castelló	0	Castelló	\N	\N	\N	\N	\N	Colegio Escolapios	1	0
MIL	El Millars	c/ Gumbau 21	12001	0	Castelló	0	Castelló	\N	\N	\N	elmillars@ono.com	http://www.elmillars.com	Concatedral Santa María	1	0
SOL	El Solaig	pl. L'Esglesia 19	12549	0	Castelló	0	Betxí	\N	\N	\N	\N	\N	Parroquia de Nuestra Señora de los Ángeles	1	0
ESP	Espadà	c/ Verge de la Mercé 8	12600	0	Castelló	0	La Vall d'Uixó	\N	\N	\N	\N	http://www.grupscoutespada.org	Parroquia de San Ángel	1	0
SNP	Sant Pere	c/ Xurruca 12 (Grao)	12100	0	Castelló	0	Castelló	\N	\N	\N	\N	\N	Parroquia de San Pedro	1	0
TRT	Tramuntana	c/ Santo Tomás 45	12550	0	Castelló	0	Almassora	\N	\N	\N	\N	\N	Scouts del poble d'Almassora	1	0
ABX	Alborxí	c/ San Martín 56	46980	0	València	0	Paterna	\N	\N	\N	scoutsalborxi@lasalle.es	http://www.lasalle.es/scoutsalborxi	Escuela Profesional La Salle	2	0
ALT	Altaïr	c/ Santa Magdalena Sofía 1	46110	0	València	0	Godella	\N	\N	\N	vicentelozanobeltran@hotmail.com	http://gruposcoutaltair.webcindario.com	Colegio Sagrado Corazón	2	0
ANV	Anem Avant	pl. Església Font de Sant Lluís 5	46013	0	València	0	València	\N	\N	\N	anemavant@anemavant.org	http://www.anemavant.org	\N	2	0
AZH	Azahar	c/ Partida Berca s/n	46680	0	València	0	Algemesí	\N	\N	\N	correo@gsazahar.org	http://www.gsazahar.org	Colegio Maristas	2	0
BWN	Brownie	c/ Colón 50	46240	0	València	0	Carlet	\N	\N	\N	gsbrownie@hotmail.com	http://msc.scouts-es.net/brownie	Iglesia de San José	2	0
BIT	Bitácora	c/ Salavert 12	46018	0	València	0	València	\N	\N	\N	info@scoutsbitacora.org	http://www.scoutsbitacora.org	\N	2	0
GAI	Gaia	c/ San Joaquín 22	46200	0	València	0	Paiporta	\N	\N	\N	marialimonge@yahoo.es	\N	\N	2	0
HWT	Hiawatha	c/ L'Esglesia 14	46114	0	València	0	Vinalesa	\N	\N	\N	anna46114@hotmail.com	http://www.iespana.es/grup-scout-vinalesa	Parroquia de Sant Honorato	2	0
ITM	Itamar	c/ Alejandro Volta 14	46014	0	València	0	València	\N	\N	\N	g_s_itamar@hotmail.com	\N	Parroquia Cristo de la Luz	2	0
ITR	Iter	c/ Fontilles 20 baix	46024	0	València	0	València	\N	\N	\N	iter.msc@scouts-es.net	\N	Parroquia de Ntra. Sra. de los Desamparados	2	0
JMB	Jamboree	c/ Aben Ferro s/n	46800	0	València	0	Xàtiva	\N	\N	\N	jamboree.msc@scouts-es.net	http://msc.scouts-es.net/jamboree	Colegio Beato Jacinto Castañeda	2	0
JA1	Jaume I	c/ Lledoners 3	46133	0	València	0	Meliana	\N	\N	\N	aejaume@yahoo.es	\N	\N	2	0
KAI	Kairos	c/ San Ernesto 5 bajo	46017	0	València	0	València	\N	\N	\N	gongofema@hotmail.com	http://www.gasbutano.net/kairos	\N	2	0
KIO	Kiomara	c/ Campoamor 12	46021	0	València	0	València	\N	\N	\N	kiomara.msc@scouts-es.net	http://msc.scouts-es.net/kiomara	Parroquia de Ntra. Sra. de Lourdes	2	0
KPL	Kipling	c/ Jaume Roig 14	46010	0	València	0	València	\N	\N	\N	webgskipling@gmail.com	http://msc.scouts-es.net/kipling	Colegio Alemán	2	0
SFR	La Safor	c/ Pellers 73 bajo	46700	0	València	0	Gandia	\N	\N	\N	\N	\N	\N	2	0
LOM	L'Om	pl. Abadia 1	46220	0	València	0	Picasent	\N	\N	\N	\N	\N	\N	2	0
CAL	Calassanç	c/ Escoles Pies 34	46680	0	València	0	Algemesí	\N	962482264	627636210	gscalasanz@hotmail.com	http://www.aecalassanc.tk	Colegio San José de Calassanç	2	1
IMP	Impeesa	Av. Dr. Jiménez Díaz, 5	3005	0	Alacant	0	Alacant	1992-09-01	689267557	\N	gruposcoutimpeesa@hotmail.com	\N	Colegio Don Bosco Salesianos	0	1
UP	Decimo de la Isla de la Educación	Plaza de los Valores	46073	0	Valencia	0	Valencia	\N	12345678	\N	chanclillas@isladeningunsitio.org	\N	\N	1	1
MKS	Malki-sua	c/ Mariano Ribera 10 bajo	46018	0	València	0	València	\N	\N	\N	malki-sua.msc@scouts-es.net	http://msc.scouts-es.net/malki-sua	Parroquia Santo Domingo Savio y San Expedito Mártir	2	0
MQS	Marquesat	c/ Bosch Marin s/n	46196	0	València	0	Catadau	\N	\N	\N	marquesat.msc@scouts-es.net	http://msc.scouts-es.net/marquesat	Parroquia de San Pedro de Catadau	2	0
MTP	Matopu	c/ Jumilla 4	46018	0	València	0	València	\N	\N	\N	marcos_tortosa_vergara@hotmail.com	http://www.matopu.tk	\N	2	0
MWG	Mowgli	c/ Jesús 9	46014	0	València	0	València	\N	\N	\N	josepe001@hotmail.com	\N	Iglesia Santa María de Jesús	2	0
NFC	Nou Foc	c/ General Prim 11	46006	0	València	0	València	\N	\N	\N	noufoc@hotmail.com	http://www.noufoc.org	\N	2	0
ORN	Orión	av. Cortes Valencianas 1	46015	0	València	0	València	\N	\N	\N	g_s_orion@hotmail.com	http://msc.scouts-es.net/orion	Escuelas Profesionales San José	2	0
OSR	Osyris	c/ Poeta Ricart Sanmartí 9	46020	0	València	0	València	\N	\N	\N	\N	http://msc.scouts-es.net/osyris	Patronato de la Juventud Obrera	2	0
PSP	Pas de Pi	c/ Perolers 1	46970	0	València	0	Alaquás	\N	\N	\N	slagunak@airtel.net	http://gruposcoutpau@hotmail.com	\N	2	0
PAU	Pau	av. Blasco Ibañez 119	46022	0	València	0	València	\N	\N	\N	\N	\N	Parroquia de San Prudencio	2	0
P12	Pío XII	c/ Alboraya 9	46010	0	València	0	València	\N	\N	\N	scouts@gspioxii.org	http://www.gspioxii.org	\N	2	0
SJR	Sant Jordi	c/ Iglesia 17	46850	0	València	0	L'Olleria	\N	651807812	\N	aesantjordi@yahoo.es	\N	Parroquia Santa María Magdalena	2	0
SN2	Seeonee	av. Cortes Valencianas 1	46015	0	València	0	València	\N	963655948	\N	seeonee@ono.com	\N	Escueles Profesionales San José	2	0
SIC	Sicània	av. Port 21	46400	0	València	0	Cullera	\N	\N	\N	gssicania@yahoo.es	\N	Colegio Hermanos Maristas	2	0
SVL	Silvel·la	c/ Montealegre 12	46950	0	València	0	Xirivella	\N	\N	\N	joferpa0@euita.upv.es	http://silvel-la.org	\N	2	0
SMR	Stella Maris	c/ Progreso s/n	46520	0	València	0	Puerto de Sagunto	\N	\N	\N	scouts.stellamaris@gmail.com	\N	Parroquia Nuestra Señora del Carmen	2	0
VAL	Valldigna	c/ Sant Josep 58	46760	0	València	0	Tavernes de Valldigna	\N	\N	\N	aevalldigna@galeon.com	http://www.aevalldigna.galeon.com	Parroquia de San José	2	0
SEP	VII Calasanz	c/ Carnicers 6	46001	0	València	0	València	\N	\N	\N	gruposeptimocalasanz@hotmail.com	http://gruposcoutviicalasanz.es.kz	\N	2	0
OCT	VIII Calasanz	c/ Micer Mascó 5	46010	0	València	0	València	\N	\N	\N	\N	http://calasanz.msc@scouts-es.net	Colegio Escuelas Pías	2	0
VIL	Villalonga	c/ Santos Reyes 6	46720	0	València	0	Villalonga	\N	\N	\N	\N	\N	Parroquia de los Santos Reyes	2	0
XLC	Xaloc	pl. Constitución	46120	0	València	0	Alboraia	\N	\N	\N	j_montaner@hotmail.com	http://tropa.webcindario.com	\N	2	0
XPI	X El Pilar	av. Blasco Ibañez 33 i 45	46010	0	València	0	València	\N	\N	\N	gruposcout@gsxelpilar.org	http://www.gsxelpilar.org	Colegio El Pilar	2	0
WWG	Wig Wam	pl. Nueva de la Iglesia s/n	46025	0	València	0	València	\N	625735283	\N	gswigwam@hotmail.com	\N	Parroquia de San Roque	2	2
SJN	Sant Joan	c/ Raval 57	46130	0	València	0	Massamagrell	\N	615357284	\N	grupscoutsantjoan@gmail.com	www.grupscoutsantjoan.org	\N	2	1
\.


--
-- TOC entry 1886 (class 0 OID 17355)
-- Dependencies: 1523
-- Data for Name: inscripcioncurso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY inscripcioncurso (idasociado, idcurso, idmonografico, fechainscripcion, pagorealizado, trabajo, fecha_entrega_trabajo, calificacion, idiomamateriales, modocontacto, formatomateriales) FROM stdin;
8	1	1	2010-11-10	f	O	\N	O	V	E	E
\.


--
-- TOC entry 1884 (class 0 OID 17330)
-- Dependencies: 1521
-- Data for Name: monografico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY monografico (id, nombre, fechainicio, fechafin, precio, descripcion, plazasdisponibles, plazastotales, listaespera, lugarprevisto) FROM stdin;
1	Castores	2011-02-04	2011-02-08	20.100000000000001	dias 4,5,6,7 y 8 de Diciembre	20	30	0	--
2	Lobatos	2011-02-04	2011-02-08	20.100000000000001	dias 4,5,6,7 y 8 de Diciembre	20	30	0	--
3	Exploradores	2011-02-04	2011-02-08	20.100000000000001	dias 4,5,6,7 y 8 de Diciembre	20	30	0	--
4	Pioneros	2011-02-04	2011-02-08	20.100000000000001	dias 4,5,6,7 y 8 de Diciembre	20	30	0	--
5	Compañeros	2011-02-04	2011-02-08	20.100000000000001	dias 4,5,6,7 y 8 de Diciembre	20	30	0	--
6	Gestión de campamentos	2012-02-12	2012-02-13	20.100000000000001	dias del 12 al 13 de Febrero	20	30	0	--
7	Vida en la naturaleza	2012-04-09	2012-04-10	20.100000000000001	dias 9 y 10 de abril	20	30	0	--
8	Educación para la salud	2012-03-26	2012-03-27	20.100000000000001	26 y 27 de marzo del 2012	20	30	0	--
9	Educación para el Desarrollo	2012-03-26	2012-03-27	20.100000000000001	26 y 27 de marzo del 2012	20	30	0	--
10	Naturaleza en la Comunidad Valenciana	2012-05-28	2012-05-29	20.100000000000001	28 y 29 de MAYO 2012	20	30	0	--
11	Educar en la Fe	2012-05-28	2012-05-29	20.100000000000001	28 y 29 de MAYO 2012	20	30	0	--
12	Recursos para la Animación	2012-01-29	2012-01-30	20.100000000000001	29 y 30 de enero del 2012	20	30	0	--
13	Habilidades Sociales	2012-01-29	2012-01-30	20.100000000000001	29 y 30 de enero del 2012	20	30	0	--
14	Trabajo y Consumo	2012-05-21	2012-05-22	20.100000000000001	21 y 22 de MAYO 2012	20	30	0	--
15	Dinámicas de grupo	2012-05-21	2012-05-22	20.100000000000001	21 y 22 de MAYO 2012	20	30	0	--
\.


--
-- TOC entry 1885 (class 0 OID 17337)
-- Dependencies: 1522
-- Data for Name: monograficos_en_cursos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY monograficos_en_cursos (idcurso, idmonografico, bloque, obligatorio, bloqueunico, bloque_numerominimo_monograficos) FROM stdin;
1	1	ramas                                                                                               	t	t	0
1	2	ramas                                                                                               	t	t	0
1	3	ramas                                                                                               	t	t	0
1	4	ramas                                                                                               	t	t	0
1	5	ramas                                                                                               	t	t	0
2	1	ramas                                                                                               	t	t	0
2	2	ramas                                                                                               	t	t	0
2	3	ramas                                                                                               	t	t	0
2	4	ramas                                                                                               	t	t	0
2	5	ramas                                                                                               	t	t	0
2	6	sin nombre                                                                                          	f	f	0
2	7	sin nombre                                                                                          	f	f	0
1	8	Rebrot                                                                                              	t	t	0
1	9	Rebrot                                                                                              	t	t	0
1	10	Rebrot                                                                                              	t	t	0
1	11	Rebrot                                                                                              	t	t	0
1	12	Técnicas                                                                                            	t	t	0
1	13	Técnicas                                                                                            	t	t	0
1	14	Técnicas                                                                                            	t	t	0
1	15	Técnicas                                                                                            	t	t	0
2	8	Rebrot                                                                                              	t	t	0
2	9	Rebrot                                                                                              	t	t	0
2	10	Rebrot                                                                                              	t	t	0
2	11	Rebrot                                                                                              	t	t	0
2	12	Técnicas                                                                                            	t	t	0
2	13	Técnicas                                                                                            	t	t	0
2	14	Técnicas                                                                                            	t	t	0
2	15	Técnicas                                                                                            	t	t	0
\.


--
-- TOC entry 1880 (class 0 OID 17041)
-- Dependencies: 1516
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (username, password, fullname, idgrupo, enabled) FROM stdin;
cudu	cudu	Cuenta de Administación	\N	t
sda	test	Scouts de Alicante	\N	t
albafo	albafo2011	cuenta interesante	UP	t
HECTOR	test	Hector, usu con permiso b	UP	t
PACO	test	paco,usu con permiso a	UP	t
TECSDC	test	tecnico del sdc	UP	t
JESTRICKGA	test	kraal o comite con permiso F	UP	t
\.


--
-- TOC entry 1847 (class 2606 OID 17091)
-- Dependencies: 1519 1519
-- Name: pk_asociado; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY asociado
    ADD CONSTRAINT pk_asociado PRIMARY KEY (id);


--
-- TOC entry 1845 (class 2606 OID 17058)
-- Dependencies: 1517 1517 1517
-- Name: pk_authorities; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY authorities
    ADD CONSTRAINT pk_authorities PRIMARY KEY (username, authority);


--
-- TOC entry 1851 (class 2606 OID 17325)
-- Dependencies: 1520 1520
-- Name: pk_curso; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY curso
    ADD CONSTRAINT pk_curso PRIMARY KEY (id);


--
-- TOC entry 1865 (class 2606 OID 17383)
-- Dependencies: 1524 1524 1524 1524
-- Name: pk_faltamonografico; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY faltamonografico
    ADD CONSTRAINT pk_faltamonografico PRIMARY KEY (idasociado, idmonografico, fechafalta);


--
-- TOC entry 1840 (class 2606 OID 17040)
-- Dependencies: 1515 1515
-- Name: pk_grupo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY grupo
    ADD CONSTRAINT pk_grupo PRIMARY KEY (id);


--
-- TOC entry 1861 (class 2606 OID 17344)
-- Dependencies: 1522 1522 1522
-- Name: pk_id_monograficos_en_cursos; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY monograficos_en_cursos
    ADD CONSTRAINT pk_id_monograficos_en_cursos PRIMARY KEY (idcurso, idmonografico);


--
-- TOC entry 1863 (class 2606 OID 17368)
-- Dependencies: 1523 1523 1523 1523
-- Name: pk_inscripcioncurso; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY inscripcioncurso
    ADD CONSTRAINT pk_inscripcioncurso PRIMARY KEY (idasociado, idmonografico, idcurso);


--
-- TOC entry 1857 (class 2606 OID 17334)
-- Dependencies: 1521 1521
-- Name: pk_monografico; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY monografico
    ADD CONSTRAINT pk_monografico PRIMARY KEY (id);


--
-- TOC entry 1842 (class 2606 OID 17048)
-- Dependencies: 1516 1516
-- Name: pk_users; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (username);


--
-- TOC entry 1849 (class 2606 OID 17123)
-- Dependencies: 1519 1519
-- Name: u_asociado_usuario; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY asociado
    ADD CONSTRAINT u_asociado_usuario UNIQUE (usuario);


--
-- TOC entry 1853 (class 2606 OID 17329)
-- Dependencies: 1520 1520 1520
-- Name: uk_curso_acronimo_anyo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY curso
    ADD CONSTRAINT uk_curso_acronimo_anyo UNIQUE (acronimo, anyo);


--
-- TOC entry 1855 (class 2606 OID 17327)
-- Dependencies: 1520 1520 1520
-- Name: uk_curso_nombre_anyo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY curso
    ADD CONSTRAINT uk_curso_nombre_anyo UNIQUE (nombre, anyo);


--
-- TOC entry 1859 (class 2606 OID 17336)
-- Dependencies: 1521 1521 1521
-- Name: uk_monografico_nomsbre_fechainicio; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY monografico
    ADD CONSTRAINT uk_monografico_nomsbre_fechainicio UNIQUE (nombre, fechainicio);


--
-- TOC entry 1843 (class 1259 OID 17105)
-- Dependencies: 1517 1517
-- Name: ix_authorities; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX ix_authorities ON authorities USING btree (username, authority);


--
-- TOC entry 1878 (class 2620 OID 17103)
-- Dependencies: 21 1519
-- Name: actualizarasociacion; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER actualizarasociacion
    BEFORE INSERT ON asociado
    FOR EACH ROW
    EXECUTE PROCEDURE actualizarfiltroasociacion();


--
-- TOC entry 1875 (class 2620 OID 17104)
-- Dependencies: 22 1515
-- Name: actualizarasociaciongrupo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER actualizarasociaciongrupo
    AFTER UPDATE ON grupo
    FOR EACH ROW
    EXECUTE PROCEDURE actualizarfiltroasociaciongrupo();


--
-- TOC entry 1877 (class 2620 OID 17100)
-- Dependencies: 1519 20
-- Name: auditasociado; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER auditasociado
    BEFORE INSERT OR UPDATE ON asociado
    FOR EACH ROW
    EXECUTE PROCEDURE actualizardatosauditoriaasociado();


--
-- TOC entry 1876 (class 2620 OID 17098)
-- Dependencies: 1519 19
-- Name: menorsoloenunaunidad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER menorsoloenunaunidad
    BEFORE INSERT OR UPDATE ON asociado
    FOR EACH ROW
    EXECUTE PROCEDURE comprobarcantidadramasmenor();


--
-- TOC entry 1868 (class 2606 OID 17092)
-- Dependencies: 1839 1515 1519
-- Name: fk_asociado_grupo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY asociado
    ADD CONSTRAINT fk_asociado_grupo FOREIGN KEY (idgrupo) REFERENCES grupo(id);


--
-- TOC entry 1869 (class 2606 OID 17124)
-- Dependencies: 1841 1516 1519
-- Name: fk_asociado_users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY asociado
    ADD CONSTRAINT fk_asociado_users FOREIGN KEY (usuario) REFERENCES users(username);


--
-- TOC entry 1867 (class 2606 OID 17059)
-- Dependencies: 1516 1841 1517
-- Name: fk_authorities_users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY authorities
    ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username);


--
-- TOC entry 1870 (class 2606 OID 17345)
-- Dependencies: 1522 1520 1850
-- Name: fk_curso_monograficos_en_cursos; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY monograficos_en_cursos
    ADD CONSTRAINT fk_curso_monograficos_en_cursos FOREIGN KEY (idcurso) REFERENCES curso(id);


--
-- TOC entry 1874 (class 2606 OID 17384)
-- Dependencies: 1523 1524 1524 1524 1523 1523 1862
-- Name: fk_faltamonografico_inscripcion; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY faltamonografico
    ADD CONSTRAINT fk_faltamonografico_inscripcion FOREIGN KEY (idasociado, idmonografico, idcurso) REFERENCES inscripcioncurso(idasociado, idmonografico, idcurso);


--
-- TOC entry 1872 (class 2606 OID 17369)
-- Dependencies: 1846 1523 1519
-- Name: fk_inscripcioncurso_asociado; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY inscripcioncurso
    ADD CONSTRAINT fk_inscripcioncurso_asociado FOREIGN KEY (idasociado) REFERENCES asociado(id);


--
-- TOC entry 1873 (class 2606 OID 17374)
-- Dependencies: 1860 1523 1522 1522 1523
-- Name: fk_inscripcioncurso_cuso; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY inscripcioncurso
    ADD CONSTRAINT fk_inscripcioncurso_cuso FOREIGN KEY (idcurso, idmonografico) REFERENCES monograficos_en_cursos(idcurso, idmonografico);


--
-- TOC entry 1871 (class 2606 OID 17350)
-- Dependencies: 1522 1521 1856
-- Name: fk_monografico_monograficos_en_cursos; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY monograficos_en_cursos
    ADD CONSTRAINT fk_monografico_monograficos_en_cursos FOREIGN KEY (idmonografico) REFERENCES monografico(id);


--
-- TOC entry 1866 (class 2606 OID 17049)
-- Dependencies: 1516 1839 1515
-- Name: pk_users_grupo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users_grupo FOREIGN KEY (idgrupo) REFERENCES grupo(id) ON UPDATE CASCADE;


--
-- TOC entry 1892 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2011-08-10 20:10:07 CEST

--
-- PostgreSQL database dump complete
--

