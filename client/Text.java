import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
public class Text extends Animal
{
    public Crab crab;
    public Color fontcolor;
    public Text(Crab crab) {
        this.crab = crab;
        if(this.crab.color.equals("yellow")) {
            this.fontcolor = Color.BLACK;
        } else {
            this.fontcolor = Color.WHITE;
        }
        setImage(new GreenfootImage("", 15, this.fontcolor, this.getColor(this.crab.color)));
    }
    
    public void text(String text) {
        System.out.println(this.crab.color);
        setImage(new GreenfootImage(text, 15, this.fontcolor, this.getColor(this.crab.color)));
    }
    
    public void hoverPosition(int x, int y) {
        setLocation(x, y - 40);
    }
    
    public Color getColor(String pColor) {
        if (pColor.equals("black")) {return Color.BLACK;}
        else if (pColor.equals("blue")) {return Color.BLUE;}
        else if (pColor.equals("red")) {return Color.RED;}
        else if (pColor.equals("green")) {return Color.GREEN;}
        else if (pColor.equals("yellow")) {return Color.YELLOW;}
        else {return Color.BLACK;}
    }
}
