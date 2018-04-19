import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Worm extends Animal
{
    public int oid;
        
    public Worm(int oid) {
        this.oid = oid;
    }
    
    public void setProperty(String key, String value) {
        if(key.equals("xy")) {
            //System.out.println("pos");
            String[] xy = value.split(";");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            this.setLocation(x, y);
            this.send(-1, "SET~Crab~"+this.oid+"~xy~"+x+";"+y);
        }else if(key.equals("rot")) {
            //System.out.println("rot");
            int rotation = Integer.parseInt(value);
            this.setRotation(rotation);
            this.send(-1, "SET~Crab~"+this.oid+"~rot~"+rotation);
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
}
