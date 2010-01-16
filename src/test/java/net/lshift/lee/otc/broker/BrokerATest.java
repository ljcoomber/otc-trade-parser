package net.lshift.lee.otc.broker;

import net.lshift.lee.otc.AbstractDcgParserTest;

import org.junit.Ignore;
import org.junit.Test;

public class BrokerATest extends AbstractDcgParserTest {

	@Test
	public void vanillaPut() {
		testPlainLine("BRT Q1 11 80.00 Put x8400, 412/426");		
	}
		
	@Test
	public void vanillaCall() {
		testPlainLine("WTI American Z10 85.00 Call x81.00, 1.11/1.22");
	}
	
	@Test
	public void putSpread() {
		testPlainLine("WTI APO 1H1177.00/67.00 PS x84.00,  2.40/2.50");
	}
	
	@Test
	public void callSpread() {
		testPlainLine("WTI APO Q1-11 90.00/110.00 cs x 83.50, 2.84/2.90");
	}
	
	@Test
	public void straddle() {
		testPlainLine("WTI American H11 83.00 Straddle, 12.20/12.50");
	}
	
	@Test
	// TODO: Match at end of this line is very vague
	public void fence() {
		testPlainLine("WTI APO 2H11 100.00/65.00 1*1.5 Fence x 85.50,  12/27 (1.5 Put)");
	}
	
	@Test
	@Ignore
	public void putRoll() {
		testPlainLine("WTI American F11/M11 80.00 Put Roll, (82.15/84.50), 3.04/3.14");
	}

	@Test
	@Ignore	
	public void callRoll() {
		testPlainLine("WTI American Z10/H11 straddle roll (81.00/83.00) 6.80/6.90");
	}

	@Test
	@Ignore	
	public void straddleRoll() {
		testPlainLine("WTI American Z10/H11 straddle roll (81.00/83.00, 6.80/7.00");
	}
}
