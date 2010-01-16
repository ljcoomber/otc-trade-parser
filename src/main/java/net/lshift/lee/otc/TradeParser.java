package net.lshift.lee.otc;

import jTrolog.engine.Prolog;
import jTrolog.engine.Solution;
import jTrolog.errors.PrologException;
import jTrolog.terms.Term;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * TODO: Separate out Prolog parser and tokenizing required to get to Prolog list
 */
public class TradeParser {
    private Prolog engine = new Prolog("jTrolog.lib.DCGLibrary");


    public TradeParser() throws IOException, PrologException {
        consult(new File("src/main/resources/parser.pl"));
    }
    
    private static void addTree(Solution s, List<Term> terms) {
    	if(s.success()) {
    		terms.add(s.getBinding("Tree"));
    	}
    }

    public List<Term> parse(String message) throws Throwable {
    	List<Term> terms = new ArrayList<Term>();
        try {
            Solution s = engine.solve("phrase(option_list, " + message + ", Tree).");
            addTree(s, terms);
            
            while(engine.hasOpenAlternatives()) {
            	s = engine.solveNext();
            	addTree(s, terms);
            }
             
            return terms;
        } catch(Throwable t) {
            System.err.println("Error processing message:" + message);
            t.printStackTrace();
            throw t;
        }        
    }

    public String toPrologList(String message) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append('[');

        StringReader reader = new StringReader(message);
        Scanner sc = new Scanner(reader);
        while(sc.hasNext()) {
            String token = sc.next();
            tokenizeToken(token, sc.hasNext(), sBuilder);
        }

        // Trim trailing comma if necessary
        if(sBuilder.substring(sBuilder.length() -2).equals(", ")) {
        	sBuilder.delete(sBuilder.length() -2, sBuilder.length());
        }
        sBuilder.append(']');
        
        return sBuilder.toString();
    }
    
    protected void consult(File file) throws IOException, PrologException {
        FileInputStream f = null;
        try {
            f = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            f.read(buffer);
            engine.addTheory(new String(buffer));
        }
        finally {
            if (f != null) try { f.close(); } catch (IOException ignored) { }
        }
    }

    private void tokenizeToken(String token, boolean hasNextToken, StringBuilder sBuilder) {
        if(isJunk(token)) {
            // skip, and delete last comma
            int idx = sBuilder.lastIndexOf(",");
            if(idx > -1) {
                sBuilder.deleteCharAt(idx);
            }
        } else {
            applyNumLetterTokenizer(token, sBuilder);
        }

        // Append comma if next token available, and this is not the first item, and something was appended
        // TODO: Do not convert to string each iteration
        if(hasNextToken && sBuilder.length() > 1 && !sBuilder.toString().endsWith(", ")) {
           sBuilder.append(", ");
        }
    }

    private static void appendToken(String token, boolean hasNextToken, StringBuilder sBuilder) {
    	boolean addedToken = false;
        if(isNumber(token)) {
            // Parse number before appending to smooth out problems Prolog cannot deal with, e.g.,
            // missing leading zero before decimal point
            try {
                sBuilder.append(Integer.parseInt(token));
                addedToken = true;
            } catch(NumberFormatException e) {
                sBuilder.append(Float.parseFloat(token));
                addedToken = true;
            }
        } else if (isSeparator(token)) {
        	// Do nothing
        } else {
            sBuilder.append('\'');
            sBuilder.append(escapeToken(token));
            sBuilder.append('\'');
            addedToken = true;
        }
        
        if(addedToken && hasNextToken) {
            sBuilder.append(", ");
        }
    }
    
    public static boolean isSeparator(String token) {
		return (token.equals(",") || token.equals(":") || token.matches("\\.\\.+"));
	}

	private static String escapeToken(String token) {
        token = token.replace("\'", "''");
        token = token.toLowerCase();
        return token;
    }

    private static void applyNumLetterTokenizer(String token, StringBuilder sBuilder) {
    	NumberLetterOtherTokenizer t = new NumberLetterOtherTokenizer(token);
    	while(t.hasMoreTokens()) {
            appendToken(t.nextToken(), t.hasMoreTokens(), sBuilder);
        }
    }

    private static final Pattern junkP = Pattern.compile("[^\\w]*");

    private static boolean isJunk(String s) {
        return junkP.matcher(s).matches();
    }

    private static boolean isNumber(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
