package net.lshift.lee.otc;

import jTrolog.errors.PrologException;
import jTrolog.terms.Term;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

// TODO: Delete?
public class AppTest {

    protected int doTest(String filename) throws Throwable {
        File file = new File("src/test/resources/sample-logs/", filename);
        long start = System.currentTimeMillis();

        String outputDir = "output/";
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String outfile = outputDir + filename + "-parse-results.txt";
        OutputStream oS = null;
        try {
        	oS = new FileOutputStream(outfile);
            MessageTokenizer mt = new MessageTokenizer(file, oS);
        	TestMessageHandler handler = new TestMessageHandler(oS);
            mt.tokenize(handler);
            long end = System.currentTimeMillis();
            float r = (handler.success) / (float) mt.getMessageCount();
            float pc = r * 100L;
            
            System.out.println("Results for " + filename);
            System.out.println("Total messages: " + (mt.getMessageCount() + mt.getIgnoreCount()));
            System.out.println("Ignored: " + mt.getIgnoreCount());
            System.out.println("Successfully parsed: " + handler.success);
            System.out.println("Ambiguous: " + handler.ambiguous);
            System.out.println("Success rate: " + pc + "%");
            System.out.println("Processing time: " + (end - start) + "ms");
            return handler.success;
        }
        finally {
        	if(oS != null) {
        		oS.close();
        	}
        }        
    }


    class TestMessageHandler implements MessageHandler {
        private DcgParser parser;
        private PrintWriter writer;

        int success = 0;
        int failure = 0;
        int ambiguous = 0;

        public TestMessageHandler(OutputStream os) throws PrologException, IOException {
            parser = new DcgParser();
            writer = new PrintWriter(os);
        }

        public void handle(Message message) throws Throwable {
        	writer.println("PARSING: " + message.getBody());
            String tokenized = parser.toPrologList(message.getBody());
            List<Term> result = parser.parse(tokenized);
            if (result.isEmpty()) {
                failure++;
                writer.println("FAILED: " + tokenized);
            } else {
            	if(result.size() == 1) {
            		success++;
            		writer.print("SUCCEEDED: " );
            	} else {
            		ambiguous++;
            		writer.println("WARNING: AMBIGUOUS RESULT:");
            	}
            	
            	Iterator<Term> it = result.iterator();
            	while (it.hasNext()) {
					Term term = (Term) it.next();
					writer.println(term.toString());
				}
            }
            
            writer.println();
            writer.flush();
        }
    }

}
