package br.com.senai.controller.produto;

import java.sql.Connection;
import java.util.Scanner;

import java.sql.PreparedStatement;

import br.com.dao.DataBaseConnection;
import br.com.senai.model.ProdutoModel;

public class CadastraProduto {
	
	Scanner entrada = new Scanner(System.in);
	ProdutoModel produtoModel;
	private Connection connection;
	
	public CadastraProduto() {
		connection = DataBaseConnection.getInstance().getConnection();
	}

	public ProdutoModel cadastrarProduto() {
		produtoModel = new ProdutoModel();

		System.out.println("\n--- CADASTRAR ITENS ---\n");
		System.out.print("Produto: ");
		produtoModel.setNomeDoProduto(entrada.next());
		System.out.print("Preço: ");
		produtoModel.setPrecoDoProduto(entrada.nextDouble());
		System.out.print("Quantidade:");
		produtoModel.setQuantidadeDeProduto(entrada.nextInt());
		produtoModel.setSaldoEmEstoque(produtoModel.getQuantidadeDeProduto() * produtoModel.getPrecoDoProduto());

		try {
			String sql = "insert into produto (nomeDoProduto, precoDoProduto, quantidadeDeProduto, saldoEmEstoque) values (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, produtoModel.getNomeDoProduto());
			preparedStatement.setDouble(2, produtoModel.getPrecoDoProduto());
			preparedStatement.setInt(3, produtoModel.getQuantidadeDeProduto());
			preparedStatement.setDouble(4, produtoModel.getSaldoEmEstoque());
			
			preparedStatement.execute();
			
		} catch (Exception e){
			System.out.println("<-< Erro ao cadastrar os dados >->");
		}
		
		return produtoModel;
	}
}
