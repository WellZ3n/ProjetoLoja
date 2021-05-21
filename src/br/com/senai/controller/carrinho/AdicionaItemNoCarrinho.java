package br.com.senai.controller.carrinho;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import br.com.dao.DataBaseConnection;
import br.com.senai.controller.produto.EditaProduto;
import br.com.senai.model.CarrinhoModel;
import br.com.senai.model.ProdutoModel;

public class AdicionaItemNoCarrinho {
	
	private Scanner entrada = new Scanner(System.in);
	private CarrinhoModel carrinhoModel;
	private Connection connection;
	ProdutoModel produtoModel;
	EditaProduto editaProduto = new EditaProduto();
	
	public AdicionaItemNoCarrinho() {
		connection = DataBaseConnection.getInstance().getConnection();
	}
	
	@SuppressWarnings("resource")
	public CarrinhoModel cadastrarItemNoCarrinho(int id) {
		PreparedStatement preparedStatement;
		
		carrinhoModel = new CarrinhoModel();
		
		System.out.println("\n--- ADICIONAR AO CARRINHO ---");
		System.out.print("Informe o ID do produto: ");
		carrinhoModel.setIdDoProduto(entrada.nextInt());
		System.out.print("Informe a quantidade desejada: ");
		carrinhoModel.setQuantidadeDeItensNoCarrinho(entrada.nextInt());
		
		try {
			String sqlBuscarValor = "SELECT precoDoProduto, quantidadeDeProduto FROM produto WHERE codigoDoProduto = ?";
			ResultSet resultSet;
			preparedStatement = connection.prepareStatement(sqlBuscarValor);
			preparedStatement.setInt(1, carrinhoModel.getIdDoProduto());
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			
			String sqlBuscarIguais = "SELECT * FROM produtosnocarrinho WHERE idCliente = ? AND idDoProduto = ?";
			ResultSet resultSetIguais;
			preparedStatement = connection.prepareStatement(sqlBuscarIguais);
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, carrinhoModel.getIdDoProduto());
			resultSetIguais = preparedStatement.executeQuery();
			
			if (carrinhoModel.getQuantidadeDeItensNoCarrinho() <= resultSet.getDouble("quantidadeDeProduto")) {
				if(!resultSetIguais.next()) {
			carrinhoModel.setValorTotalPorItem(resultSet.getDouble("precoDoProduto") * carrinhoModel.getQuantidadeDeItensNoCarrinho());
			
			String sqlInsert = "INSERT INTO produtosnocarrinho (idDoProduto, quantidadeDeItensNoCarrinho, valorTotalPorItem, idCliente) VALUES (?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sqlInsert);
			
			preparedStatement.setInt(1, carrinhoModel.getIdDoProduto());
			preparedStatement.setInt(2, carrinhoModel.getQuantidadeDeItensNoCarrinho());
			preparedStatement.setDouble(3, carrinhoModel.getValorTotalPorItem());
			preparedStatement.setInt(4, id);
			
			preparedStatement.execute();
			
			System.out.println("Produto cadastrado no carrinho com sucesso");
			editaProduto.atualizarQuantidadeEValorTotal(carrinhoModel.getQuantidadeDeItensNoCarrinho(), carrinhoModel.getIdDoProduto(), carrinhoModel.getValorTotalPorItem()); 
				} else {
					carrinhoModel.setValorTotalPorItem(resultSet.getDouble("precoDoProduto") * carrinhoModel.getQuantidadeDeItensNoCarrinho());
					
					String sqlUpdate = "UPDATE produtosnocarrinho SET quantidadeDeItensNoCarrinho = ?, valorTotalPorItem = ? WHERE idCliente = ? AND idDoProduto = ?";
					preparedStatement = connection.prepareStatement(sqlUpdate);
					
					preparedStatement.setInt(1, carrinhoModel.getQuantidadeDeItensNoCarrinho() + resultSetIguais.getInt("quantidadeDeItensNoCarrinho"));
					preparedStatement.setDouble(2, carrinhoModel.getValorTotalPorItem() + resultSetIguais.getDouble("valorTotalPorItem"));
					preparedStatement.setInt(3, id);
					preparedStatement.setInt(4, carrinhoModel.getIdDoProduto());
					
					preparedStatement.execute();
					
					editaProduto.atualizarQuantidadeEValorTotal(carrinhoModel.getQuantidadeDeItensNoCarrinho(), carrinhoModel.getIdDoProduto(), carrinhoModel.getValorTotalPorItem()); 
				}
			} else {
				System.out.println("Esse valor excede a quantidade em estoque!");
				return null;
			}
		} catch (Exception e) {
			System.out.println("Erro ao manipular o produto no carrinho.");
			e.printStackTrace();
		}

		return carrinhoModel;
		
	}
}
