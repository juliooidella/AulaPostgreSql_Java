# Aula de Java com PostgreSQL
Introdução
Bem-vindo à aula sobre o desenvolvimento de uma aplicação de cadastro e movimentação de estoque em Java, utilizando o PostgreSQL como banco de dados. Nesta aula, exploraremos a integração entre a aplicação e o banco de dados, abordando a listagem de dados, a inserção no banco e o conceito de trigger. Porém antes você deverá ajustar o código da classe Estoque.java, corrigindo o que estiver errado.

![image](https://github.com/juliooidella/AulaPostgreSql_Java/assets/22839053/094e017d-81cf-4d66-82bc-373b2b8e7856)

# Passo 1: Clone esse projeto pelo CMD e abra ele pelo programada Intellij
- Abra o CMD no seu Windows
- Digite o comando a seguir para entrar na pasta Documentos do seu computador: cd Documents
- Depois digite o comando para clonar o projeto: git clone https://github.com/juliooidella/AulaPostgreSql_Java.git
- Agora abra o projeto pelo Intellij da seguinte forma:
  <br><br> ![image](https://github.com/juliooidella/AulaPostgreSql_Java/assets/22839053/79c0fbe2-a309-40b9-924a-66e0268dbdf9)
- Navegue até a pasta que baixou o projeto e abra a pasta, ou seja, pela pasta Documentos.
<br><br>![image](https://github.com/juliooidella/AulaPostgreSql_Java/assets/22839053/02010981-afcd-4492-83e7-976cbc8f551a)

  

# Passo 2: Crie a tabela "Produtos" com os seguintes campos:
- id (tipo serial, não nulo, chave primária)
- nome (tipo text, não nulo)
- preco (tipo numeric, não nulo)
- categoria (tipo text)
- qtd_estoque (tipo numeric)
- unidade (tipo text)
- nrocodbar (tipo text)
- pesobruto (tipo numeric)

# Passo 3: Crie a tabela "mov_estoque" com os seguintes campos:
- id (tipo serial, não nulo, chave primária)
- codigo_item (tipo bigint, não nulo)
- tipo (tipo text, não nulo)
- valor_unitario (tipo numeric, não nulo)
- quantidade (tipo numeric, não nulo)
- valor_total (tipo numeric)
  
# Passo 4: Execute os comandos a seguir para criar as funções e triggers
https://github.com/juliooidella/AulaPostgreSql_Java/blob/main/Triggers.sql

# Passo 5: Execute os scritps a seguir para testar se a inserção e selação dos dados estão funcionado

INSERT INTO produtos (nome, preco, categoria,  unidade, nrocodbar, pesobruto) VALUES ( 'Notebook', 5000, 'Informatica', 'Unitário', '1234', 3.500);

INSERT INTO mov_estoque (codigo_item, tipo, valor_unitario, quantidade) VALUES (1, 'Compra', 5000, 10);

SELECT * FROM PRODUTOS;

# Agora se tudo funcionou corretamente até aqui é hora de execute a classe Estoque.java e corrigir os erros que encontrar.

# Boa Sorte!
