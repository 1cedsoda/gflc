import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
public class Text extends Animal
{
    public Text() {
        setImage(new GreenfootImage("", 15, Color.WHITE, Color.BLACK));
    }
    
    public void text(String text) {
        setImage(new GreenfootImage(text, 15, Color.WHITE, Color.BLACK));
    }
    
    public void hoverPosition(int x, int y) {
        setLocation(x, y - 40);
    }  
}
