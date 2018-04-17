import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Crab extends Sprite
{
    public boolean player = false;
    public String username = "";
    
    public Crab(int oid) {
        super(oid);
    }
    
    public void act() 
    {
        checkKeypress();
    }
        
    public void setProperty(String key, String value) {
        if(key == "xy") {
            if(!this.player) {
                String[] xy = value.split(";");
                int x = Integer.parseInt(xy[0]);
                int y = Integer.parseInt(xy[1]);
                this.setLocation(x, y);
            }
        }
        if(key == "rotation") {
            if(!this.player) {
                int rotation = Integer.parseInt(value);
                this.setRotation(rotation);
            }
        }
    }
    
    public void checkKeypress()
    {
        if(this.player) {
            if(Greenfoot.isKeyDown("a")) {
                turn(-6);
            }
            if(Greenfoot.isKeyDown("d"))        {
                turn(6);
            }
            if ( Greenfoot.isKeyDown("w") ){   
                turn(-90);
                move(1);
                turn(90);
            }
            if ( Greenfoot.isKeyDown("s") )
            {
                turn(90);
                move(1);
                turn(-90);
            }
        }
    }
}
