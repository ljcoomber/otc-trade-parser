package net.lshift.lee.otc;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import jTrolog.errors.PrologException;

import java.io.IOException;

import org.junit.Test;

public class MiscTradeParserTest extends AbstractTradeParserTest {
	@Test
    public void testBodyTokenizer() {
        String body = "brt q1 11 80.00 put x8400, 412/426";
        assertEquals("['brt', 'q', 1, 11, 80.0, 'put', 'x', 8400, 412, '/', 426]",
                parser.toPrologList(body));        
    }
    
    @Test
    public void testMultiline() {
    	String tokenized = "['wti', 'apo', 'q', 1, '-', 11, 90.0, '/', 110.0, 'cs', 'x', 83.5, 2.86, '/', 2.89, 'wti', 'apo', 1, 'h', 11, 77.0, '/', 67.0, 'ps', 'x', 84.0, 2.45, '/', 2.48, 'wti', 'apo', 'q', 111, 60, '/', 70, 'ps', 'vs', 83.0, 1.24, '/', 1.32, 'wti', 'apo', 'q', 211, 95, '/', 115, 1, '*', 2, 'cs', 'vs', 85.0, 2.28, '/', 2.35, 'wti', 'american', 'z', 10, 85.0, 'call', 'x', 81.0, 1.13, '/', 1.2, 'wti', 'american', 'z', 11, 75.0, '/', 95.0, 'fence', 'x', 85.75, 40, '/', 43, '(', 'cp', ')', 'brt', 'q', 1, 11, 80.0, 'put', 'x', 8400, 417, '/', 420]";
    	testTokenizedLine(tokenized);
    }
    
    @Test
    public void testSeparator() {
    	assertTrue(TradeParser.isSeparator(","));
    	assertTrue(TradeParser.isSeparator(".."));
    	assertTrue(TradeParser.isSeparator("..."));
    	assertTrue(TradeParser.isSeparator("...."));
    	assertTrue(TradeParser.isSeparator(":"));
    	assertFalse(TradeParser.isSeparator("."));
    }
    
    @Test
    public void testSeparatorAtEnd() throws IOException, PrologException {
        TradeParser parser = new TradeParser();
    	String pList = parser.toPrologList("Z11 120c LIVE  198/201 now..");
    	assertEquals("['z', 11, 120, 'c', 'live', 198, '/', 201, 'now']", pList);
    }

    /**
     * Test method used to play with ad-hoc lines.
     */
    @Test
    public void workbench() {
    	String tokenized = "['wti', '(', 'lo', ')', 'f', 11, 65, 'put', 'x', 80.0, 54, '/', 66]";
    	testTokenizedLine(tokenized);
    } 
}
