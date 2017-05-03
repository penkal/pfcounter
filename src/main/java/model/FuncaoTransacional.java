package model;

public class FuncaoTransacional {
	String nome;
	TipoTransacao tipo;
	double confianca;
	int pontuacao;

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

}
