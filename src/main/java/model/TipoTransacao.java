package model;

public enum TipoTransacao {
	
	CONSULTA_EXTERNA("CE"),
	ENTRADA_EXTERNA("EE"),
	SAIDA_EXTERNA("SE"),
	CRUD("CRUD");
	
	private String classe;
	
	public String getClasse() {
		return classe;
	}

	private TipoTransacao(String classe){
		this.classe = classe;
	}
}
