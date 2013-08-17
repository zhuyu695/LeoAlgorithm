package Parser;

import static org.junit.Assert.assertEquals;

import junit.framework.TestSuite;

import org.junit.Test;

public class ParserTest {
	@Test
	public void testSimple() throws ParseException {
		String src = "1+(2+3)*4;";
		String out = EG1.Parse(src);
		assertEquals("OK.", out);
	}
}
