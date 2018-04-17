import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Sprite extends Actor
{
    public void send(int cid, String data) {
        World world = getWorld();
        Server server = null;
        System.out.println(world.getObjects(Server.class));
        server.send(cid, data);
    }  
}
