package net.lshift.lee.otc;

/**
 * Splits a string into a series of tokens, delimited by whether there is a
 * change to the type of character between numbers, letters and others.
 *
 * For example, the source string "abc12345/54321", would produce the following
 * tokens: abc, 12345, /, 54321. Note that a decimal place forms part of a
 * number. 
 */
public class NumberLetterOtherTokenizer {
    private enum State {
        NUMBER, LETTER, OTHER
    };

    private String source;
    private int i = 0;
    private State state = null;

    public NumberLetterOtherTokenizer(String source) {
        this.source = source;
    }

    public boolean hasMoreTokens() {
        return i < source.length();
    }

    private boolean setState(char c) {
        if(Character.isDigit(c) ||									// Include digits
        		(c == '.' && 										// and decimal points
        				(next() != '.' && previous() != '.')) || 	//   but not if they are part of an ellipsis 
        		(isSign(c) && Character.isDigit(next()) &&			// and signs before digits
        				(!Character.isDigit(previous())))) {		//   but not if they join two digits together			
            return setState(State.NUMBER);
        } else if(Character.isLetter(c)) {
            return setState(State.LETTER);
        } else {
            return setState(State.OTHER);
        }
    }

    public String nextToken() {
        int initialI = i;
        for (; i < source.length(); i++) {
            char c = source.charAt(i);
            if(setState(c)) {
                break;
            }
        }

        return source.substring(initialI, i);
    }
    
    private char next() {
    	return i < source.length() - 1 ? source.charAt(i + 1) : (char) 0;
    }
    
    private char previous() {
    	return i > 0  ? source.charAt(i - 1) : (char) 0;
    }
    
    private boolean isSign(char c) {
    	return c == '+' || c == '-';
    }
    
    private boolean setState(State state) {
        boolean isChange = (this.state != state) && this.state != null;
        this.state = state;
        return isChange;
    }
}
