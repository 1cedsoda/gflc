import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Lobster extends Animal
{
    public int oid;
    public GreenfootImage okLobster = new GreenfootImage("lobster.png");
    public GreenfootImage koLobster = new GreenfootImage("lobster2.png");
    public Lobster(int oid) {
        this.oid = oid;
    }
    
    public void act() 
    {
    }
        
    public void setProperty(String key, String value) {
        if(key.equals("xy")) {
                String[] xy = value.split(";");
                int x = Integer.parseInt(xy[0]);
                int y = Integer.parseInt(xy[1]);
                this.setLocation(x, y);
        } else if(key.equals("rot")) {
                int rotation = Integer.parseInt(value);
                this.setRotation(rotation);
        } else if(key.equals("image")) {
                if(value.equals("ok")) {
                    setImage(this.okLobster);
                } else  if(value.equals("ko")) {
                    setImage(this.koLobster);
                }
        } else {
            System.out.println(this + ": failed to parse key " + key);
        }
    }
    
    public void send(String data) {
        getWorld().getObjects(Client.class).get(0).send(data);
    }
}
  
