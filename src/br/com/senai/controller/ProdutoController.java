package br.com.senai.controller;

import java.util.List;
import java.util.Scanner;

import br.com.senai.model.ProdutoModel;

public class ProdutoController {

	private Scanner entrada;
	private Scanner qtd;

	public ProdutoController() {
		entrada = new Scanner(System.in);
		qtd = new Scanner(System.in);
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
		System.out.println("5) Adicionar no Carrinho");                                                                                      
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
		System.out.printf("| %2s | %10s | %8s | %4s | %9s |\n", "ID", "Produto", "Preço", "Qtd", "R$ Total");
		
		for(int i = 0; i < produtos.size(); i++) {
			System.out.printf("| %2s | %10s | %8s | %4s | %9s |\n",i+1 ,produtos.get(i).getNomeDoProduto(),produtos.get(i).getPrecoDoProduto(), produtos.get(i).getQuantidadeDeProduto(), produtos.get(i).getSaldoEmEstoque() );
		};
		return produtos;
	}
	public ProdutoModel editarProduto(List<ProdutoModel> produtos) {
		ProdutoModel produto = new ProdutoModel();
		int idDoProduto, indexDoCampo;
		
		if (produtos.size() <= 0) {
			System.out.println("Não possui produtos para serem editados");
			return null;
		}
		
		listarProdutos(produtos);
		
		System.out.println("--- EDITAR DADOS DE PRODUTO ---");
		System.out.print("Informe o Id do produto: ");
		idDoProduto =  entrada.nextInt() - 1;
		if(idDoProduto >= produtos.size()) {
			System.out.println("Este produto não existe");
			return null;
		}

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
			produto.setSaldoEmEstoque(produtos.get(idDoProduto).getQuantidadeDeProduto() * produto.getPrecoDoProduto());
			
			produtos.set(idDoProduto, produto);
			break;
		case 3:
			System.out.print("Informe a nova quantidade para o produto: ");
			produto.setQuantidadeDeProduto(entrada.nextInt());
			
			produto.setPrecoDoProduto(produtos.get(idDoProduto).getPrecoDoProduto());
			produto.setNomeDoProduto(produtos.get(idDoProduto).getNomeDoProduto());
			produto.setSaldoEmEstoque(produtos.get(idDoProduto).getPrecoDoProduto() * produto.getQuantidadeDeProduto());
			
			produtos.set(idDoProduto, produto);
			break;
		default:
			System.out.println("Opção inválida");
			break;
			}
		return produto;
	}
	
	public void removerProduto(List<ProdutoModel> produtos) {
		System.out.println("--- REMOVER PRODUTO ---");
		if (produtos.size() <= 0) {
			System.out.println("Não possui produtos para serem removidos.");
			return;
		}
		listarProdutos(produtos);
		System.out.println("Informe o ID do produto a ser removido: ");
		int idDoProduto = entrada.nextInt() - 1;
		
		if(idDoProduto > produtos.size()) {
			System.out.println("Este produto não foi cadastrado.");
			return;
		}
		
		produtos.remove(idDoProduto);
	}
	public ProdutoModel adicionarNoCarrinho(List<ProdutoModel> produtos, List<ProdutoModel> compras) {
		ProdutoModel compra = new ProdutoModel();
		ProdutoModel produto = new ProdutoModel();
		
		System.out.println("Qual o ID do produto?");
		int ide = entrada.nextInt() - 1;
		compra.setNomeDoProduto(produtos.get(ide).getNomeDoProduto());
		System.out.println("Qual a quantidade desse produto?");
		int quanti = qtd.nextInt();
		compra.setQuantidadeDeProduto(quanti);
		
		compra.setPrecoDoProduto(produtos.get(ide).getPrecoDoProduto());
		compra.setNomeDoProduto(produtos.get(ide).getNomeDoProduto());
		compras.add(ide, compra);
		
		produto.setQuantidadeDeProduto(produtos.get(ide).getQuantidadeDeProduto() - quanti);
		
		produto.setPrecoDoProduto(produtos.get(ide).getPrecoDoProduto());
		produto.setNomeDoProduto(produtos.get(ide).getNomeDoProduto());
		produto.setSaldoEmEstoque(produtos.get(ide).getPrecoDoProduto() * produto.getQuantidadeDeProduto());
		
		System.out.println("------------ CARRINHO ------------");
		System.out.printf("| %8s | %6s | %10s |\n", "Nome", "Preço", "Quantidade");
		for(int i = 0; i < compras.size(); i++) {
			System.out.printf("| %8s | %6s | %10s |\n",compras.get(i).getNomeDoProduto(), compras.get(i).getPrecoDoProduto(), compras.get(i).getQuantidadeDeProduto());
		};
		return produto;
	}
}

