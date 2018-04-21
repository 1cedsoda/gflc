import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Worm extends Animal
{
    public int oid;
    public int lastX;
    public int lastY;
    
    public void act() 
    {
        if ( Greenfoot.getRandomNumber(100) < 3 ) 
        {
            int x;
            int y;
            if ( Greenfoot.getRandomNumber(2) < 1 ) {
                x = getX() + 2;
            } else {
                x = getX() - 2;
            }
            if ( Greenfoot.getRandomNumber(2) < 1 ) {
                y = getY() + 2;
            } else {
                y = getY() - 2;
            }
            setLocation(x, y);
        }   
        if(this.getX() != this.lastX) {
            this.send(-1, "SET~Worm~"+this.oid+"~xy~"+getX()+";"+getY());
            this.lastX = this.getX();
            this.lastY = this.getY();
        }
        if(this.getY() != this.lastY) {
            this.send(-1, "SET~Worm~"+this.oid+"~xy~"+getX()+";"+getY());
            this.lastX = this.getX();
            this.lastY = this.getY();
        }
    }
    
    public Worm(int oid) {
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
            this.send(-1, "SET~Worm~"+this.oid+"~xy~"+x+";"+y);
        } else {
            System.out.println(this + ": failed to parse key " + key);
        }
    }
    
    public void send(int cid, String data) {
        getWorld().getObjects(Server.class).get(0).send(cid, data);
    }
    
    public void sendAllProperties() {
        this.send(-1, "ADD~Worm~"+this.oid);
        this.send(-1, "SET~Worm~"+this.oid+"~xy~"+getX()+";"+getY());
    }
}

