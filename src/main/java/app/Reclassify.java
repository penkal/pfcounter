package app;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.ExampleCollectionResponse;
import com.ibm.watson.developer_cloud.conversation.v1.model.ExampleResponse;
import com.ibm.watson.developer_cloud.http.ServiceCall;

public class Reclassify {

	private static final Logger LOG = Logger.getAnonymousLogger();
	
	public List<ExampleResponse> send(List<ReclassifyDTO> lista){
		DocumentReader docReader = new DocumentReader();
		ConversationService conversationService = docReader.iniciarConversation();
		List<ExampleResponse> listaResponse = new ArrayList<ExampleResponse>();
		for (ReclassifyDTO dto : lista){
			if (dto.isIrrelevant()){
				listaResponse.add(conversationService.createCounterexample(docReader.wksId, dto.getFuncaoTransacional().getNome()).execute());
				LOG.info("Marcando '" + dto.getFuncaoTransacional().getNome() + "' como irrelevante." );
			} else {
			if (conversationService.getExample(docReader.wksId, dto.getFuncaoTransacional().getTipo().name(), 
					dto.getFuncaoTransacional().getNome()).execute() != null){
				conversationService.deleteExample(docReader.wksId, dto.getFuncaoTransacional().getTipo().name(), 
						dto.getFuncaoTransacional().getNome()).execute();				
			}
				listaResponse.add(conversationService.createExample(docReader.wksId, dto.getNovoTipo().name(), 
						dto.getFuncaoTransacional().getNome()).execute());
				LOG.info("Mudando tipo de '" + dto.getFuncaoTransacional().getNome() + 
						"' de '" + dto.getFuncaoTransacional().getTipo() + "' para '" + dto.getNovoTipo() );
			}
		
		}
		return listaResponse;
	}
	
}
