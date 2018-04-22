import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Keyboard extends Animal
{
    public String text = "";
    public int actCounter = 0;
    public Keyboard() {
        setImage(new GreenfootImage("                    " + this.text + "                    ", 150, Color.WHITE, Color.RED));
    }
    public void act()
    {
        if(this.actCounter % 7 == 0) {
            if(Greenfoot.isKeyDown("1")) {
                this.text += "1";
            } else if(Greenfoot.isKeyDown("2")) {
                this.text += "2";
            }else if(Greenfoot.isKeyDown("3")) {
                this.text += "3";
            }else if(Greenfoot.isKeyDown("4")) {
                this.text += "4";
            }else if(Greenfoot.isKeyDown("5")) {
                this.text += "5";
            }else if(Greenfoot.isKeyDown("6")) {
                this.text += "6";
            }else if(Greenfoot.isKeyDown("7")) {
                this.text += "7";
            }else if(Greenfoot.isKeyDown("8")) {
                this.text += "8";
            }else if(Greenfoot.isKeyDown("9")) {
                this.text += "9";
            }else if(Greenfoot.isKeyDown("0")) {
                this.text += "0";
            }else if(Greenfoot.isKeyDown("a")) {
                this.text += "a";
            }else if(Greenfoot.isKeyDown("b")) {
                this.text += "b";
            }else if(Greenfoot.isKeyDown("c")) {
                this.text += "c";
            }else if(Greenfoot.isKeyDown("d")) {
                this.text += "d";
            }else if(Greenfoot.isKeyDown("e")) {
                this.text += "e";
            }else if(Greenfoot.isKeyDown("f")) {
                this.text += "f";
            }else if(Greenfoot.isKeyDown("g")) {
                this.text += "g";
            }else if(Greenfoot.isKeyDown("h")) {
                this.text += "h";
            }else if(Greenfoot.isKeyDown("i")) {
                this.text += "i";
            }else if(Greenfoot.isKeyDown("j")) {
                this.text += "j";
            }else if(Greenfoot.isKeyDown("k")) {
                this.text += "k";
            }else if(Greenfoot.isKeyDown("l")) {
                this.text += "l";
            }else if(Greenfoot.isKeyDown("m")) {
                this.text += "m";
            }else if(Greenfoot.isKeyDown("n")) {
                this.text += "n";
            }else if(Greenfoot.isKeyDown("o")) {
                this.text += "o";
            }else if(Greenfoot.isKeyDown("p")) {
                this.text += "p";
            }else if(Greenfoot.isKeyDown("q")) {
                this.text += "q";
            }else if(Greenfoot.isKeyDown("s")) {
                this.text += "s";
            }else if(Greenfoot.isKeyDown("t")) {
                this.text += "t";
            }else if(Greenfoot.isKeyDown("u")) {
                this.text += "u";
            }else if(Greenfoot.isKeyDown("v")) {
                this.text += "v";
            }else if(Greenfoot.isKeyDown("w")) {
                this.text += "w";
            }else if(Greenfoot.isKeyDown("x")) {
                this.text += "x";
            }else if(Greenfoot.isKeyDown("y")) {
                this.text += "y";
            }else if(Greenfoot.isKeyDown("z")) {
                this.text += "z";
            }else if(Greenfoot.isKeyDown(".")) {
                this.text += ".";
            }else if(Greenfoot.isKeyDown("enter")) {
                Client client = new Client();
                if(this.text.equals("")) {
                    this.text = "localhost";
                }
                if(client.connect(this.text)){
                    getWorld().removeObject(getWorld().getObjects(DynamicText.class).get(0));
                    getWorld().addObject(client, 0, 0);
                    getWorld().removeObject(this);
                } else {
                    System.exit(1);
                }
            }else if(Greenfoot.isKeyDown("backspace")) {
                this.text = this.text.substring(0, this.text.length() - 1);
            }
            setImage(new GreenfootImage("                    " + this.text + "                    ", 150, Color.WHITE, Color.RED));
        }
        this.actCounter++;
    }
}

