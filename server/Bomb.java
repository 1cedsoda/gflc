import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Bomb extends Animal
{
    public int oid;
    public int playeroid;
    public long borntime = System.currentTimeMillis();
    
    public void act() 
    {
        if(this.lifetime() > 30) {
            this.explode();
        } else {
            try {
                if(canSee(Crab.class)) {
                    Actor actor = getOneObjectAtOffset(0, 0, Crab.class);
                    if(actor instanceof Crab) {
                        Crab crab = (Crab) actor;
                        if(playeroid != crab.oid) {
                            this.send(-1, "COLLIDE~Crab~"+crab.oid+"~Bomb~"+this.oid);
                            this.explode();
                        }
                    }
                }
            } catch (Exception e) {
                //komische Fehler
            }
        }
    }
    
    public Bomb(int oid) {
        this.oid = oid;
    }
    
    public void setProperty(String key, String value) {
    }
    
    public void send(int cid, String data) {
        getWorld().getObjects(Server.class).get(0).send(cid, data);
    }
    
    public void sendAllProperties() {
        this.send(-1, "ADD~Bomb~"+this.oid);
        this.send(-1, "SET~Bomb~"+this.oid+"~xy~"+getX()+";"+getY());
        this.send(-1, "SET~Bomb~"+this.oid+"~player~"+this.oid);
    }
    
    public long lifetime() {
        long bornInSeconds = this.borntime / 1000;
        long nowInSeconds = System.currentTimeMillis() / 1000;
        return(nowInSeconds - bornInSeconds);
    }
    
    public void explode() {
        this.send(-1, "REMOVE~Bomb~"+this.oid);
        Server server = getWorld().getObjects(Server.class).get(0);
        server.bombs.remove(this.oid);
        getWorld().removeObject(this);
    }
}
