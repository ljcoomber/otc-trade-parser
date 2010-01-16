package net.lshift.lee.otc.broker;

import net.lshift.lee.otc.AbstractDcgParserTest;
import org.junit.Ignore;
import org.junit.Test;

public class BrokerCTest extends AbstractDcgParserTest {

	@Test
	public void vanillaPut() {
		testPlainLine("GASOIL AMERICAN: Nov10 680 Put x 681, 22.25/22.75");		
	}
		
	@Test
	public void vanillaCall() {
		testPlainLine("WTI: (LO) Z10 100 Call x 82.00, 8/13");
	}
	
	@Test
	public void putSpread() {
		testPlainLine("WTI: 2H11 60/80 1x2 Put Spread x86.50, 320/335");
	}
	
	@Test
	public void callSpread() {
		testPlainLine("WTI (LO) F11 88/98 Call Spread x 80.00, 116/126");
	}
	
	@Test
	@Ignore
	public void straddle() {
		testPlainLine("WTI (LO) Z/F Straddle Spread 2.40/2.60");
	}
	
	@Test
	public void fence() {
		testPlainLine("WTI: Q211 70/110 Fence x84.50, 185/205");
	}
	
	@Test
	public void putRoll() {
		// None available
	}

	@Test
	@Ignore	
	public void callRoll() {
		testPlainLine("GASOIL: Q211/Q411 ATM Call Roll (745/765), 20.25/23.25");
	}

	@Test
	public void straddleRoll() {
		// None available
	}
}
