package com.example.project111;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class POP3 extends ProtocolReceiver{
    public POP3(String hostname, int port) {
        super(hostname, port);
    }

    public List<Email> ReadEmail()
    {
        List<Email> emails = new ArrayList<>();
        SendCommand("LIST","OK");
        String Line;
        Map<String, String> EmailMap = new HashMap<>();
        while ( !(Line = ReadResponse()).equals(".") )
        {
            String [] Parts = Line.split(" ");
            EmailMap.put(Parts[0], Parts[1]);
            NumberOfNewMessage ++;
        }

        for(int i = 1; i <= NumberOfNewMessage; i ++) {
            SendCommand("RETR " + i, "OK");
            String Message = "";
            while (!(Line = ReadResponse()).equals(".")) {
                Message += Line + "\n";
            }
            Email email = new Email();
             email.ToEmail(Message);
            emails.add(email);

        }
        return emails;

    }
    @Override
    public boolean Login(String User, String Password) {
        boolean done =
                 SendCommand("USER " + User, "OK");
                 SendCommand("PASS " + Password, "OK");
        return done;

    }

    @Override
    public boolean Logout() {
        return false;
    }

    @Override
    public boolean CheckEmail(String User, String Password) {
        return false;
    }

}
