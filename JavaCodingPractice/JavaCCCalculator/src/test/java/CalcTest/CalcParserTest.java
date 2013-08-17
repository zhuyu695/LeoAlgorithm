package CalcTest;

import static org.junit.Assert.assertEquals;
import junit.framework.TestSuite;

import org.junit.Test;

import CalcParser.Calc;
import CalcParser.ParseException;

public class CalcParserTest {
	@Test
	public void testSimple() throws ParseException {
		String src = "1+5+6\n5+8+9\n9-1-6\n5*6/3\n(2+3)*(4+6)/25\n";
		Calc cc = new Calc(src);
		cc.Start();
		//assertEquals(12, out);
	}
}
