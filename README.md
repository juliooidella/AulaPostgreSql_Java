# Aula de Java com PostgreSQL
Introdução
Bem-vindo à aula sobre o desenvolvimento de uma aplicação de cadastro e movimentação de estoque em Java, utilizando o PostgreSQL como banco de dados. Nesta aula, exploraremos a integração entre a aplicação e o banco de dados, abordando a listagem de dados, a inserção no banco e o conceito de trigger. Porém antes você deverá ajustar o código da classe Estoque.java, corrigindo o que estiver errado.

![image](https://github.com/juliooidella/AulaPostgreSql_Java/assets/22839053/094e017d-81cf-4d66-82bc-373b2b8e7856)


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


# Agora execute a classe Estoque.java e corrija os erros que encontrar.
# Boa Sorte!
