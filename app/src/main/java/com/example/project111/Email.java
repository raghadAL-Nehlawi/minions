package com.example.project111;

import android.app.LauncherActivity;
import android.speech.RecognizerIntent;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Email {
    private String Sender;
    private String ReturnPath;
    private String Receiver;
    private String NameSender; // this for Address book
    private String body;
    private String Subject;
    private Date date;
    private String ID_Message;
    private String Boundary;

    //Flags this to filter the emails
    private boolean FlagSent     = false;
    private boolean FlagSpam     = false;
    private boolean FlagReceived = false;
    private boolean FlagDeleted  = false;

    public Email(String sender, String receiver, String NameSender, String Subject, String body, Date date) {
        Sender = sender;
        Receiver = receiver;
        this.body = body;
        this.date = date;
        this.Subject = Subject;
        this.NameSender = NameSender;
    }

    public Email() {
    }

    public Email ToEmail(String message)
         {
             //Take info from ReadEmail method and convert it to Email class

             Email email;
             email = new Email();
             int LastIndex;
             // return path that maybe can by empty if that email not delivered
             LastIndex = message.indexOf("Return-Path: <");
             if(LastIndex > 0){
                         ReturnPath  = message.substring(LastIndex + 14,
                         message.indexOf(">" , LastIndex) );
                         Log.d("EmailReceiver", ReturnPath);}

             // set receiver
             LastIndex = message.indexOf("Delivered-To:");
             if(LastIndex > 0){
                         Receiver =  message.substring(LastIndex + 14,
                         message.indexOf("\n" , LastIndex + 13) );
                         Log.d("Email", Receiver); }
             // set the date
             LastIndex = message.indexOf("Delivery-date: ");
             if(LastIndex > 0){
                 String DateTemporary =  message.substring(LastIndex + 15,
                                         message.indexOf(":"  , LastIndex + 14) + 6);
                 try {
                     SimpleDateFormat format =  new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
                     date = format.parse(DateTemporary);
                 } catch (ParseException e) {
                     e.printStackTrace();
                 }
                 Log.d("Email", date.toString());}

             // set subject
             LastIndex = message.indexOf("Subject: ");
             if(LastIndex > 0){
                 Subject =   message.substring(LastIndex + 9,
                             message.indexOf("\n"  , LastIndex ));
                 Log.d("Email", Subject);}

             //set really sender and his name
             LastIndex  = message.indexOf("From: ");
             NameSender =   message.substring(LastIndex + 6,
                            message.indexOf("<"  , LastIndex ));
             Log.d("Email", NameSender);
             Sender =   message.substring( message.indexOf("<"   , LastIndex ) + 1,
                        message.indexOf(">"  , LastIndex ));
             Log.d("Email", Sender);

             // set ID message
             LastIndex  = message.indexOf("Message-ID: <");
             if(LastIndex > 0) {
                 ID_Message = message.substring(LastIndex + 13,
                              message.indexOf(">", LastIndex + 13));
                 Log.d("EmailID", ID_Message);}


             //Set boundary  this to know when contain (text , HTMl , ...) starts and when ends
             LastIndex  = message.indexOf("boundary=\"") ;
             if(LastIndex > 0) {
                 Boundary = message.substring(LastIndex + 10,
                            message.indexOf("\"", LastIndex + 10));
                 Log.d("Email", Boundary); }
             if(LastIndex == -1){
                 LastIndex = message.lastIndexOf("boundary=");
                 Boundary = message.substring(LastIndex + 9,
                         message.indexOf("\n", LastIndex + 9));
                 Log.d("Email", Boundary);
             }

             // set body message "not completed"
             LastIndex = message.lastIndexOf(Boundary, LastIndex + Boundary.length());
             String temp = message.substring(LastIndex);


             // set spam flag
             LastIndex  = message.indexOf("X-Spam-Flag: ");
             if(LastIndex > 0) {
                 String Spam = message.substring(LastIndex + 13,
                         message.indexOf("\n", LastIndex));
                 if (Spam.contains("NO"))
                     FlagSpam = false;
                 else
                     FlagSpam = false;
                 Log.d("Spam? ", String.valueOf(FlagSpam));
             }








             return email;
         }
    public String getSender() { return Sender; }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}
