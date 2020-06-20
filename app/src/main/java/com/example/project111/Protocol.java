package com.example.project111;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import static javax.net.ssl.SSLSocket.*;

public abstract class Protocol {
    protected String Hostname;
    protected int Port;
    protected Socket    socket;
    protected SSLSocket SSLsocket;
    PrintWriter Out;
    BufferedReader In;
    public Protocol(String hostname, int port) {
        Hostname = hostname;
        Port = port;

    }

    public boolean ConnectToServer()
    {
        if(socket == null)
          try {
                socket = new Socket(Hostname, Port);
                In = new BufferedReader(new InputStreamReader( socket.getInputStream()));
                Out = new PrintWriter( socket.getOutputStream() ,true);
                return true;
              } catch (IOException e)
          { e.printStackTrace();
            return false;
          }
        return true;

    }
    public boolean ConnectToServerSSL()
    {
        if(SSLsocket == null)
        try {
            SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
            SSLsocket = (SSLSocket)factory.createSocket(Hostname, Port);
            SSLsocket.startHandshake();
            In = new BufferedReader(new InputStreamReader( SSLsocket.getInputStream()));
            Out = new PrintWriter(SSLsocket.getOutputStream() ,true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public abstract boolean Login(String User , String Password);
    public abstract boolean Logout();
    public abstract boolean CheckEmail(String User, String Password);
    public String ReadResponse()
    {
        String Response = "";
       if(In != null) {
           try {
               Response += In.readLine();
               Log.d("response", Response);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }


        return Response;
    }
    public boolean SendCommand(String com, String res)
    {
        Out.println(com);
        String Response = ReadResponse();
        if(Response.contains(res))
            return true;
        return false;
    }
    public String  SendCommandAndReadWhile(String command,String stop){
        Out.println(command);
        String Return = "";
        String Line;
        try {
            while (!(Line = ReadResponse()).contains(stop))
            {
                Return += Line + "\n";
            }
        }catch (Exception e){

        }
        return Return;
    }
    public boolean ISConnectToSSL()
    {
        if(SSLsocket != null)
            return SSLsocket.isConnected();
        return false;
    }
    public boolean ISConnect()
    {
        if(socket != null)
            return socket.isConnected();
        return false;
    }
}
