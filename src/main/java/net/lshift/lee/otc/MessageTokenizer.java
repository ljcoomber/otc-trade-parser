package net.lshift.lee.otc;

import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a file into distinct IM <code>Message</code>s.
 */
public class MessageTokenizer {
    private int messageCount = 0;
	private int ignoreCount = 0;
    private File file;
    private static final Pattern headerPattern = Pattern.compile("^Conversation with (.*) on \\d{1,2}/\\d{1,2}/\\d{2,4} \\d{1,2}:\\d{1,2}:\\d{1,2} [A|P]M:");
    private static final Pattern delimiterPattern = Pattern.compile("\\((\\d{1,2}:\\d{1,2}:\\d{1,2} [A|P]M)\\) ");
    private String broker;
	private PrintWriter writer;

    public MessageTokenizer(File file, OutputStream oS) {
        this.file = file;
        this.writer = new PrintWriter(oS);
    }

    public void tokenize(MessageHandler handler) throws Throwable {
        Scanner sc = new Scanner(new FileReader(file));
        sc.useDelimiter(delimiterPattern);

        // First line should be header
        if(sc.hasNext()) {
            String header = sc.next();
            Matcher m = headerPattern.matcher(header.trim());
            if(m.matches()) {
                broker = m.group(1);
            } else {
                throw new Exception("Missing header line");
            }
        }

        while(sc.hasNext()) {
            parseMessage(handler, broker, sc.next());
        }

        sc.close();
    }
  
    /* Pattern to look for likely trade info by matching on spreads */
    public static final Pattern SPREAD_P = Pattern.compile(".*[0-9][/|-][\\-0-9].*", Pattern.DOTALL);
    
    private void parseMessage(MessageHandler handler, String broker, String message) throws Throwable {
        // Break message up between IM sender and message body
        message = message.trim();
        int idx = message.indexOf(':');
        if(idx > -1) {
            String b = message.substring(0, idx);
            String m = message.substring(idx + 2);
            // Drop messages that are not from the original conversation originator
            if(b.equals(broker)) {
            	// Drop messages that do not contain a spread
            	if( SPREAD_P.matcher(m).matches()) {
            		handler.handle((new Message(b, m)));
            		messageCount++;
            	} else {
            		writer.println("IGNORING: " + m);
            		ignoreCount++;
            	}
            	
            }
        } else {
            // Drop message
        }
    }

    public int getMessageCount() {
        return messageCount;
    }
    
    public int getIgnoreCount() {
		return ignoreCount;
	}
}
