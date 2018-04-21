import java.util.*;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Crab extends Animal
{
    public int oid;
    public int cid;
    public int lives = 5;
    public GreenfootImage okCrab = new GreenfootImage("crab.png");
    public GreenfootImage koCrab = new GreenfootImage("crab2.png");
    public Text textField;
    public int points;
        
    public Crab(int oid) {
        this.oid = oid;
    }
    
    public void act() {
        if (canSee(Worm.class)) {
            Actor actor = getOneObjectAtOffset(0, 0, Worm.class);
            if(actor instanceof Worm) {
                Worm worm = (Worm) actor;
                int oid = worm.oid;
                this.send(-1, "SET~Crab~"+this.oid+"~points~"+this.points);
                this.send(-1, "REMOVE~Worm~"+oid);
                Server server = getWorld().getObjects(Server.class).get(0);
                getWorld().removeObject(server.worms.get(oid));
                int newoid = server.addSprite("Worm");
                server.worms.get(newoid).sendAllProperties();
            }
        }
    }
    
    public void setProperty(String key, String value) {
        if(key.equals("xy")) {
            //System.out.println("pos");
            String[] xy = value.split(";");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            this.setLocation(x, y);
            this.textField.text(this.lives + " Lives");
            this.textField.hoverPosition(getX(), getY());
            this.send(-1, "SET~Crab~"+this.oid+"~xy~"+x+";"+y);
        } else if(key.equals("rot")) {
            //System.out.println("rot");
            int rotation = Integer.parseInt(value);
            this.setRotation(rotation);
            this.send(-1, "SET~Crab~"+this.oid+"~rot~"+rotation);
        } else if(key.equals("img")) {
            if(value.equals("ok")) {
                setImage(this.okCrab);
                this.send(-1, "SET~Crab~"+this.oid+"~img~ok");
            }
            if(value.equals("ko")) {
                setImage(this.koCrab);
                this.send(-1, "SET~Crab~"+this.oid+"~img~ko");
            }
            
        } else if(key.equals("lives")) {
            this.lives = Integer.parseInt(value);
            this.send(-1, "SET~Crab~"+this.oid+"~lives~"+this.lives);
            if(this.lives == 0) {
                getWorld().getObjects(Server.class).get(0).removeClient(this.cid);
            }
        } else {
            System.out.println(this + ": failed to parse key " + key);
        }
    }
    
    public void send(int cid, String data) {
        getWorld().getObjects(Server.class).get(0).send(cid, data);
    }
    
    public void sendAllProperties() {
        try {
        this.send(-1, "ADD~Crab~"+this.oid);
        this.send(-1, "SET~Crab~"+this.oid+"~xy~"+getX()+";"+getY());
        this.send(-1, "SET~Crab~"+this.oid+"~rot~"+getRotation());
        this.send(-1, "SET~Crab~"+this.oid+"~lives~"+this.lives);
    } catch (Exception e) {}
    }
    
    public void gettingAHit(String pType, int pOid) {
        this.lives--;
        this.send(-1, "SET~Crab~"+this.oid+"~lives~"+this.lives); 
    }
    
    public void initText() {
        this.textField = new Text();
        getWorld().addObject(this.textField, 0, 0);
    }
}
