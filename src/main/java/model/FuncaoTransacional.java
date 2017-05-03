package model;

public class FuncaoTransacional {
	String nome;
	TipoTransacao tipo;
	double confianca;
	int pontuacao;
	
	public FuncaoTransacional(){}
	
	public FuncaoTransacional(String nome, String tipo, double confianca, int pontuacao){
		super();
		this.nome = nome;
		this.tipo = TipoTransacao.valueOf(tipo);
		this.confianca = confianca;
		this.pontuacao = pontuacao;
	}

	public FuncaoTransacional(String nome, TipoTransacao tipo, double confianca) {
		super();
		this.nome = nome;
		this.tipo = tipo;
		this.confianca = confianca;
		this.pontuacao = tipo.getPontos();
	}

	public String getNome() {
		return nome;
	}

	public TipoTransacao getTipo() {
		return tipo;
	}

	public double getConfianca() {
		return confianca;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public String toHTML() {
		return "<p class=\"" + obterClasseConfianca() + "\"> " + nome + " </p>";
	}

	private String obterClasseConfianca() {
		if (confianca >= 0.85D) {
			return "conf1";
		} else if (confianca >= 0.6D) {
			return "conf2";
		} else {
			return "conf3";
		}
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipo(TipoTransacao tipo) {
		this.tipo = tipo;
	}

	public void setConfianca(double confianca) {
		this.confianca = confianca;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

}
