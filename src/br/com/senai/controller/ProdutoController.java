package br.com.senai.controller;

import java.util.List;
import java.util.Scanner;

import br.com.senai.model.ProdutoModel;

public class ProdutoController {

	private Scanner entrada;

	public ProdutoController() {
		entrada = new Scanner(System.in);
	}

	public int opcao() {
		System.out.print("> ");
		return entrada.nextInt();
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
		produtoModel.setNomeDoProduto(entrada.next());
		System.out.print("Preço: ");
		produtoModel.setPrecoDoProduto(entrada.nextDouble());
		System.out.print("Quantidade:");
		produtoModel.setQuantidadeDeProduto(entrada.nextInt());
		produtoModel.setSaldoEmEstoque(produtoModel.getQuantidadeDeProduto() * produtoModel.getPrecoDoProduto());
		
		return produtoModel;
	}
	
	public List<ProdutoModel> listarProdutos(List<ProdutoModel> produtos) {
		System.out.println("\n----------- PRODUTOS CADASTRADOS -----------\n");
		System.out.printf("| %10s | %8s | %4s | %9s |\n", "Produto", "Preço", "Qtd", "R$ Total");
		
		produtos.forEach(produto -> {
			System.out.printf("| %10s | %8s | %4s | %9s |\n", produto.getNomeDoProduto(),produto.getPrecoDoProduto(), produto.getQuantidadeDeProduto(), produto.getSaldoEmEstoque() );
		});
		return produtos;
	}
	public ProdutoModel editarProduto(List<ProdutoModel> produtos) {
		ProdutoModel produto = new ProdutoModel();
		int idDoProduto, indexDoCampo;
		System.out.println("--- Editar dados de produto ---");
		System.out.print("Informe o Id do produto");
		idDoProduto =  entrada.nextInt();
		
		System.out.println("--- Campos ---");
		System.out.println("1) Nome do produto\n2) Preço unitário\n3) Quantidade");
		System.out.print("Informe o campo que deseja editar: ");
		indexDoCampo = entrada.nextInt();
		
		switch(indexDoCampo) {
		case 1:
			System.out.print("Informe o novo nome para o produto: ");
			produto.setNomeDoProduto(entrada.next());
			
			produto.setPrecoDoProduto(produtos.get(idDoProduto).getPrecoDoProduto());
			produto.setQuantidadeDeProduto(produtos.get(idDoProduto).getQuantidadeDeProduto());
			produto.setSaldoEmEstoque(produtos.get(idDoProduto).getSaldoEmEstoque());
			
			produtos.set(idDoProduto, produto);
			break;
		case 2:
			System.out.print("Informe o novo preço para o produto: ");
			produto.setPrecoDoProduto(entrada.nextDouble());
			
			produto.setNomeDoProduto(produtos.get(idDoProduto).getNomeDoProduto());
			produto.setQuantidadeDeProduto(produtos.get(idDoProduto).getQuantidadeDeProduto());
			produto.setSaldoEmEstoque(produtos.get(idDoProduto).getSaldoEmEstoque());
			
			produtos.set(idDoProduto, produto);
			break;
		case 3:
			System.out.print("Informe a nova quantidade para o produto: ");
			
			
			produto.setPrecoDoProduto(produtos.get(idDoProduto).getPrecoDoProduto());
			produto.setQuantidadeDeProduto(produtos.get(idDoProduto).getQuantidadeDeProduto());
			produto.setSaldoEmEstoque(produtos.get(idDoProduto).getSaldoEmEstoque());
			
			produtos.set(idDoProduto, produto);
			break;
		default:
			
			break;
		}
		return null;
	}
}

