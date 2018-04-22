import greenfoot.*;  // (Actor, World, Greenfoot, GreenfootImage)

public class CrabWorld extends World
{
    public CrabWorld() 
    {
        super(1200, 600, 1);
        prepare();
    }

    public void prepare(){
        Keyboard kb = new Keyboard();
        addObject(kb, 600, 300);
        DynamicText dt = new DynamicText("                                          press RUN & connect to a server                                          ");
        addObject(dt, 600, 220);
    }
}
