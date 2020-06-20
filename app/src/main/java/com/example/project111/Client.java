package com.example.project111;


public class Client {
    private String EmailUser;
    private String Password;
    private  ProtocolReceiver receiver;
    private ProtocolSender SMTP;


    public void send(String To, String Subject, String Body){
        SMTP.ConnectToServer();
        SMTP.ReadResponse();
        SMTP.Login(EmailUser, Password);



    }

}
