import greenfoot.*;  // (Actor, World, Greenfoot, GreenfootImage)

public class CrabWorld extends World
{
    public CrabWorld() 
    {
        super(1200, 600, 1);
        prepare();
    }

    public void prepare(){
        
        Crab crab = new Crab();
        addObject(crab,564,140);
        Crab crab2 = new Crab();
        addObject(crab2,263,320);
        Crab crab3 = new Crab();
        addObject(crab3,636,269);
        Server server = new Server(1223);
        addObject(server,120,129);
    }
}
