package br.com.senai.controller.produto;

import java.sql.Connection;
import java.util.Scanner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.dao.DataBaseConnection;

public class DeletaProduto {
	
	Scanner entrada = new Scanner(System.in);
	ListaProduto listaProduto;
	private Connection connection;
	
	public DeletaProduto() {
		connection = DataBaseConnection.getInstance().getConnection();
	}	
	
	public boolean verificaSeExisteProduto(int idDoProduto) {
		PreparedStatement preparedStatement;
		
		try {String sql = "SELECT * FROM produto WHERE codigoDoProduto = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idDoProduto);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.next()) {
				System.out.println("Este produto não existe.");
				return false;

			} else {
				return true;
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				return false;
		}
	}
	
	public void removerProduto() {
		PreparedStatement preparedStatement;
		listaProduto = new ListaProduto();
		
		System.out.println("--- REMOVER PRODUTO ---");
		
		if (listaProduto.listarProdutos() == null) {
			return;
		}

		System.out.print("Informe o ID do produto a ser removido: ");
		int idDoProduto = entrada.nextInt();
		
		try {
			
			if (!verificaSeExisteProduto(idDoProduto)) {
				return;
			}
			
			String sql = "DELETE FROM produto WHERE codigoDoProduto = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idDoProduto);
			preparedStatement.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Não foi possível excluir esta informação!");
			return;
		}
	}
}
