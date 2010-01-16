package net.lshift.lee.otc.broker;

import net.lshift.lee.otc.AbstractTradeParserTest;

import org.junit.Test;

// 
// 
public class BrokerBTest extends AbstractTradeParserTest {

	@Test
	public void vanillaPut1() {
		testPlainLine("F 83p vs 8300....442/445... 44d");
	}
	
	@Test
	public void vanillaCall1() {
		testPlainLine("Z12 110c vs 8730...640/650");
	}
	
	@Test
	public void vanillaCall2() {
		testPlainLine("Electro M   M 60p/100c fence vs 8150...60/70");
	}	
	
	@Test
	public void flatCall() {
		testPlainLine("Q1 Flat Call 17/25");
	}
	
	@Test
	public void putSpread1() {
		testPlainLine("X 68p/71p vs 7750...17/18");
	}
	
	@Test
	public void callSpread() {
		testPlainLine("Electro M  Z 85c/100c 1x3 vs 7750...58/68");
	}
	
	@Test
	public void callSpreadWithVernacular() {
		testPlainLine("OTC  H 85/100 cs vs 84.50 466/473 37d, ppr interest");
	}
	
	@Test
	public void straddle() {
	}
	
	@Test
	public void fence() {
		testPlainLine("Electro M   M 60p/100c fence vs 8150...60/70");
	}
	
	@Test
	public void putRoll() {
	}

	@Test
	public void callRoll() {
	}

	@Test
	public void straddleRoll() {
	}
	
	@Test
	public void calendarSpread1() {
		testPlainLine("OTC Crude CSO:  X/Z -200/-500 ps 15/22");
	}
	
	@Test
	public void calendarSpread2() {
		testPlainLine("OTC Crude CSO:  X/Z -200/-500 ps cso 19/26");
	}
	
	@Test
	public void calendarSpread3() {
		testPlainLine("X/Z -100p 22/30, X/Z -150p 13/20, Z/F -100p 30/37");
	}
	
	@Test
	// TODO: What is the implication of CSO with no calendar spread
	public void calendarSpread4() {
		testPlainLine("OTC Crude CSO  2H11 -50c/+50c 38/41 now");
	}
	
	@Test
	public void calendarSpread5() {
		testPlainLine("OTC Crude CSO:  X/Z -2.00p   13/18");
	}
	
	@Test
	public void calendarSpread6() {
		testPlainLine("Electro M  X 68p/71p vs 7750...17/18");
	}
	
	@Test
	public void calendarSpread7() {
		testPlainLine("OTC: Z/F -175p 17/20"); 
	}
		

	
	@Test
	public void CalendarSpreadStrip() {
		testPlainLine("OTC: F/G-G/H -125p strip 20/27");
	}
	
	@Test
	public void trailingJunk() {
		testPlainLine("X 7650p vs 8170... 36/38 now");
	}
	
	@Test
	public void averagePriceOptionWithMoreJunk() {
		testPlainLine("OTC  APO 1Q11 105c vs 82.50 74/81 now more");
	}

	@Test
	public void averagePriceOptionWithMuchMoreJunk() {
		testPlainLine("OTC  APO 1Q11 105c vs 82.50 74/81 now much more");
	}

	
	@Test
	public void averagePriceOption() {
		testPlainLine("OTC  APO 1Q11 105c vs 82.50 74/81 now");
	}
	
	@Test
	// TODO: Should this be calendar spread?
	public void testOTC() {
		
	}
}
