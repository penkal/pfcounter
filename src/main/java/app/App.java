package app;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

public class App {

	public static void main(String[] args) {
		

		ConversationService conversationService = new ConversationService("2017-04-21");
		conversationService.setUsernameAndPassword("d639af77-0f79-4d83-a6d8-b44ffd1b854c", "ijQJSV7sN7SJ");
		String textoMensagem = "Permitir consultar, alterar, inativar e reativar usuário";
		MessageResponse response = conversationService
				.message("4caa9120-7f7b-47f7-a47c-3a02275cd502",
						new MessageRequest.Builder().inputText(textoMensagem).build())
				.execute();
		System.out.println(response);
	}
	
	
}
