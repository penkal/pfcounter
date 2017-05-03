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
	public final String username = "1ccb5365-cf1b-446c-b3c4-2787733db29e";
	public final String password = "2VtLLXgvHfdl";
	public final String wksId = "0f43d37b-b213-4bc0-9c7f-cabff38ada27";
	
	public List<FuncaoTransacional> parseFile(File textFile) {
		try {
			final Scanner scan = new Scanner(textFile);
			return parseScanner(scan);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOG.warning(e.toString());
			return null;
		}
	}

	public List<FuncaoTransacional> parseFile(String text) {
		Scanner scan = new Scanner(text);
		return parseScanner(scan);
	}
	
	private List<FuncaoTransacional> parseScanner(final Scanner scan){
		List<FuncaoTransacional> linhasProcessadas = new ArrayList<FuncaoTransacional>();
		ConversationService conversationService = iniciarConversation();
		while (scan.hasNextLine()) {
			String linha = scan.nextLine();
			linha = linha.replaceAll("\t", "");
			if (linha.length() > 10 && linha.contains(" ")) {
				FuncaoTransacional funcaoTransacional = processarLinha(linha,
						conversationService);
				if (funcaoTransacional != null) {
					linhasProcessadas.add(funcaoTransacional);
				}
			} else {
				LOG.info("Descartando a linha:" + linha);
			}
		}
		scan.close();
		return linhasProcessadas;
		
	}
	public ConversationService iniciarConversation() {
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
