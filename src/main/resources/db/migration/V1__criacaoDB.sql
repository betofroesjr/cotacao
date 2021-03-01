-- DROP SEQUENCE public.cotacao_dia_seq;

CREATE SEQUENCE public.cotacao_dia_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- DROP TABLE public.cotacao_dia_db;

CREATE TABLE public.cotacao_dia_db
(
    id bigint NOT NULL,
    local_data date,
    cultura character varying(10),
    valor numeric(18,2),
    CONSTRAINT pk_cotacao_id PRIMARY KEY (id)
)