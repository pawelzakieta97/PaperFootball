package com.company;

import java.awt.*;

public class Ball extends GameObject{
    Point pos = new Point(0,0);
    public Ball(){}
    public void render(Graphics g){
        g.fillOval(pos.getX(),pos.getY(),4,4);
    }
    public void tick (){}
}
