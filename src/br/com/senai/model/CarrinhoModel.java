package br.com.senai.model;

public class CarrinhoModel {

	private int quantidadeDeItensNoCarrinho;
	private int idDoProduto;
	private double valorTotalPorItem;

	public CarrinhoModel() {
	}

	public CarrinhoModel(int quantidadeDeItensNoCarrinho, int idDoProduto, double valorTotalPorItem) {
		super();
		this.quantidadeDeItensNoCarrinho = quantidadeDeItensNoCarrinho;
		this.idDoProduto = idDoProduto;
		this.valorTotalPorItem = valorTotalPorItem;
	}

	public int getQuantidadeDeItensNoCarrinho() {
		return quantidadeDeItensNoCarrinho;
	}

	public void setQuantidadeDeItensNoCarrinho(int quantidadeDeItensNoCarrinho) {
		this.quantidadeDeItensNoCarrinho = quantidadeDeItensNoCarrinho;
	}

	public int getIdDoProduto() {
		return idDoProduto;
	}

	public void setIdDoProduto(int idDoProduto) {
		this.idDoProduto = idDoProduto;
	}

	public double getValorTotalPorItem() {
		return valorTotalPorItem;
	}

	public void setValorTotalPorItem(double valorTotalPorItem) {
		this.valorTotalPorItem = valorTotalPorItem;
	}

}
