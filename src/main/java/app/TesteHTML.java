package app;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.FuncaoTransacional;
import model.TipoTransacao;

public class TesteHTML {
	private static final String BASE_PATH = "./src/main/resources";

	public static void main(String[] args) throws IOException {
		String template = new String(Files.readAllBytes(Paths.get(BASE_PATH + "/template.html")));
		StringBuilder html = new StringBuilder();
		
		List<FuncaoTransacional> funcoes = new ArrayList<FuncaoTransacional>();
		funcoes.add(new FuncaoTransacional("Manter Usuário", TipoTransacao.CRUD, 0.8777D));
		funcoes.add(new FuncaoTransacional("Incluir Tipo de Alguma Coisa", TipoTransacao.ENTRADA_EXTERNA, 0.6277D));
		funcoes.add(new FuncaoTransacional("Consultar algo de interassante", TipoTransacao.SAIDA_EXTERNA, 0.4777D));
		
		for(FuncaoTransacional func : funcoes){
			html.append(func.toHTML());
		}
		
		Files.write(Paths.get(BASE_PATH + "/teste.html"), template.replace("%%SUBSTITUIR%%", html.toString()).getBytes());
	}
}
