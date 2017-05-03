package model;

public enum TipoTransacao {

	CE("Consulta Externa", 4), EE("Entrada Externa", 4), SE("Consulta Externa",
			5), CRUD("CRUD", 20), DESCARTE("Descartar", 0);

	private String descricao;
	private int pontos;

	private TipoTransacao(String descricao, int pontos) {
		this.descricao = descricao;
		this.pontos = pontos;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public int getPontos() {
		return this.pontos;
	}
}
