package br.com.senai.controller;

import java.util.List;
import java.util.Scanner;

import br.com.senai.model.ProdutoModel;

public class ProdutoController {

	private Scanner sc;

	public ProdutoController() {
		sc = new Scanner(System.in);
	}

	public int opcao() {
		System.out.print("> ");
		return sc.nextInt();
	}
	
	public void menu() {
		System.out.println("\n------ MENU ------");
		System.out.println("1) Cadastrar itens");
		System.out.println("2) Listar estoque");
		System.out.println("3) Editar item");
		System.out.println("4) Remover item");
		System.out.println("5) Realizar venda");                                                                                      
		System.out.println("9) Sair do sistema");
		System.out.println("--------------------");
	}

	public ProdutoModel cadastrarProduto() {
		ProdutoModel produtoModel = new ProdutoModel();

		System.out.println("\n--- CADASTRAR ITENS ---\n");
		System.out.print("Produto: ");
		produtoModel.setNomdeDoProduto(sc.next());
		System.out.print("Pre�o: ");
		produtoModel.setPrecoDoProduto(sc.nextDouble());
		System.out.print("Quantidade:");
		produtoModel.setQuantidadeDeProduto(sc.nextInt());
		produtoModel.setSaldoEmEstoque(produtoModel.getQuantidadeDeProduto() * produtoModel.getPrecoDoProduto());
		
		return produtoModel;
	}
	
	public void consultarProdutos(List<ProdutoModel> produtos) {
		System.out.println("\n----------- PRODUTOS CADASTRADOS -----------\n");
		System.out.printf("| %10s | %8s | %4s | %9s |\n", "Produto", "Pre�o", "Qtd", "R$ Total");
		
//		for (ProdutoModel produtoModel : produtos) {
//			System.out.printf("| %10s | %8s | %4s | %9s |\n", produtoModel.getNomeDoProduto(), produtoModel.getPrecoDoProduto(), produtoModel.getQuantidadeDeProduto(), produtoModel.getSaldoEmEstoque());
//			
//		}
		produtos.forEach(produto -> {
			System.out.printf("| %10s | %8s | %4s | %9s |\n", produto.getNomeDoProduto(),produto.getPrecoDoProduto(), produto.getQuantidadeDeProduto(), produto.getSaldoEmEstoque() );
		});
	}
}

