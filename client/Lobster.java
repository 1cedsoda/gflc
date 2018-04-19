import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Lobster extends Animal
{
    public boolean player = false;
    public String username = "";
    public int oid;
    public int lives = 3;
    public int lastX;
    public int lastY;
    public int lastRot;
    
    public Lobster(int oid) {
        this.oid = oid;
    }
    
    public void act() 
    {
        if(this.getX() != this.lastX) {
            this.send("SET~Crab~"+this.oid+"~xy~"+getX()+";"+getY());
            this.lastX = this.getX();
            this.lastY = this.getY();
        }
        if(this.getY() != this.lastY) {
            this.send("SET~Crab~"+this.oid+"~xy~"+getX()+";"+getY());
            this.lastX = this.getX();
            this.lastY = this.getY();
        }
        if(this.getRotation() != this.lastRot) {
            this.send("SET~Lobster~"+this.oid+"~rot~"+getRotation());
            this.lastRot = this.getRotation();
        }
    }
        
    public void setProperty(String key, String value) {
        if(key.equals("xy")) {
            if(!this.player) {
                String[] xy = value.split(";");
                int x = Integer.parseInt(xy[0]);
                int y = Integer.parseInt(xy[1]);
                this.setLocation(x, y);
            }
        }else if(key.equals("rotation")) {
            if(!this.player) {
                int rotation = Integer.parseInt(value);
                this.setRotation(rotation);
            }
        } else {
            System.out.println(this + ": failed to parse key " + key);
        }
    }
    
    public void send(String data) {
        getWorld().getObjects(Client.class).get(0).send(data);
    }
}
  
