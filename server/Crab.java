import java.util.*;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Crab extends Animal
{
    public int oid;
    public int cid;
    public int lifes = 5;
    public GreenfootImage okCrab = new GreenfootImage("crab.png");
    public GreenfootImage koCrab = new GreenfootImage("crab2.png");
    public Text textField;
    public int points;
    public String color;
    public int rush = 0;
    public boolean rushing = false;
        
    public Crab(int oid) {
        this.oid = oid;
    }
    
    public void act() {
        if (canSee(Worm.class)) {
            Actor actor = getOneObjectAtOffset(0, 0, Worm.class);
            if(actor instanceof Worm) {
                Worm worm = (Worm) actor;
                String effect = worm.effect;
                int oid = worm.oid;
                if(effect.equals("plus")) {
                    this.points += 3;
                } else {
                    this.points++;
                }
                this.send(-1, "SET~Crab~"+this.oid+"~points~"+this.points);
                this.send(-1, "REMOVE~Worm~"+oid);
                Server server = getWorld().getObjects(Server.class).get(0);
                getWorld().removeObject(server.worms.get(oid));
                server.worms.remove(oid);
                int newoid = server.addSprite("Worm");
                server.worms.get(newoid).sendAllProperties();
                if(effect.equals("blood")) {
                    this.lifes++;
                    this.send(-1, "SET~Crab~"+this.oid+"~addlife~1");
                } else if(effect.equals("energy")) {
                    this.rush++;
                    this.send(-1, "SET~Crab~"+this.oid+"~addrush~1");
                }  else if(effect.equals("bomb")) {
                    int newbomboid = server.addSprite("Bomb");
                    server.bombs.get(newbomboid).playeroid = this.oid;
                    server.bombs.get(newbomboid).setLocation(getX(), getY());
                    server.bombs.get(newbomboid).sendAllProperties();
                }
            }
        }
    }
    
    public void setProperty(String key, String value) {
        if(key.equals("xy")) {
            //System.out.println("pos");
            String[] xy = value.split(";");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            this.setLocation(x, y);
            this.textField.text(this.points +" Points " + this.lifes + " Lifes");
            this.textField.hoverPosition(getX(), getY());
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
        } else if(key.equals("lifes")) {
            this.lifes = Integer.parseInt(value);
            this.send(-1, "SET~Crab~"+this.oid+"~lifes~"+this.lifes);
            if(this.lifes == 0) {
                getWorld().getObjects(Server.class).get(0).removeClient(this.cid);
            }
        } else if(key.equals("rush")) {
            this.rush = Integer.parseInt(value);
            this.send(-1, "SET~Crab~"+this.oid+"~rush~"+this.rush);
        } else if(key.equals("rushing")) {
            this.rushing = Boolean.parseBoolean(value);
            this.send(-1, "SET~Crab~"+this.oid+"~rushing~"+this.rushing);
        } else if(key.equals("color")) {
            this.color = value;
        } else {
            System.out.println(this + ": failed to parse key " + key);
        }
    }
    
    public void send(int cid, String data) {
        try {
        getWorld().getObjects(Server.class).get(0).send(cid, data);
    } catch (Exception e) {
        //Fehlermeldung. Keine Zeit zum Fixen
    }
    }
    
    public void sendAllProperties() {
        try {
        this.send(-1, "ADD~Crab~"+this.oid);
        this.send(-1, "SET~Crab~"+this.oid+"~xy~"+getX()+";"+getY());
        this.send(-1, "SET~Crab~"+this.oid+"~rot~"+getRotation());
        this.send(-1, "SET~Crab~"+this.oid+"~lifes~"+this.lifes);
        this.send(-1, "SET~Crab~"+this.oid+"~points~"+this.points);
        this.send(-1, "SET~Crab~"+this.oid+"~color~"+this.color);
        this.send(-1, "SET~Crab~"+this.oid+"~rush~"+this.rush);
        } catch (Exception e) {}
    }
    
    public void gettingAHit(String pType, int pOid) {
        this.lifes--;
        this.send(-1, "SET~Crab~"+this.oid+"~lifes~"+this.lifes); 
    }
    
    public void initText(String color) {
        this.color = color;
        this.textField = new Text(this);
        getWorld().addObject(this.textField, 0, 0);
    }
    
}
