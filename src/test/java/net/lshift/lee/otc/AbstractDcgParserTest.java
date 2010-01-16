package net.lshift.lee.otc;

import static org.junit.Assert.fail;
import jTrolog.terms.Term;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;

public abstract class AbstractDcgParserTest {
    protected DcgParser parser;


    @Before
    public void init() throws Exception {
         parser = new DcgParser();
    }

    public void testPlainLine(String line) {
    	String tokenized = parser.toPrologList(line);
    	System.out.println("Tokenized: " + tokenized);
    	testTokenizedLine(tokenized);
    }
    
    public void testTokenizedLine(String tokenized) {
    	List<Term> result = null;
		try {
			result = parser.parse(tokenized);
		} catch (Throwable t) {
			fail(t.getMessage());
			throw new RuntimeException(t);
		}

    	try {
	    	if(result.isEmpty()) {
	    		fail("No results");
	    	} else if(result.size() > 1) {    	
	    		fail("Too many results");
	    	} else {
	    		// success
	    	}	    	
    	}
    	finally {
    		Iterator<Term> it = result.iterator();
    		while (it.hasNext()) {
				Term term = (Term) it.next();
				System.out.println("RESULT: " + term.toString());
			}
    	}
    }
}
