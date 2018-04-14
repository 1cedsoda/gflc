import java.net.*;
import java.io.*;
import java.util.*;
import greenfoot.*;
public class Acceptor extends Thread
{
    public Server server;
    public int port;
    public World world;
    public Acceptor(World world) {
        this.world = world;
        System.out.println(this + ": started subthread");
    }
    public void act() {
    }
    public void updateServerLink() {
            List<Server> servers = new ArrayList<Server>();
            servers = this.world.getObjects(Server.class);
            this.server = servers.get(0);
    }
    public void run(){
        try{
            ServerSocket serversocket = new ServerSocket(1223);
            serversocket.setReuseAddress(true);
            while(this.server != null) {
                System.out.println(this + ": listening");
                Socket socket = serversocket.accept();
                System.out.println("Socket: " + socket);
                this.updateServerLink();
                this.server.addClient(socket);
                System.out.println(this + ": client connected");
            }
        }catch(IOException e){
            e.printStackTrace();
            System.out.println(this + ": failed");
        }
    }
}
