package app;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest.Builder;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

public class App {

	public static void main(String[] args) {

		ConversationService conversationService = new ConversationService(
				"2017-04-21");
		conversationService.setUsernameAndPassword(
				"d639af77-0f79-4d83-a6d8-b44ffd1b854c", "ijQJSV7sN7SJ");
		String textoMensagem = "Manter pré-cadastro individual de usuário";
		Builder msg = new MessageRequest.Builder().inputText(textoMensagem);
		MessageRequest msgtxt = msg.build();
		String wksId = "4caa9120-7f7b-47f7-a47c-3a02275cd502";
		MessageResponse response = conversationService.message(wksId, msgtxt)
				.execute();
		System.out.println(response);
	}
	
	

}
