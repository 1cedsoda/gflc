import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Crab extends Sprite
{
    public int oid;
    public Crab(int oid) {
        this.oid = oid;
        this.send(-1, "ADD~Crab~"+this.oid);
    }
    
    public void setProperty(String key, String value) {
        if(key == "xy") {
            String[] xy = value.split(";");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            this.setLocation(x, y);
        }
        if(key == "rotation") {
            int rotation = Integer.parseInt(value);
            this.setRotation(rotation);
        }
    }
}
