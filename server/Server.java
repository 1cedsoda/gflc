import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Server extends Actor
{
    public List<BufferedReader> in = new ArrayList<>();
    public List<PrintWriter> out = new ArrayList<>();
    public int port;
    public Acceptor acceptor;
    public boolean acceptorRunning = false;
    /* Konstruktor
     */
    public Server(int port) {
            this.port = port;
            System.out.println(this + ": constructed");
    }
    public void destroyOtherServers() {
        try{
            World world = getWorld();
            List<Server> servers = new ArrayList<Server>();
            servers = world.getObjects(Server.class);
            for(int i = 0; i < servers.size(); i++) {
                if (servers.get(i) != this) {
                    world.removeObject(servers.get(i));
                    System.out.println(this + ": " + servers.get(i) + " removed.");
                }
            }
        } catch (Exception e) {e.printStackTrace();}
    }
    
    /* Lässt wiederholt Nachrichten abfragen
     */
    public void act() {
        if (!this.acceptorRunning) {this.startAcceptor();} //der connection listener wird einmal gestartet
        this.cIM();
    }
    public void startAcceptor() {
        this.destroyOtherServers();
        System.out.println(this + ": started");
        this.acceptor = new Acceptor(getWorld());
        this.acceptor.start();
        this.acceptorRunning = true;
    }
    /* Die Datenstreams eines Clients hinzufügen. 
     * Wird extern von einem "Acceptor"-Thread aufgerufen, welcher alle Verbindungs-Anfragen annimmt
     */
    public void addClient(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //in.mark(1);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            this.in.add(in);
            this.out.add(out);
            System.out.println("Client added.");
        } catch (IOException e) {e.printStackTrace();}
    }
    public void showAcceptor() {
            World world = getWorld();
            List<Acceptor> acceptors = new ArrayList<Acceptor>();
            System.out.println(world.getObjects(Acceptor.class));
            
    }
    /*Alle input streams nach neuen Nachrichten abfragen
     */
    public void checkIncomingMessages() {
        //System.out.println(this.in);
        for (int i = 0; i < this.in.size(); i++) {
            String data;
            System.out.println(this.in.get(i));
            try {
                System.out.println("I");
                if((data = this.in.get(i).readLine()) != null){
                    System.out.println("II");
                    System.out.println("\"" + data + "\"");
                    System.out.println("III");
                }
                System.out.println("IV");
                this.in.get(i).reset();
                System.out.println("IV");
            } catch (IOException e) {
                this.in.remove(i);
                e.printStackTrace();
            }
        }
        System.out.println("DONE");
    }
    
    public void cIM() {
        for (int i = 0; i < this.in.size(); i++) {
            try {
                System.out.println(this.in.get(i).readLine() + "\n");
            } catch (IOException e) {e.printStackTrace();}
        }
    }
    /*senden von Daten an einen Stream an einem bestimmten array-index
     */
    public void send(int id, String data) {
        this.out.get(id).write(data);
    }
}
