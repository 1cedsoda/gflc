import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Crab extends Animal
{
    public boolean player = false;
    public String username = "";
    public int oid;
    public int lives = 5;
    public int lastX;
    public int lastY;
    public int lastRot;
    public int lastLives = 6;
    public int points;
    public String lastImage = "ok";
    public long lastHit = System.currentTimeMillis() - 3000; //Kein Schaden in den ersten 5 Sekunden
    public GreenfootImage okCrab = new GreenfootImage("crab.png");
    public GreenfootImage koCrab = new GreenfootImage("crab2.png");
    public Text textField;
    public boolean textFieldExcists = false;
    
    public Crab(int oid) {
        this.oid = oid;
    }
    
    public void act()
    {
        if(this.player) {
            if(this.lastHitDifference() > 2) {
                setImage(this.okCrab);
                if(!this.lastImage.equals("ok")) {
                    this.send("SET~Crab~"+this.oid+"~img~ok");
                    this.lastImage = "ok";
                }
                this.checkKeypress();
            }
            if(this.lives != this.lastLives) {
                this.send("SET~Crab~"+this.oid+"~lives~"+this.lives);
                this.lastLives = this.lives;
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
        }
        this.textField.text(this.lives + " Lives");
        this.textField.hoverPosition(getX(), getY());
    }
        
    public void setProperty(String key, String value) {
        if(key.equals("xy")) {
            //System.out.println("pos");
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
        }else if(key.equals("lives")) {
            if(!this.player) {
                int lives = Integer.parseInt(value);
                this.lives = lives;
            }
        } else if(key.equals("points")) {
            int points = Integer.parseInt(value);
            this.points = points;
        } else if(key.equals("player")) {
            if(value.equals("true")) {
                this.player = true;
            } else if(value.equals("false")) {
                this.player = false;
            }
        } else {
            System.out.println(this + ": failed to parse key " + key);
        }
        
    }
    
    public void send(String data) {
        getWorld().getObjects(Client.class).get(0).send(data);
    }
    
    public void checkKeypress()
    {
        if(Greenfoot.isKeyDown("a")) {
            turn(-6);
        }
        if(Greenfoot.isKeyDown("d"))        {
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
    }
    
    public void collide(String enemyType, int enemyOid) {
        if(this.lastHitDifference() > 2) {
            setImage(this.koCrab);
            if(!this.lastImage.equals("ko")) {
                this.send("SET~Crab~"+this.oid+"~img~ko");
                this.lastImage = "ko";
                this.lives--;
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
        this.textField = new Text();
        getWorld().addObject(this.textField, 0, 0);
    }
}
