package com.example.project111;

public abstract class ProtocolReceiver extends  Protocol{
    protected int NumberOfNewMessage = 0;
    public ProtocolReceiver(String hostname, int port) {
        super(hostname, port);
    }

    public int getNumberOfNewMessage() { return NumberOfNewMessage; }
}
