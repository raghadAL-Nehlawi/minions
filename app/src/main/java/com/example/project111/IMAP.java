package com.example.project111;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class IMAP extends ProtocolReceiver {
    List <String> FoldersNames = new ArrayList<>();

    public IMAP(String hostname, int port) {
        super(hostname, port);
    }

    public void FilledFolderName()
    {
        int LastIndex = 1;
        String name;
        String ListFolder = SendCommandAndReadWhile(". LIST \"\"  \"*\"", "OK List completed");
        while(LastIndex > 0 && LastIndex < ListFolder.length() ) {
            LastIndex = ListFolder.indexOf("\".\"", LastIndex);
            name = ListFolder.substring( LastIndex + 4  , ListFolder.indexOf("\n",LastIndex) );
            LastIndex = ListFolder.indexOf("\n",LastIndex) + 1 ;
            Log.d("Email", name );
            FoldersNames.add(name);
        }

    }

    public List<Email> SelectFolderAndReadEmails(String FolderName){
        List<Email> emails = new ArrayList<>();
        String Response = SendCommandAndReadWhile(". SELECT " + FolderName, "Select completed");
        int numMessage = Integer.valueOf(Response.substring(
                            Response.lastIndexOf("*" , Response.indexOf("EXISTS")) + 2,
                            Response.indexOf("EXISTS") ).replaceAll("\\s+",""));
        for(int i = 1; i <= numMessage; i ++){
           String response =
                   SendCommandAndReadWhile(". FETCH " + i + " body[] ", "OK Fetch completed");
           Email email = new Email();
           email = email.ToEmail(response);
           emails.add(email);
        }

        Log.d("Folder Number" , String.valueOf(numMessage));
        return emails;
    }


    @Override
    public boolean Login(String User, String Password) {
        return SendCommand(". LOGIN " + User + " " + Password, "OK");
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
