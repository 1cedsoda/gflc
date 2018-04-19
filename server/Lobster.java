import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Lobster extends Animal
{
    public int oid;
    public int lastX;
    public int lastY;
    public int lastRot;
    
    public void act() 
    {
        //if (canSee(Crab.class)) {
            //Actor actor = getOneObjectAtOffset(0, 0, Crab.class);
            //If 
            //this.send(-1, "COLLIDE~Lobster~"+this.oid+"~Crab~"+crab.oid);
        //}
        if (atWorldEdge()) {
            turn(180);
        }

        if ( Greenfoot.getRandomNumber(100) < 10 ) 
        {
            turn( Greenfoot.getRandomNumber(60)-30 );
        }
        if ( Greenfoot.getRandomNumber(100) < 80 )
            move(2);
            
        if(this.getX() != this.lastX) {
            this.send(-1, "SET~Crab~"+this.oid+"~xy~"+getX()+";"+getY());
            this.lastX = this.getX();
            this.lastY = this.getY();
        }
        if(this.getY() != this.lastY) {
            this.send(-1, "SET~Crab~"+this.oid+"~xy~"+getX()+";"+getY());
            this.lastX = this.getX();
            this.lastY = this.getY();
        }
        if(this.getRotation() != this.lastRot) {
            this.send(-1, "SET~Lobster~"+this.oid+"~rot~"+getRotation());
            this.lastRot = this.getRotation();
        }
    }
    
    public Lobster(int oid) {
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
            this.send(-1, "SET~Lobster~"+this.oid+"~xy~"+x+";"+y);
        }else if(key.equals("rot")) {
            //System.out.println("rot");
            int rotation = Integer.parseInt(value);
            this.setRotation(rotation);
            this.send(-1, "SET~Lobster~"+this.oid+"~rot~"+rotation);
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
