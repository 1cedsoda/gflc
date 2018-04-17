import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Server extends Actor
{ 
    public Map<Integer, Crab> crabs = new HashMap<Integer, Crab>();
    public List<BufferedReader> in = new ArrayList<>();
    public List<DataOutputStream> out = new ArrayList<>();
    public Acceptor acceptor;
    public boolean acceptorRunning = false;
    public int port;
    public int nextoid = 0;
    
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
            //Krabbe erzeugen
            int oid = this.addSprite("Crab");
            //Wenn die Krabbe erfolgreich erzeugt wurde
            if(oid >= 0) {
                //Arrays start at 0
                int cid = this.out.size() - 1;
                //Neuverbundenem Spieler die Rechte geben seine Krabbe zu steuern.
                this.send(cid, "SET~Crab~"+oid+"~player~true"); 
            }
        } catch (IOException e) {e.printStackTrace();}
    }
    
    public int newOID() {
        this.nextoid++;
        return(this.nextoid - 1);
    }
    
    /*senden von Daten an einen Stream an einem bestimmten array-index
     */
    public void send(int cid, String data) {
        if(cid <= 0) {
            try {
                this.out.get(cid).writeUTF(data + "\n");
                System.out.println(this + ": [out] " + data);
            } catch (SocketException e) {
                System.out.println(this + ": connection lost to client " + cid);
            } catch (IOException e) {e.printStackTrace();}
        } else {
            for(int i = 0; i > this.out.size(); i++) {
                try {
                    this.out.get(i).writeUTF(data + "\n");
                    System.out.println(this + ": [out] " + data);
                } catch (SocketException e) {
                    System.out.println(this + ": connection lost to client " + i);
                } catch (IOException e) {e.printStackTrace();}
            }
        }
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
                    this.handleMessage(data);
                }
            } catch (EOFException e) {
                System.out.println("alright");
            } catch (IOException e) {
                this.in.remove(i);
                e.printStackTrace();
            }
        }
    }
    
    public void handleMessage(String data) {
        String[] com = data.split("~");
        if(com[0] == "SET") {
            String type = com[1]; //Object class
            int oid = Integer.parseInt(com[2]); //Object ID
            String key = com[3]; //Variable name
            String value = com[4]; //new variable value
            this.setObjectProperty(type, oid, key, value);
        }
    }
    
    public void setObjectProperty(String type, int oid, String key, String value) {
        if(type == "Crab") {
            if(this.crabs.containsKey(oid)) {
                this.crabs.get(oid).setProperty(key, value);
            }
        }
    }
    
    public int addSprite(String type) {
        if(type == "Crab") {
            int oid = newOID();
            Crab crab = new Crab(oid);
            getWorld().addObject(crab, 0, 0);
            this.crabs.put(oid, crab);
            return(oid);
        } else {
            return(-1);
        }
    }
}
