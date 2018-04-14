import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class Client extends Actor
{
    public BufferedReader in;
    public DataOutputStream out;
    public Socket socket;
    public boolean connected = false;
    
    public Client() {
        System.out.println(this + ": constructed");
    }
    
    public void act() 
    {
            //if (!this.connected) {this.connect();}
            //this.checkIncomingMessages();
            this.send("test");
    }    
    
    public void connect() {
        try {
            this.socket = new Socket("localhost", 1223);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            this.out = out;
            this.connected = true;
            System.out.println(this + ": connected");
        } catch (IOException e) {
            System.out.println(this + ": Failed to connect");
        }
    }
    
    public void send(String data) {
       try {
           //this.out.writeBytes(data);
           this.out.writeUTF(data);
           System.out.println(this + ": [out] " + data);
       } catch (IOException e) {e.printStackTrace();}
    }
    
    public void checkIncomingMessages() {
        String data;
        try {
            if ((data = this.in.readLine()) != null) {
                System.out.println(this + ": [in] " + data);
            }
        } catch (IOException e) {e.printStackTrace();}
    }
    
}
