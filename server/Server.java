import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Server extends Actor
{
    public List<BufferedReader> in = new ArrayList<>();
    public List<DataOutputStream> out = new ArrayList<>(); 
    public Acceptor acceptor;
    public boolean acceptorRunning = false;
    public int port;
    
    /* Konstruktor
     */
    public Server(int port) {
            this.port = port;
            System.out.println(this + ": constructed");
    }
    
    /* Lässt wiederholt Nachrichten abfragen
     */
    public void act() {
        if (!this.acceptorRunning) {this.startAcceptor();} //der connection listener wird einmal gestartet
        this.checkIncomingMessages();
    }
    
    /* Started den Thread, welcher die Verbindungsanfragen annimmt.
     */
    public void startAcceptor() {
        Acceptor acceptor = new Acceptor(this);
        acceptor.start();
        this.acceptorRunning = true;
    }
    
    /* Die Datenstreams eines Clients hinzufügen. 
     * Wird extern von einem "Acceptor"-Thread aufgerufen, welcher alle Verbindungs-Anfragen annimmt
     */
    public void addClientStreams(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            this.in.add(in);
            this.out.add(out);
            System.out.println(this + ": Client connected");        
        } catch (IOException e) {e.printStackTrace();}
    }
    
    /*senden von Daten an einen Stream an einem bestimmten array-index
     */
    public void send(int id, String data) {
        try {
            this.out.get(id).writeUTF(data + "\n");
            System.out.println(this + ": [out] " + data);
        } catch (SocketException e) {
            System.out.println(this + ": connection lost to client " + id);
        } catch (IOException e) {e.printStackTrace();}
    }
    
    /*Alle input streams nach neuen Nachrichten abfragen
     */
    public void checkIncomingMessages() {
        for (int i = 0; i < this.in.size(); i++) {
            String data;
            try {
                while(this.in.get(i).ready()) {
                    data = this.in.get(i).readLine();
                    System.out.println(this + ": [in][" + i + "] " + data);
            }
            } catch (EOFException e) {
                System.out.println("alright");
            } catch (IOException e) {
                this.in.remove(i);
                e.printStackTrace();
            }
        }
    }
}
