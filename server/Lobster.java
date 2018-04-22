import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Lobster extends Animal
{
    public int oid;
    public int lastX;
    public int lastY;
    public int lastRot;
    public long lastHit = System.currentTimeMillis() + 2000;
    public GreenfootImage okLobster = new GreenfootImage("lobster.png");
    public GreenfootImage koLobster = new GreenfootImage("lobster2.png");
    public String image = "ok";
    public String lastimage = "ok";
    
    public void act() 
    {
        if(!this.lastimage.equals(this.image)){
            this.send(-1, "SET~Lobster~"+this.oid+"~image~"+this.image);
            this.lastimage = image;
        }
        if(this.lastHitDifference() > 2 || this.image.equals("ko")) {
            this.image = "ok";
        }
        if(canSee(Crab.class)) {
            Actor actor = getOneObjectAtOffset(0, 0, Crab.class);
            if(actor instanceof Crab) {
                Crab crab = (Crab) actor;
                if(!crab.rushing) {
                    this.send(-1, "COLLIDE~Crab~"+crab.oid+"~Lobster~"+this.oid);
                }
            }
        }
        if (atWorldEdge()) {
            turn(Greenfoot.getRandomNumber(60)-30);
        }
        if ( Greenfoot.getRandomNumber(100) < 5 ) 
        {
            turn(Greenfoot.getRandomNumber(60)-30 );
        }
        //if ( Greenfoot.getRandomNumber(100) < 80 )
            move(3);    
        if(this.getX() != this.lastX) {
            this.send(-1, "SET~Lobster~"+this.oid+"~xy~"+getX()+";"+getY());
            this.lastX = this.getX();
            this.lastY = this.getY();
        }
        if(this.getY() != this.lastY) {
            this.send(-1, "SET~Lobster~"+this.oid+"~xy~"+getX()+";"+getY());
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
            String[] xy = value.split(";");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            this.setLocation(x, y);
            this.send(-1, "SET~Lobster~"+this.oid+"~xy~"+x+";"+y);
        }else if(key.equals("rot")) {
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
        this.send(-1, "ADD~Lobster~"+this.oid);
        this.send(-1, "SET~Lobster~"+this.oid+"~xy~"+getX()+";"+getY());
        this.send(-1, "SET~Lobster~"+this.oid+"~rot~"+getRotation());
    }
    
    public void collide() {
        this.lastHit = System.currentTimeMillis();
        this.image = "ko";
        this.setImage(this.koLobster);
    }
    
    public long lastHitDifference() {
        long lastHitInSeconds = this.lastHit / 1000;
        long timeInSeconds = System.currentTimeMillis() / 1000;
        return(timeInSeconds - lastHitInSeconds);
    }
}
