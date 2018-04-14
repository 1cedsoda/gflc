import java.net.*;
import java.io.*;
import java.util.*;
import greenfoot.*;
public class Acceptor extends Thread
{
    public Server server;
    public int port;
    public Acceptor(Server server) {
        this.server = server;
    }
    public void run(){
        try{
            ServerSocket serversocket = new ServerSocket(1223);
            serversocket.setReuseAddress(true);
            System.out.println(this + ": listening");
            while(this.server != null) {
                Socket socket = serversocket.accept();
                this.server.addClient(socket);
            }
            System.out.println(this + ": serverlink is null");
        }catch(BindException e){
            System.out.println(this + ": another thread is already running. Commiting suicide...");
            System.exit(1);
        }catch(IOException e){
            e.printStackTrace();
            System.out.println(this + ": failed");
        }
    }
}
