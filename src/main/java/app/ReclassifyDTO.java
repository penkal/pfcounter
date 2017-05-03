package app;

import java.io.Serializable;

import model.FuncaoTransacional;
import model.TipoTransacao;

public class ReclassifyDTO implements Serializable{

	private static final long serialVersionUID = 2417494632637961749L;

	private FuncaoTransacional funcaoTransacional;
	private TipoTransacao novoTipo;
	private boolean irrelevant;
		
	public ReclassifyDTO(FuncaoTransacional funcaoTransacional, TipoTransacao novoTipo, 
			boolean irrelevant) {
		super();
		this.funcaoTransacional = funcaoTransacional;
		this.novoTipo = novoTipo;
		this.irrelevant = irrelevant;
	}
	public FuncaoTransacional getFuncaoTransacional() {
		return funcaoTransacional;
	}
	public void setFuncaoTransacional(FuncaoTransacional funcaoTransacional) {
		this.funcaoTransacional = funcaoTransacional;
	}
	
	public TipoTransacao getNovoTipo() {
		return novoTipo;
	}
	public void setNovoTipo(TipoTransacao novoTipo) {
		this.novoTipo = novoTipo;
	}
	public boolean isIrrelevant() {
		return irrelevant;
	}
	public void setIrrelevant(boolean irrelevant) {
		this.irrelevant = irrelevant;
	}
	
	
}
