package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.FuncaoTransacional;
import model.TipoTransacao;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.Intent;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest.Builder;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

public class DocumentReader {

	public List<FuncaoTransacional> parseFile(File textFile) {
		List<FuncaoTransacional> linhasProcessadas = new ArrayList<FuncaoTransacional>();
		try {
			Scanner scan = new Scanner(textFile);
			while (scan.hasNextLine()) {
				String linha = scan.nextLine();
				linha = linha.replaceAll("\t", "");
				if (linha.length() > 10 && linha.contains(" ")) {
					linhasProcessadas.add(processarLinha(linha));
				} else {
					System.out.println("Descartando a linha:" + linha);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return linhasProcessadas;
	}

	private FuncaoTransacional processarLinha(String linha) {
		ConversationService conversationService = new ConversationService(
				"2017-04-21");
		conversationService.setUsernameAndPassword(
				"d8e1b624-2697-4f43-b341-57037ab19846", "45LTxKOJof5w");
		Builder msg = new MessageRequest.Builder().inputText(linha);
		MessageRequest msgtxt = msg.build();
		String wksId = "7547197d-9868-4fd3-bdff-614af8e5f84a";
		MessageResponse response = conversationService.message(wksId, msgtxt)
				.execute();
		if (response.getIntents().isEmpty()) {
			System.out.println("Linha '" + linha + "' processada: irrelevant");
			return null;
		} else {
			return processarResposta(response);
		}

	}

	private FuncaoTransacional processarResposta(MessageResponse response) {
		Intent intent = response.getIntents().get(0);
		FuncaoTransacional funcao = new FuncaoTransacional(
				response.getInputText(), TipoTransacao.valueOf(intent
						.getIntent()), intent.getConfidence());
		System.out.println("Linha :'" + funcao.getNome() + "' confianca: "
				+ funcao.getConfianca() + " tipo:" + funcao.getTipo());
		return funcao;
	}

}
