package net.lshift.lee.otc;

import static junit.framework.Assert.assertEquals;
import static net.lshift.lee.otc.MessageTokenizer.SPREAD_P;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class MessageTokenizerTest {
	@Test
	public void testRegex() {
		assertTrue(SPREAD_P.matcher("3/5").matches());
		assertTrue(SPREAD_P.matcher("3/-5").matches());
		assertTrue(SPREAD_P.matcher("3-5").matches());		
	}
	
    @Test
    public void tokenizingBrokerASampleShouldProduce7Messages() throws Throwable {
        MessageTokenizer mt = new MessageTokenizer(new File("src/test/resources/sample-logs/broker-a.txt"), System.out);
        mt.tokenize(new TestMessageHandler());
        assertEquals(7, mt.getMessageCount());
    }

    class TestMessageHandler implements MessageHandler {
        public void handle(Message message) {
            System.out.println(message);
        }
    }
}
