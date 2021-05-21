package br.com.senai.controller.carrinho;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import br.com.dao.DataBaseConnection;
import br.com.senai.controller.produto.EditaProduto;
import br.com.senai.model.CarrinhoModel;
import br.com.senai.model.ProdutoModel;

public class GerarCupom {

	private Scanner entrada = new Scanner(System.in);
	private CarrinhoModel carrinhoModel;
	private Connection connection;
	ProdutoModel produtoModel;
	EditaProduto editaProduto = new EditaProduto();
	private int escolha;
	private double total;
	
	public GerarCupom() {
		connection = DataBaseConnection.getInstance().getConnection();
	}
	
	public CarrinhoModel gerarCupom(int id) {
		
		PreparedStatement preparedStatement;
		
		carrinhoModel = new CarrinhoModel();
		
		try {
			String sql = "SELECT * FROM produtosnocarrinho WHERE idCliente = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if(!resultSet.next()) {
				System.out.println("Não possui produtos no carrinho.");
				return null;
			}
			
			System.out.println("\n-- PRODUTOS NO CARRINHO --");
			System.out.printf("| %4s | %4s | %9s |\n", "ID", "Qtd", "R$ Total");
			
			resultSet.previous();
			
			while(resultSet.next()) {
				System.out.printf("| %4s | %4s | %9s |\n",
						resultSet.getInt("idDoProduto"),
						resultSet.getInt("quantidadeDeItensNoCarrinho"),
						resultSet.getDouble("valorTotalPorItem"));	
			}
			
			System.out.println("\nDeseja completar a ação? 1 - Sim / 2 - Não");
			escolha = entrada.nextInt();
			if (escolha == 1) {
				
				String sqlSelectCliente = "SELECT * FROM clientes WHERE id = ?";
				preparedStatement = connection.prepareStatement(sqlSelectCliente);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
				resultSet.next();
				
				System.out.println("CUPOM FISCAL: " + resultSet.getString("nome"));
				
				String sqlSelectProduto = "SELECT * FROM produtosnocarrinho WHERE idCliente = ?";
				preparedStatement = connection.prepareStatement(sqlSelectProduto);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
				
				System.out.println("\n-------- PRODUTOS ---------");
				System.out.printf("| %4s | %4s | %9s |\n", "ID", "Qtd", "R$ Total");
				
				while(resultSet.next()) {
					System.out.printf("| %4s | %4s | %9s |\n",
							resultSet.getInt("idDoProduto"),
							resultSet.getInt("quantidadeDeItensNoCarrinho"),
							resultSet.getDouble("valorTotalPorItem"));	
					total += resultSet.getDouble("valorTotalPorItem");
				}
				
				System.out.println("\nVALOR TOTAL DA COMPRA: R$" + total);
				
				String sqlDelete = "DELETE FROM produtosnocarrinho WHERE idCliente = ?";
				preparedStatement = connection.prepareStatement(sqlDelete);
				preparedStatement.setInt(1, id);
				preparedStatement.execute();
				
			} else {
				System.out.println("Operação cancelada!");
				return null;
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao gerar cupom!");
			e.printStackTrace();
			return null;
		}
		return carrinhoModel;
	}
}
