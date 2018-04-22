import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Crab extends Animal
{
    public boolean player = false;
    public String username = "";
    public int oid;
    public int lifes = 5;
    public int lastX;
    public int lastY;
    public int lastRot;
    public int lastLifes = 6;
    public int points;
    public String lastImage = "ok";
    public long lastHit = System.currentTimeMillis() - 3000; //Kein Schaden in den ersten 5 Sekunden
    public GreenfootImage okCrab = new GreenfootImage("crab.png");
    public GreenfootImage koCrab = new GreenfootImage("crab2.png");
    public Text textField;
    public boolean textFieldExcists = false;
    public String color = "black";
    public int rush = 0;
    public int lastRush = 0;
    public boolean rushing = false;
    
    public Crab(int oid) {
        this.oid = oid;
    }
    
    public void act()
    {
        if (canSee(Crab.class) && this.rushing) {
            Actor actor = getOneObjectAtOffset(0, 0, Crab.class);
            if(actor instanceof Crab) {
                Crab crab = (Crab) actor;
                if(crab.oid != this.oid) {
                    this.send("COLLIDE~Crab~"+crab.oid+"~Crab~"+this.oid);
                }
            }
        }
        if (canSee(Lobster.class) && this.rushing) {
            Actor actor = getOneObjectAtOffset(0, 0, Lobster.class);
            if(actor instanceof Lobster) {
                Lobster lobster = (Lobster) actor;
                if(lobster.oid != this.oid) {
                    this.send("COLLIDE~Lobster~"+lobster.oid+"~Crab~"+this.oid);
                }
            }
        }
        if(this.player) {
            if(this.lastHitDifference() > 2) {
                setImage(this.okCrab);
                if(!this.lastImage.equals("ok")) {
                    this.send("SET~Crab~"+this.oid+"~img~ok");
                    this.lastImage = "ok";
                }
                this.checkKeypress();
            }
            if(this.lifes != this.lastLifes) {
                this.send("SET~Crab~"+this.oid+"~lifes~"+this.lifes);
                this.lastLifes = this.lifes;
            }
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
                this.send("SET~Crab~"+this.oid+"~rot~"+getRotation());
                this.lastRot = this.getRotation();
            }
            if(this.rush != this.lastRush) {
                this.send("SET~Crab~"+this.oid+"~rush~"+this.rush);
                this.lastRush = this.rush;
            }
        }
        this.textField.text(this.points + " Points " + this.lifes + " Lifes");
        this.textField.hoverPosition(getX(), getY());
    }
        
    public void setProperty(String key, String value) {
        if(key.equals("xy")) {
            if(!this.player) {
                String[] xy = value.split(";");
                int x = Integer.parseInt(xy[0]);
                int y = Integer.parseInt(xy[1]);
                this.setLocation(x, y);
            }
        }else if(key.equals("rot")) {
            if(!this.player) {
                int rotation = Integer.parseInt(value);
                this.setRotation(rotation);
            }
        }else if(key.equals("img")) {
            if(!this.player) {
                if(value.equals("ok")) {
                    setImage(this.okCrab);
                }
                if(value.equals("ko")) {
                    setImage(this.koCrab);
                }
            }
        }else if(key.equals("lifes")) {
            if(!this.player) {
                int lifes = Integer.parseInt(value);
                this.lifes = lifes;
            }
        } else if(key.equals("points")) {
            int points = Integer.parseInt(value);
            this.points = points;
        } else if(key.equals("addlife")) {
            this.lifes += Integer.parseInt(value);
        } else if(key.equals("addrush")) {
            this.rush += Integer.parseInt(value);
        } else if(key.equals("rush")) {
            this.rush = Integer.parseInt(value);
        } else if(key.equals("hit")) {
            this.collide();
        } else if(key.equals("player")) {
            if(value.equals("true")) {
                this.player = true;
            } else if(value.equals("false")) {
                this.player = false;
            }
        } else if(key.equals("color")) {
            this.color = value;
        } else {
            System.out.println(this + ": failed to parse key " + key);
        }
        
    }
    
    public void send(String data) {
        getWorld().getObjects(Client.class).get(0).send(data);
    }
    
    public void checkKeypress()
    {
        if(Greenfoot.isKeyDown("space")) {
            if(this.rush > 0 && !this.rushing) {
                this.rush--;
                this.rushing = true;
                this.send("SET~Crab~"+this.oid+"~rushing~true");
            }
        }
        if(!this.rushing) {
            if(Greenfoot.isKeyDown("a")) {
                turn(-6);
            }
            if(Greenfoot.isKeyDown("d")) {
                turn(6);
            }
            if ( Greenfoot.isKeyDown("w") ){   
                turn(-90);
                move(3);
                turn(90);
            }
            if ( Greenfoot.isKeyDown("s") )
            {
                turn(90);
                move(3);
                turn(-90);
            }
        } else {
            if(!atWorldEdge()) {
                turn(-90);
                move(15);
                turn(90);
            } else {
                rushing = false;
                this.send("SET~Crab~"+this.oid+"~rushing~false");
            }
        }
    }
    
    public void collide() {
        if(this.lastHitDifference() > 2) {
            setImage(this.koCrab);
            if(!this.lastImage.equals("ko")) {
                this.send("SET~Crab~"+this.oid+"~img~ko");
                this.lastImage = "ko";
                this.lifes--;
            }
            this.lastHit = System.currentTimeMillis();
        }
    }
    
    public long lastHitDifference() {
        long lastHitInSeconds = this.lastHit / 1000;
        long timeInSeconds = System.currentTimeMillis() / 1000;
        return(timeInSeconds - lastHitInSeconds);
    }
    
    public void initText() {
        this.textField = new Text(this);
        getWorld().addObject(this.textField, 0, 0);
    }
}
