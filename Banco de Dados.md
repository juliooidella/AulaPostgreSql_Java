# Primeiro: Crie a tabela "Produtos" com os seguintes campos:
- id (tipo serial, não nulo, chave primária)
- nome (tipo text, não nulo)
- preco (tipo numeric, não nulo)
- categoria (tipo text)
- qtd_estoque (tipo numeric)
- unidade (tipo text)
- nrocodbar (tipo text)
- pesobruto (tipo numeric)

# Segundo: Crie a tabela "mov_estoque" com os seguintes campos:
- id (tipo serial, não nulo, chave primária)
- codigo_item (tipo bigint, não nulo)
- tipo (tipo text, não nulo)
- valor_unitario (tipo numeric, não nulo)
- quantidade (tipo numeric, não nulo)
- valor_total (tipo numeric)
  
# Terceiro: Execute os comandos a seguir para criar as funções e triggers
https://github.com/juliooidella/AulaPostgreSql_Java/blob/main/Triggers.sql
