import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Bomb extends Animal
{
    public int oid;
    public int playeroid;
    
    public void act() 
    {
    }
    
    public Bomb(int oid) {
        this.oid = oid;
    }
    
    public void setProperty(String key, String value) {
        if(key.equals("player")) {
            this.playeroid = Integer.parseInt(value);
        }else if(key.equals("xy")) {
            String[] xy = value.split(";");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            this.setLocation(x, y);
        } else {
            System.out.println(this + ": failed to parse key " + key);
        }
    }
    
    public void send(int cid, String data) {
        getWorld().getObjects(Client.class).get(0).send(data);
    }
}
