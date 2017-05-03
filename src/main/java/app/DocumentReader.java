package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import model.FuncaoTransacional;
import model.TipoTransacao;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.Intent;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest.Builder;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

public class DocumentReader {
	private static final Logger LOG = Logger.getAnonymousLogger();
	public final String versao = "2017-04-21";
	public final String username = "b7bf276e-1fd2-4225-8f31-6fe19b10daf0";
	public final String password = "rGIgcLIabvNU";
	public final String wksId = "13fbe900-13ba-4d90-895d-ea8fe58a6507";

	public List<FuncaoTransacional> parseFile(File textFile) {
		List<FuncaoTransacional> linhasProcessadas = new ArrayList<FuncaoTransacional>();
		try {
			Scanner scan = new Scanner(textFile);
			ConversationService conversationService = iniciarConversation();
			while (scan.hasNextLine()) {
				String linha = scan.nextLine();
				linha = linha.replaceAll("\t", "");
				if (linha.length() > 10 && linha.contains(" ")) {
					FuncaoTransacional funcaoTransacional = processarLinha(
							linha, conversationService);
					if (funcaoTransacional != null) {
						linhasProcessadas.add(funcaoTransacional);
					}
				} else {
					LOG.info("Descartando a linha:" + linha);
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOG.warning(e.toString());
		}
		return linhasProcessadas;
	}

	public List<FuncaoTransacional> parseFile(String text) {
		List<FuncaoTransacional> linhasProcessadas = new ArrayList<FuncaoTransacional>();
		Scanner scan = new Scanner(text);
		ConversationService conversationService = iniciarConversation();
		while (scan.hasNextLine()) {
			String linha = scan.nextLine();
			linha = linha.replaceAll("\t", "");
			if (linha.length() > 10 && linha.contains(" ")) {
				FuncaoTransacional funcaoTransacional = processarLinha(linha, conversationService);
				linhasProcessadas.add(funcaoTransacional);
			} else {
				LOG.info("Descartando a linha:" + linha);
			}
		}
		scan.close();
		return linhasProcessadas;
	}

	public ConversationService iniciarConversation(){
		ConversationService conversationService = new ConversationService(
				versao);
		conversationService.setUsernameAndPassword(username, password);
		return conversationService;
	}
	
	private FuncaoTransacional processarLinha(String linha,
			ConversationService conversationService) {
		Builder msg = new MessageRequest.Builder().inputText(linha);
		MessageRequest msgtxt = msg.build();
		MessageResponse response = conversationService.message(wksId, msgtxt)
				.execute();
		if (response.getIntents().isEmpty()) {
			LOG.info("Linha '" + linha + "' processada: irrelevant");
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
		LOG.info("Linha :'" + funcao.getNome() + "' confianca: "
				+ funcao.getConfianca() + " tipo:" + funcao.getTipo());
		return funcao;
	}

}
