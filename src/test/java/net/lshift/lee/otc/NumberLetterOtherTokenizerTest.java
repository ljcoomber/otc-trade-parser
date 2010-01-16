package net.lshift.lee.otc;

import org.junit.After;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class NumberLetterOtherTokenizerTest {

	private NumberLetterOtherTokenizer tokenizer;

	@After
	public void assertNoMoreTokens() {
		assertFalse(tokenizer.hasMoreTokens());
	}
	
    @Test
    public void emptySourceReturnsEmptyTokenizer() {
        tokenizer = new NumberLetterOtherTokenizer("");
    }
    
    @Test
    public void tokenizerGroupsDigits() {
    	tokenizer = new NumberLetterOtherTokenizer("123");
        assertEquals("123", tokenizer.nextToken());
    }
    
    @Test
    public void tokenizerIncludesDecimalPlaceInNumber() {
    	tokenizer = new NumberLetterOtherTokenizer("1.2");
        assertEquals("1.2", tokenizer.nextToken());
    }
    
    @Test
    public void tokenizerSeparatesEllipsisFromNumber() {
    	tokenizer = new NumberLetterOtherTokenizer("1...2");
        assertEquals("1", tokenizer.nextToken());
        assertEquals("...", tokenizer.nextToken());
        assertEquals("2", tokenizer.nextToken());
    }
    
    @Test
    public void tokenizerIncludesSignWithNumber() {
    	tokenizer = new NumberLetterOtherTokenizer("/+100/-200/");
        assertEquals("/", tokenizer.nextToken());
        assertEquals("+100", tokenizer.nextToken());
        assertEquals("/", tokenizer.nextToken());
        assertEquals("-200", tokenizer.nextToken());
        assertEquals("/", tokenizer.nextToken());
    }
    
    @Test
    public void tokenizerExcludesSignIfJoiningTwoNumbersTogether() {
    	tokenizer = new NumberLetterOtherTokenizer("100-200");
        assertEquals("100", tokenizer.nextToken());
        assertEquals("-", tokenizer.nextToken());
        assertEquals("200", tokenizer.nextToken());
    }
    
    @Test
    public void tokenizerCopesWithSignsAtBeginningOfLine() {
    	tokenizer = new NumberLetterOtherTokenizer("-0.25/2.25");
    	assertEquals("-0.25", tokenizer.nextToken());
    	assertEquals("/", tokenizer.nextToken());
    	assertEquals("2.25", tokenizer.nextToken());
    }

    @Test
    public void tokenizerGroupsLetters() {
    	tokenizer = new NumberLetterOtherTokenizer("abc");
        assertEquals("abc", tokenizer.nextToken());
    }

    @Test
    public void tokenizerSeparatesSymbols() {
    	tokenizer = new NumberLetterOtherTokenizer("1/a");
        assertEquals("1", tokenizer.nextToken());
        assertEquals("/", tokenizer.nextToken());
        assertEquals("a", tokenizer.nextToken());
    }   
    
    @Test
    public void tokenizerProvidesHasMoreSemantics() {
        tokenizer = new NumberLetterOtherTokenizer("1/a");
        assertEquals("1", tokenizer.nextToken());
        assertTrue(tokenizer.hasMoreTokens());
        assertEquals("/", tokenizer.nextToken());
        assertTrue(tokenizer.hasMoreTokens());
        assertEquals("a", tokenizer.nextToken());
        assertFalse(tokenizer.hasMoreTokens());
    }
}
