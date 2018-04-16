import greenfoot.*;  // (Actor, World, Greenfoot, GreenfootImage)

public class CrabWorld extends World
{
    public CrabWorld() 
    {
        super(1200, 600, 1);
        prepare();
    }
    public void prepare(){
        Server server = new Server(1223);
        addObject(server,120,129);
    }
}
