import greenfoot.*;  // (Actor, World, Greenfoot, GreenfootImage)

public class CrabWorld extends World
{
    public CrabWorld() 
    {
        super(1200, 600, 1);
        prepare();
    }

    public void prepare(){
        Client client = new Client();
        addObject(client,113,93);
        client.connect();
    }
}
