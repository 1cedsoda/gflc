import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Worm extends Animal
{
    public int oid;
    public String effect;
    
    public Worm(int oid) {
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
        } else if(key.equals("effect")) {
                this.setEffect(value);
        } else {
            System.out.println(this + ": failed to parse key " + key);
        }
    }
    
    public void send(String data) {
        getWorld().getObjects(Client.class).get(0).send(data);
    }
    
    public void setEffect(String effect) {
        if(effect.equals("normal")) {
            this.effect = "normal";
            this.setImage(new GreenfootImage("worm.png"));
        } else if(effect.equals("bomb")) {
            this.effect = "bomb";
            this.setImage(new GreenfootImage("worm-bomb.png"));
        } else if(effect.equals("energy")) {
            this.effect = "energy";
            this.setImage(new GreenfootImage("worm-energy.png"));
        } else if(effect.equals("blood")) {
            this.effect = "blood";
            this.setImage(new GreenfootImage("worm-blood.png"));
        } else if(effect.equals("plus")) {
            this.effect = "plus";
            this.setImage(new GreenfootImage("worm-plus.png"));
        } else {
            System.out.println(this + ": unknown effect");
        }
    }
}
  
