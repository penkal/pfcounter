package app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import model.FuncaoTransacional;

import org.junit.Test;

public class DocumentReaderTest {

	@Test
	public void testPartseFile() {
		File dv = new File(
				"/home/00003871932/git/pfcounter/src/test/resources/DV-Teste-v2");
		assertTrue(dv.exists());

		DocumentReader sut = new DocumentReader();
		List<FuncaoTransacional> funcoesProcessadas = sut.parseFile(dv);
		assertFalse(funcoesProcessadas.isEmpty());
	}

}
