package com.company.Controller;

import com.company.Model.Point;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MouseEventHandler implements MouseListener{

    private Controller controller;
    private ExecutorService executor = Executors.newCachedThreadPool();
    void assignController(Controller controller){
        this.controller = controller;
    }

    public void mouseClicked(MouseEvent event){
    }


    public void mousePressed(MouseEvent event){
    }

    public void mouseReleased(MouseEvent event){
        if (event.getButton()==MouseEvent.BUTTON1){
            System.out.println("button 1");
            Point pos = new Point(event.getX(),event.getY());
            executor.execute(new Runnable(){
                public void run(){
                    controller.getModel().makePlayerMove(pos);
                }
            });

        }
        if (event.getButton()==MouseEvent.BUTTON3){
            System.out.println("button 3");
            controller.getModel().eraseMove();
        }


    }

    public void mouseEntered(MouseEvent event){}

    public void mouseExited(MouseEvent event){

    }

}
