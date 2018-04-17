import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Sprite extends Actor
{
    public int oid;
    public Sprite(int oid) {
        this.oid = oid;
    }
    public void send(String data) {
        World world = getWorld();
        Client client = null;
        client = world.getObjects(Client.class).get(0);
        if(client != null) {
            client.send(data);
        }
    }
}
