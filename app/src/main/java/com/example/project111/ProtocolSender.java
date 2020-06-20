package com.example.project111;

import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;


public class ProtocolSender extends Protocol {

    public ProtocolSender(String hostname, int port) {
        super(hostname, port);
    }

    public void Send(Email email)
    {
        SendCommand("helo mail"  , "250");
        SendCommand("MAIL FROM: " + email.getSender(), "555");
        SendCommand("rcpt to: " + email.getReceiver() , "555");
        SendCommand("DATA" , "354");
        SendCommand("Date:" + String.valueOf(email.getDate())
                + "From:" + email.getSender() + "\n"
                + "Subject:" + email.getSubject() +"\n"
                + "To:" + email.getReceiver()
                + " \n.\n", "250" );

    }
    @Override
    public boolean Login(String User, String Password) {
        String EncryptedUser = Base64.encodeToString((User).getBytes() , Base64.URL_SAFE );
        String EncryptedPass = Base64.encodeToString((Password).getBytes() , Base64.URL_SAFE);

            SendCommand("AUTH LOGIN" ,"334");
            SendCommand( EncryptedUser,"334");
            SendCommand( EncryptedPass,"235");



        return false;
    }

    @Override
    public boolean Logout() {
        boolean done =  SendCommand("QUIT" ,"221");
        if(ISConnect()) {
            try {
                socket.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        if(ISConnectToSSL()) {
            try {
                SSLsocket.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;

    }

    @Override
    public boolean CheckEmail(String User, String Password) {
        return  false;
    }
}
