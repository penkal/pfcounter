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
		String filePath = "src/test/resources/DV-Teste-v2";
		File dv = new File(filePath);
		assertTrue(dv.exists());

		DocumentReader sut = new DocumentReader();
		List<FuncaoTransacional> funcoesProcessadas = sut.parseFile(filePath);
		assertFalse(funcoesProcessadas.isEmpty());
	}

}
