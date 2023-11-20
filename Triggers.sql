-- FUNCTION: public.atualizar_qtd_estoque()

-- DROP FUNCTION IF EXISTS public.atualizar_qtd_estoque();

CREATE OR REPLACE FUNCTION public.atualizar_qtd_estoque()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
  IF TG_OP = 'INSERT' THEN
    -- Se for uma inserção (compra), atualiza a quantidade no estoque
    IF NEW.tipo = 'Compra' THEN
      UPDATE produtos
      SET qtd_estoque = COALESCE(qtd_estoque, 0) + NEW.quantidade
      WHERE id = NEW.codigo_item;
	else UPDATE produtos
      SET qtd_estoque = COALESCE(qtd_estoque, 0) - NEW.quantidade
      WHERE id = NEW.codigo_item;
    END IF;

  ELSIF TG_OP = 'DELETE' THEN
    -- Se for uma exclusão (venda), atualiza a quantidade no estoque
    IF NEW.tipo = 'Venda' THEN
      UPDATE produtos
      SET qtd_estoque = COALESCE(qtd_estoque, 0) + NEW.quantidade
      WHERE id = NEW.codigo_item;
	else UPDATE produtos
      SET qtd_estoque = COALESCE(qtd_estoque, 0) - NEW.quantidade
      WHERE id = NEW.codigo_item;
    END IF;

  END IF;

  RETURN NULL;
END;
$BODY$;

ALTER FUNCTION public.atualizar_qtd_estoque()
    OWNER TO postgres;


-- FUNCTION: public.calcular_valor_total()

-- DROP FUNCTION IF EXISTS public.calcular_valor_total();

CREATE OR REPLACE FUNCTION public.calcular_valor_total()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
  -- Calcula o valor total e atualiza o campo correspondente
  NEW.valor_total := NEW.valor_unitario * NEW.quantidade;
  RETURN NEW;
END;
$BODY$;

ALTER FUNCTION public.calcular_valor_total()
    OWNER TO postgres;


-- Trigger: trigger_atualiza_qtd_estoque

-- DROP TRIGGER IF EXISTS trigger_atualiza_qtd_estoque ON public.mov_estoque;

CREATE TRIGGER trigger_atualiza_qtd_estoque
    AFTER INSERT OR DELETE
    ON public.mov_estoque
    FOR EACH ROW
    EXECUTE FUNCTION public.atualizar_qtd_estoque();

-- Trigger: trigger_calcula_valor_total

-- DROP TRIGGER IF EXISTS trigger_calcula_valor_total ON public.mov_estoque;

CREATE TRIGGER trigger_calcula_valor_total
    BEFORE INSERT
    ON public.mov_estoque
    FOR EACH ROW
    EXECUTE FUNCTION public.calcular_valor_total();
