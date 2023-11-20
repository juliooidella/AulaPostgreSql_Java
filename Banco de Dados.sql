--- CREATE da Tabela Produtos
DROP TABLE IF EXISTS public.produtos;

CREATE TABLE IF NOT EXISTS public.produtos
(
    id serial NOT NULL,
    nome text COLLATE pg_catalog."default" NOT NULL,
    preco numeric NOT NULL,
    categoria text COLLATE pg_catalog."default",
    qtd_estoque numeric,
    unidade text COLLATE pg_catalog."default",
    nrocodbar text COLLATE pg_catalog."default",
    pesobruto numeric,
   CONSTRAINT produtos_pkey PRIMARY KEY (id)
);

--- CREATE da tabela de movimentação de estoque
DROP TABLE IF EXISTS public.mov_estoque;

CREATE TABLE IF NOT EXISTS public.mov_estoque
(
    id serial NOT NULL,
    codigo_item bigint NOT NULL,
    tipo text COLLATE pg_catalog."default" NOT NULL,
    valor_unitario numeric NOT NULL,
    quantidade numeric NOT NULL,
    valor_total numeric,
    CONSTRAINT mov_estoque_pkey PRIMARY KEY (id)
);

-- Trigger: Para atualização do Saldo do estoque

-- DROP TRIGGER IF EXISTS trigger_atualiza_qtd_estoque ON public.mov_estoque;

CREATE TRIGGER trigger_atualiza_qtd_estoque
    AFTER INSERT OR DELETE
    ON public.mov_estoque
    FOR EACH ROW
    EXECUTE FUNCTION public.atualizar_qtd_estoque();

-- Trigger: Para calcular o valor total.

-- DROP TRIGGER IF EXISTS trigger_calcula_valor_total ON public.mov_estoque;

CREATE TRIGGER trigger_calcula_valor_total
    BEFORE INSERT
    ON public.mov_estoque
    FOR EACH ROW
    EXECUTE FUNCTION public.calcular_valor_total();
