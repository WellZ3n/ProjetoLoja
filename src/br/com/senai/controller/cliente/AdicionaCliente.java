package br.com.senai.controller.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import br.com.dao.DataBaseConnection;

public class AdicionaCliente {

	private Connection connection;
	Scanner entrada = new Scanner(System.in);
	int id;
	
	public AdicionaCliente() {
		connection = DataBaseConnection.getInstance().getConnection();
	}
	
	public int definirCliente() {
		
		PreparedStatement preparedStatement;
		
		System.out.print("Informe o nome do cliente: ");
		String nome = entrada.next();
		try {
			
			String sqlBuscarValor = "SELECT * FROM clientes WHERE nome = ?";
			ResultSet resultSet;
			preparedStatement = connection.prepareStatement(sqlBuscarValor);
			preparedStatement.setString(1, nome);
			resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.next()) {
				//cadastrar
				
				String sqlCadastro = "INSERT INTO clientes (nome) VALUES (?)";
				preparedStatement = connection.prepareStatement(sqlCadastro);
				preparedStatement.setString(1, nome);
				preparedStatement.execute();
				
				id = resultSet.getInt("id");
				
				System.out.println("Seja bem vindo " + nome);
				System.out.println("Sua identificação foi gerada automaticamente: " + id);
				
				return id;
			} else {
				//logar
				
				id = resultSet.getInt("id");
				
				System.out.println("Bem vindo de volta " + nome);
				System.out.println("Identificação: " + id);
				
				return id;
			}
			
		} catch (Exception e) {
			System.out.println("ERRO");
			e.printStackTrace();
			return 0;
		}
	}
}
