package com.company;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

public class MouseEventHandler extends Observable implements MouseListener{

    public void mouseClicked(MouseEvent event){
    }

    private MouseEvent lastEvent;

    public void setLastEvent(MouseEvent lastEvent) {
        this.lastEvent = lastEvent;
        setChanged();
        notifyObservers();
    }

    public void mousePressed(MouseEvent event){
    }

    public void mouseReleased(MouseEvent event){
        if (event.getButton()==MouseEvent.BUTTON1){
            System.out.println("button 1");
            Point pos = new Point(event.getX(),event.getY());
            //model.makePlayerMove(pos);

        }
        if (event.getButton()==MouseEvent.BUTTON3){
            System.out.println("button 3");
            //model.getPitch().currentGameState.eraseMove();
            //model.eraseMove();
        }


    }

    public void mouseEntered(MouseEvent event){}

    public void mouseExited(MouseEvent event){

    }

}
