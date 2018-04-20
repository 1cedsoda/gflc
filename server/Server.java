import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Server extends Actor
{ 
    //Class Maps
    public Map<Integer, Integer> players = new HashMap<Integer, Integer>();
    public Map<Integer, Crab> crabs = new HashMap<Integer, Crab>();
    public Map<Integer, Worm> worms = new HashMap<Integer, Worm>();
    public Map<Integer, Lobster> lobsters = new HashMap<Integer, Lobster>();
    
    public List<BufferedReader> in = new ArrayList<>();
    public List<DataOutputStream> out = new ArrayList<>();
    public Acceptor acceptor;
    public boolean acceptorRunning = false;
    public boolean worldInitalized = false;
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
        if (!this.worldInitalized) {this.init();}
        this.checkIncomingMessages();
    }
    
    public void init() {
        this.worldInitalized = true;
        for(int i = 0; i < 10; i++) {
            int oid = this.addSprite("Lobster");
            this.lobsters.get(oid).sendAllProperties();
        }
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
            this.initClient();
        } catch (IOException e) {e.printStackTrace();}
    }
    
    public void initClient() {
        //Krabbe erzeugen
            int cid = this.out.size() - 1;
            int oid = this.addSprite("Crab");
            this.crabs.get(oid).cid = cid;
            this.players.put(cid, oid);
            //Wenn die Krabbe erfolgreich erzeugt wurde
            if(cid != -1) {
                int totalcrabs = this.crabs.size();
                int crabsdone = 0;
                int crabsindex = 0;
                while(crabsdone < totalcrabs) {
                    if(this.crabs.containsKey(crabsindex)) {
                        crabsdone++;
                        this.crabs.get(crabsindex).sendAllProperties();
                        }
                    crabsindex++;
                }
                
                int totalworms = this.worms.size();
                int wormsdone = 0;
                int wormsindex = 0;
                while(wormsdone < totalworms) {
                    if(this.worms.containsKey(wormsindex)) {
                        wormsdone++;
                        this.worms.get(wormsindex).sendAllProperties();
                        }
                    wormsindex++;
                }
                
                int totallobsters = this.lobsters.size();
                int lobstersdone = 0;
                int lobstersindex = 0;
                while(lobstersdone < totallobsters) {
                    if(this.lobsters.containsKey(lobstersindex)) {
                        lobstersdone++;
                        this.lobsters.get(lobstersindex).sendAllProperties();
                        }
                    lobstersindex++;
                }
                this.send(cid, "SET~Crab~"+oid+"~player~true"); 
            }
    }
    
    public int newOID() {
        this.nextoid++;
        return(this.nextoid - 1);
    }
    
    /*senden von Daten an einen Stream an einem bestimmten array-index
     */
    public void send(int cid, String data) {
        if(cid < 0) {
            for(int i = 0; i < this.out.size(); i++) {
                    try {
                        this.out.get(i).writeUTF(data + "\n");
                        System.out.println(this + ": [out]["+i+"] " + data);
                    } catch (SocketException e) {
                        System.out.println(this + ": connection lost to client " + i);
                    } catch (IOException e) {
                        System.out.println("A");
                        e.printStackTrace();}
            }
        } else {
            try {
                this.out.get(cid).writeUTF(data + "\n");
                System.out.println(this + ": [out]["+cid+"] " + data);
            } catch (SocketException e) {
                System.out.println(this + ": connection lost to client " + cid);
            } catch (IOException e) {
                System.out.println("A");
                e.printStackTrace();}
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
                    this.handleMessage(data.substring(2));
                }
            } catch (EOFException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void handleMessage(String data) {
        String[] com = data.split("~");
        if(com[0].equals("SET")) {
            String type = com[1]; //Object class
            int oid = Integer.parseInt(com[2]); //Object ID
            String key = com[3]; //Variable name
            String value = com[4]; //new variable value
            this.setObjectProperty(type, oid, key, value);
        }
    }
    
    public void setObjectProperty(String type, int oid, String key, String value) {
        if(type.equals("Crab")) {
            if(this.crabs.containsKey(oid)) {
                this.crabs.get(oid).setProperty(key, value);
            }
        } else if(type.equals("Lobster")) {
            if(this.lobsters.containsKey(oid)) {
                this.lobsters.get(oid).setProperty(key, value);
            }
        } else if(type.equals("Worm")) {
            if(this.worms.containsKey(oid)) {
                //this.worms.get(oid).setProperty(key, value);
            }
        }
    }
    
    public int addSprite(String type) {
        if(type.equals("Crab")) {
            int oid = newOID();
            Crab crab = new Crab(oid);
            getWorld().addObject(crab, Greenfoot.getRandomNumber(1200), Greenfoot.getRandomNumber(400));
            this.crabs.put(oid, crab);
            return(oid);
        } else if(type.equals("Lobster")) {
            int oid = newOID();
            Lobster lobster = new Lobster(oid);
            getWorld().addObject(lobster, Greenfoot.getRandomNumber(1200), Greenfoot.getRandomNumber(400));
            this.lobsters.put(oid, lobster);
            return(oid);
        } else if(type.equals("Worm")) {
            int oid = newOID();
            Worm worm = new Worm(oid);
            getWorld().addObject(worm, Greenfoot.getRandomNumber(1200), Greenfoot.getRandomNumber(400));
            this.worms.put(oid, worm);
            return(oid);
        } else {
            return(-1);
        }
    }
    
    public void removeClient(int cid) {
        int oid = this.players.get(cid);
        System.out.println(this.in.size());
        this.in.set(cid, null);
        this.out.set(cid, null);
        System.out.println(this.in.size());
        this.send(-1, "REMOVE~Crab~"+oid);
    }
}
