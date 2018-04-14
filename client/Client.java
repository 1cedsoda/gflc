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
    public int a;
    
    public Client() {
        System.out.println(this + ": constructed");
    }
    
    public void act() 
    {
            this.send(Integer.toString(this.a));
            this.a++;
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
        if(this.connected) {
            try {
                this.out.writeUTF(data + "\n");
                System.out.println(this + ": [out] " + data);
            } catch (IOException e) {
                System.out.println(this + ": connection lost");
            }
        } else {
            System.out.println(this + ": not connected");
        }
    }
    
    public void checkIncomingMessages() {
        if(this.connected) {
            String data;
            try {
                while(this.in.ready()) {
                    data = this.in.readLine();
                    System.out.println(this + ": [in] " + data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(this + ": no connected");
        }
    }
}
