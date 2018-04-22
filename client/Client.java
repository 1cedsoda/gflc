import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Client extends Actor
{
    public Map<Integer, Crab> crabs = new HashMap<Integer, Crab>();
    public Map<Integer, Lobster> lobsters = new HashMap<Integer, Lobster>();
    public Map<Integer, Worm> worms = new HashMap<Integer, Worm>();
    public Map<Integer, Bomb> bombs = new HashMap<Integer, Bomb>();
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
            //if(!this.connected)this.connect();
            this.checkIncomingMessages();
    }    
    
    public boolean connect(String host) {
        if(!this.connected) {
            try {
                this.socket = new Socket(host, 1223);
                this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                this.out = out;
                this.connected = true;
                System.out.println(this + ": connected");
                return(true);
            } catch (IOException e) {
                System.out.println(this + ": Failed to connect to " + host);
                this.connected = false;
                return(false);
            }
        } else {
            System.out.println(this + ": Already connected. Reset and retry.");
            this.connected = false;
            return(false);
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
            String value;
            try{
                value = com[4]; //new variable value
            } catch (Exception e) {
                value = "";
            }
            this.setObjectProperty(type, oid, key, value);
        } else if (com[0].equals("ADD")) {
            String type = com[1]; //Object class
            int oid = Integer.parseInt(com[2]); //Object ID
            if(type.equals("Crab")) {
                if(!this.crabs.containsKey(oid)) {
                    Crab crab = new Crab(oid);
                    getWorld().addObject(crab, 0, 0);
                    crab.initText();
                    this.crabs.put(oid, crab);
                }
            } else if(type.equals("Lobster")) {
                if(!this.lobsters.containsKey(oid)) {
                    Lobster lobster = new Lobster(oid);
                    getWorld().addObject(lobster, 0, 0);
                    this.lobsters.put(oid, lobster);
                }
            } else if(type.equals("Worm")) {
                if(!this.worms.containsKey(oid)) {
                    Worm worm = new Worm(oid);
                    getWorld().addObject(worm, 0, 0);
                    this.worms.put(oid, worm);
                }
            } else if(type.equals("Bomb")) {
                if(!this.bombs.containsKey(oid)) {
                    Bomb bomb = new Bomb(oid);
                    getWorld().addObject(bomb, 0, 0);
                    this.bombs.put(oid, bomb);
                }
            } else {
                System.out.println(this + ": Failed to summon object " + type);
            }
        } else if (com[0].equals("REMOVE")) {
            String type = com[1]; //Object class
            int oid = Integer.parseInt(com[2]); //Object ID
            if(type.equals("Crab")) {
                Crab crab = this.crabs.get(oid);
                getWorld().removeObject((Actor)this.crabs.get(oid).textField);
                getWorld().removeObject((Actor)this.crabs.get(oid));
                this.crabs.remove(oid);
                if(crab.player) {
                    this.gameOver();
                }
            } else if(type.equals("Lobster")) {
                getWorld().removeObject((Actor)this.lobsters.get(oid));
                this.lobsters.remove(oid);
            } else if(type.equals("Worm")) {
                getWorld().removeObject((Actor)this.worms.get(oid));
                this.worms.remove(oid);
            } else if(type.equals("Bomb")) {
                getWorld().removeObject((Actor)this.bombs.get(oid));
                this.bombs.remove(oid);
            }
         } else if (com[0].equals("COLLIDE")) {
            String type = com[1]; //Object class
            int oid = Integer.parseInt(com[2]); //Object ID
            String enemyType = com[3];
            int enemyOid = Integer.parseInt(com[4]);
            if(type.equals("Crab")) {
                try {
                    this.crabs.get(oid).collide();
                } catch(Exception e) {
                    System.out.println(this + ": collision failure");
                }
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
        } else if(type.equals("Worm")) {
            if(this.worms.containsKey(oid)) {
                this.worms.get(oid).setProperty(key, value);
            }
        } else if(type.equals("Bomb")) {
            if(this.bombs.containsKey(oid)) {
                this.bombs.get(oid).setProperty(key, value);
            }
        } else {
            System.out.println(this + ": Can't find class '"+type+"'");
        }
    }
    
    public void gameOver() {
        System.out.println("GAME OVER");
        getWorld().removeObjects(getWorld().getObjects(Crab.class));
        getWorld().removeObjects(getWorld().getObjects(Text.class));
        getWorld().removeObjects(getWorld().getObjects(Lobster.class));
        getWorld().removeObjects(getWorld().getObjects(Worm.class));
        DynamicText dt = new DynamicText("  Press RESET ");
        getWorld().addObject(dt,90,585);
        GameOver go = new GameOver();
        getWorld().addObject(go,600,300);
        try{
            getWorld().removeObjects(getWorld().getObjects(Client.class));
        } catch (Exception e) {}
        Greenfoot.stop();
    }
}
