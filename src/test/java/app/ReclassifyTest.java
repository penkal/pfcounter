package app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.FuncaoTransacional;
import model.TipoTransacao;

import org.junit.Test;

import com.ibm.watson.developer_cloud.conversation.v1.model.ExampleResponse;

public class ReclassifyTest {

	private static final Logger LOG = Logger.getAnonymousLogger(); 
	
	@Test
	public void testReclassify() {
		File dv = new File("src/test/resources/DV-Teste-v4");
		assertTrue(dv.exists());

		DocumentReader sut = new DocumentReader();
		List<FuncaoTransacional> funcoesProcessadas = sut.parseFile(dv);
		assertFalse(funcoesProcessadas.isEmpty());
		
		List<ReclassifyDTO> listaDTO = new ArrayList<ReclassifyDTO>();
		
		LOG.info("########################################");
		int i = 0;
		for (FuncaoTransacional ft : funcoesProcessadas){
			if (ft==null){
				continue;
			}
			boolean irrelevant =  (i % 5 == 0);
			listaDTO.add(new ReclassifyDTO(ft, rotateTipo(ft.getTipo()), irrelevant));
			i++;
		}
		
		Reclassify reclassify = new Reclassify();
		List<ExampleResponse> listaReponse = reclassify.send(listaDTO);
		assertFalse(listaReponse.isEmpty());
		LOG.info("########################################");
		
		funcoesProcessadas = sut.parseFile(dv);
	}
	
	private TipoTransacao rotateTipo(TipoTransacao tipo){
		switch (tipo){
		case CE : return TipoTransacao.CRUD;
		case CRUD : return TipoTransacao.EE;
		case EE : return TipoTransacao.SE;
		case SE : return TipoTransacao.CE;
		}
		return TipoTransacao.SE;
	}

}
