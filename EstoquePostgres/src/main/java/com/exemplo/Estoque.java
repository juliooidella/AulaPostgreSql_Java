package com.exemplo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Estoque {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            exibirMenu();
            int opcao = obterOpcao(scanner);

            switch (opcao) {
                case 1:
                    cadastrarNovoItem(scanner);
                    break;
                case 2:
                    listarItensCadastrados();
                    break;
                case 3:
                    realizarMovimentacaoEstoque(scanner);
                    break;
                case 4:
                    System.out.println("Encerrando o programa. Até mais!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (true);
    }

    private static void exibirMenu() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Cadastrar um novo item");
        System.out.println("2. Listar todos os itens cadastrados");
        System.out.println("3. Realizar movimentação de estoque");
        System.out.println("4. Encerrar o programa");
    }

    private static int obterOpcao(Scanner scanner) {
        int opcao = 0;
        if (scanner.hasNextInt()) {
            opcao = scanner.nextInt();
        } else {
            System.out.println("Entrada inválida. Encerrando o programa.");
            System.exit(0);
        }
        scanner.nextLine();  // Limpar o buffer do scanner
        return opcao;
    }

    private static boolean codigoItemExistente(int codigoItem) {
        // Verificar se o código do item existe na tabela de produtos
        // Configurações do banco de dados
        String url = "jdbc:postgresql://127.0.0.1:5433/SENAI";
        String usuario = "postgres";
        String senha = "1234";


        try {
            // Conectar ao banco de dados

            Connection conexao = DriverManager.getConnection(url, usuario, senha);

            // Consulta para verificar se o código do item existe
            String sql = "SELECT id FROM produtos WHERE id = ?";
            try (PreparedStatement statement = conexao.prepareStatement(sql)) {
                statement.setInt(1, codigoItem);
                ResultSet resultSet = statement.executeQuery();

                // Se houver pelo menos uma linha, o código do item existe
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void realizarMovimentacaoEstoque(Scanner scanner) {
        // Exibir a lista de itens cadastrados
        System.out.println("Lista de Itens Cadastrados:");
        listarItensCadastrados();

        int codigoItem;
        do {
            // Solicitar o código do item
            System.out.println("Informe o código do item:");
            codigoItem = scanner.nextInt();

            // Verificar se o código do item é válido
            if (!codigoItemExistente(codigoItem)) {
                System.out.println("Código do item inválido. Informe um código válido.");
            }
        } while (!codigoItemExistente(codigoItem));

        // Solicitar se é uma compra ou uma venda
        System.out.println("Informe o tipo (1 para Compra, 2 para Venda):");
        int tipo = scanner.nextInt();

        System.out.println("Informe o valor unitário:");
        double valorUnitario = scanner.nextDouble();

        System.out.println("Informe a quantidade:");
        int quantidade = scanner.nextInt();

        if (tipo == 1) {
            realizarMovimentacao(codigoItem, "Compra", valorUnitario, quantidade);
            System.out.println("Compra realizada com sucesso!");
        } else if (tipo == 2) {
            realizarMovimentacao(codigoItem, "Venda", valorUnitario, quantidade);
            System.out.println("Venda realizada com sucesso!");
        } else {
            System.out.println("Tipo inválido. Operação cancelada.");
        }
    }

    private static void realizarMovimentacao(int codigoItem, String tipo, double valorUnitario, int quantidade) {
        // Configurações do banco de dados
        String url = "jdbc:postgresql://127.0.0.1:5433/SENAI";
        String usuario = "postgres";
        String senha = "1234";

        try {
            // Conectar ao banco de dados

            Connection conexao = DriverManager.getConnection(url, usuario, senha);

            // Inserir dados na tabela mov_estoque
            inserirMovimentacao(conexao, codigoItem, tipo, valorUnitario, quantidade);

            // Atualizar o estoque na tabela produtos
            //atualizarEstoque(conexao, codigoItem, tipo, quantidade);

            // Fechar recursos
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void inserirMovimentacao(Connection conexao, int codigoItem, String tipo, double valorUnitario, int quantidade) throws SQLException {
        String sql = "INSERT INTO mov_estoque (codigo_item, tipo, valor_unitario, quantidade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, codigoItem);
            statement.setString(2, tipo);
            statement.setDouble(3, valorUnitario);
            statement.setInt(4, quantidade);
            statement.executeUpdate();
        }
    }
    private static void cadastrarNovoItem(Scanner scanner) {
        // Configurações do banco de dados
        String url = "jdbc:postgresql://127.0.0.1:5433/SENAI";
        String usuario = "postgres";
        String senha = "1234";

        try {
            // Conectar ao banco de dados
            Connection conexao = DriverManager.getConnection(url, usuario, senha);

            // Solicitar dados do produto

            System.out.println("Informe o nome do produto:");
            String nomeProduto = scanner.nextLine();
            System.out.println("Informe o preço do produto:");
            double precoProduto = scanner.nextDouble();

            // Solicitar categoria do produto
            scanner.nextLine();  // Consumir a quebra de linha pendente
            System.out.println("Selecione a categoria do produto:");
            System.out.println("1. Informatica");
            System.out.println("2. Papelaria");
            System.out.println("3. Almoxarifado");
            System.out.println("4. Outros");
            int opcaoCategoria = scanner.nextInt();
            String categoria = obterCategoria(opcaoCategoria);

            // Solicitar unidade do produto
            System.out.println("Selecione a unidade do produto:");
            System.out.println("1. Metro");
            System.out.println("2. Quilo");
            System.out.println("3. Litro");
            System.out.println("4. Gramas");
            System.out.println("5. Unitário");
            int opcaoUnidade = scanner.nextInt();
            String unidadeProduto = obterUnidade(opcaoUnidade);

            // Solicitar NROCODBAR do produto
            scanner.nextLine();  // Consumir a quebra de linha pendente
            System.out.println("Informe o Nº do código de barras:");
            String nroCodBarProduto = scanner.nextLine();

            // Solicitar PESOBRUTO do produto
            System.out.println("Informe o Peso Bruto do produto:");
            double pesoBrutoProduto = scanner.nextDouble();

            // Inserir dados no banco
            inserirProduto(conexao, nomeProduto, precoProduto, categoria, unidadeProduto, nroCodBarProduto, pesoBrutoProduto);

            // Fechar recursos
            conexao.close();
            //scanner.close();

            System.out.println("Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarItensCadastrados() {
        // Configurações do banco de dados
        String url = "jdbc:postgresql://127.0.0.1:5433/SENAI";
        String usuario = "postgres";
        String senha = "1234";


        try {
            // Conectar ao banco de dados
            Connection conexao = DriverManager.getConnection(url, usuario, senha);

            // Consulta para obter todos os itens cadastrados
            String sql = "SELECT * FROM produtos";
            try (PreparedStatement statement = conexao.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                // Exibir os itens cadastrados
                System.out.println("Itens cadastrados:");

                while (resultSet.next()) {
                    System.out.println("Código: " + resultSet.getInt("id"));
                    System.out.println("Nome: " + resultSet.getString("nome"));
                    System.out.println("Preço: " + resultSet.getDouble("preco"));
                    System.out.println("Categoria: " + resultSet.getString("categoria"));
                    System.out.println("Unidade: " + resultSet.getString("unidade"));
                    System.out.println("Nº Código de Barra: " + resultSet.getString("nrocodbar"));
                    System.out.println("Peso Bruto: " + resultSet.getDouble("pesobruto"));
                    System.out.println("Quantidade em Estoque: " + resultSet.getDouble("qtd_estoque"));
                    System.out.println("---------------------");
                }
            }

            // Fechar recursos
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void inserirProduto(Connection conexao, String nome, double preco, String categoria, String unidade, String nroCodBar, double pesoBruto) throws SQLException {
        String sql = "INSERT INTO produtos (nome, preco, categoria,  unidade, nrocodbar, pesobruto) VALUES ( ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setDouble(2, preco);
            statement.setString(3, categoria);
            statement.setString(4, unidade);
            statement.setString(5, nroCodBar);
            statement.setDouble(6, pesoBruto);
            statement.executeUpdate();
        }
    }

    private static String obterCategoria(int opcao) {
        switch (opcao) {
            case 1:
                return "Informatica";
            case 2:
                return "Papelaria";
            case 3:
                return "Almoxarifado";
            default:
                return "Outros";
        }
    }

    private static String obterUnidade(int opcao) {
        switch (opcao) {
            case 1:
                return "Metro";
            case 2:
                return "Quilo";
            case 3:
                return "Litro";
            case 4:
                return "Gramas";
            case 5:
                return "Unitário";
            default:
                return "Outros";
        }
    }
}
