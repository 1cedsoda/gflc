import java.util.*;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Crab extends Animal
{
    public int oid;
    public int cid;
    public int lives = 3;
    public GreenfootImage okCrab = new GreenfootImage("crab.png");
    public GreenfootImage koCrab = new GreenfootImage("crab2.png");
        
    public Crab(int oid) {
        this.oid = oid;
        //this.send(-1, "ADD~Crab~"+this.oid);
    }
    
    public void setProperty(String key, String value) {
        if(key.equals("xy")) {
            //System.out.println("pos");
            String[] xy = value.split(";");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            this.setLocation(x, y);
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
            
        } else {
            System.out.println(this + ": failed to parse key " + key);
        }
    }
    
    public void send(int cid, String data) {
        getWorld().getObjects(Server.class).get(0).send(cid, data);
    }
    
    public void sendAllProperties() {
        this.send(-1, "ADD~Crab~"+this.oid);
        this.send(-1, "SET~Crab~"+this.oid+"~xy~"+getX()+";"+getY());
        this.send(-1, "SET~Crab~"+this.oid+"~rot~"+getRotation());
    }
    
    public void gettingAHit(String pType, int pOid) {
        this.lives--;
        this.send(-1, "SET~Crab~"+this.oid+"~lives~"+this.lives); 
    }
}
