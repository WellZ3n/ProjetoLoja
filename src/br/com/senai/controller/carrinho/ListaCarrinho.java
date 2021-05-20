package br.com.senai.controller.carrinho;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.dao.DataBaseConnection;
import br.com.senai.controller.produto.EditaProduto;
import br.com.senai.model.CarrinhoModel;
import br.com.senai.model.ProdutoModel;

public class ListaCarrinho {
	
	private CarrinhoModel carrinhoModel;
	private Connection connection;
	ProdutoModel produtoModel;
	EditaProduto editaProduto = new EditaProduto();
	
	
	public ListaCarrinho() {
		connection = DataBaseConnection.getInstance().getConnection();
	}
	
	public CarrinhoModel listarItensDoCarrinho(int id) {
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
			
			System.out.println("\n- PRODUTOS NO CARRINHO -");
			System.out.printf("| %4s | %4s | %9s |\n", "ID", "Qtd", "R$ Total");
			
			resultSet.previous();
			
			while(resultSet.next()) {
				System.out.printf("| %4s | %4s | %9s |\n",
						resultSet.getInt("idDoProduto"),
						resultSet.getInt("quantidadeDeItensNoCarrinho"),
						resultSet.getDouble("valorTotalPorItem"));	
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao listar produtos do carrinho!");
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
