package net.lshift.lee.otc;

public class Message {
    private String sender;
    private String body;

    public Message(String sender, String body) {
        this.sender = sender;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
