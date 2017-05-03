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
	public final String username = "d8e1b624-2697-4f43-b341-57037ab19846";
	public final String password = "45LTxKOJof5w";
	public final String wksId = "7547197d-9868-4fd3-bdff-614af8e5f84a";

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
					LOG.info("Descartando a linha:" + linha);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOG.warning(e.toString());
		}
		return linhasProcessadas;
	}

	public ConversationService iniciarConversation(){
		ConversationService conversationService = new ConversationService(
				versao);
		conversationService.setUsernameAndPassword(username, password);
		return conversationService;
	}
	
	private FuncaoTransacional processarLinha(String linha) {
		ConversationService conversationService = iniciarConversation();
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
