package net.lshift.lee.otc;

public interface MessageHandler {
    void handle(Message message) throws Throwable;
}
