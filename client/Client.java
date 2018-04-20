import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Client extends Actor
{
    public Map<Integer, Crab> crabs = new HashMap<Integer, Crab>();
    public Map<Integer, Lobster> lobsters = new HashMap<Integer, Lobster>();
    //public Map<Integer, Worm> worms = new HashMap<Integer, Worm>();
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
            if(!this.connected)this.connect();
            this.checkIncomingMessages();
    }    
    
    public void connect() {
        if(!this.connected) {
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
        } else {
            System.out.println(this + ": Already connected. Reset and retry.");
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
                    this.handleMessage(data.substring(2));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(this + ": no connected");
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
        } else if (com[0].equals("ADD")) {
            String type = com[1]; //Object class
            int oid = Integer.parseInt(com[2]); //Object ID
            if(type.equals("Crab")) {
                if(!this.crabs.containsKey(oid)) {
                    Crab crab = new Crab(oid);
                    getWorld().addObject(crab, 0, 0);
                    this.crabs.put(oid, crab);
                }
            } else if(type.equals("Lobster")) {
                if(!this.lobsters.containsKey(oid)) {
                    Lobster lobster = new Lobster(oid);
                    getWorld().addObject(lobster, 0, 0);
                    this.lobsters.put(oid, lobster);
                }
            } else {
                System.out.println(this + ": Failed to summon object " + type);
            }
        } else if (com[0].equals("REMOVE")) {
            String type = com[1]; //Object class
            int oid = Integer.parseInt(com[2]); //Object ID
            if(type.equals("Crab")) {
                getWorld().removeObject((Actor)this.crabs.get(oid));
                this.crabs.remove(oid);
            }
         } else if (com[0].equals("COLLIDE")) {
            String type = com[1]; //Object class
            int oid = Integer.parseInt(com[2]); //Object ID
            String enemyType = com[3];
            int enemyOid = Integer.parseInt(com[4]);
            if(type.equals("Crab")) {
                this.crabs.get(oid).collide(enemyType, enemyOid);
            }
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
        } else {
            System.out.println(this + ": Can't find class '"+type+"'");
        }
    }
}
