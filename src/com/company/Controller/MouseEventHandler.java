package com.company.Controller;

import com.company.Model.Player;
import com.company.Model.Point;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is responsible for translating mouse input into players' move
 */
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


    /**
     * MouseEventHandler implements MouseListener interface. Whenever a left mouse button is released, this method
     * attempts to make an appropriate move in a separate thread. After making a player's move this method checks if
     * AI is supposed to make a move as well
     * @param event MouseEvent object containing mouse position information
     */
    public void mouseReleased(MouseEvent event){
        if (event.getButton()==MouseEvent.BUTTON1){

            if (controller.getModel().getPitch().getCurrentGameState().gameEnded()){
                return;
            }
            System.out.println("button 1");
            Point pos = new Point(event.getX(),event.getY());
            executor.execute(new Runnable(){
                public void run(){
                    controller.getModel().makePlayerMove(pos);
                    if (controller.getModel().getPitch().getCurrentGameState().getCurrentPlayer()== Player.AI){
                        controller.getModel().makeAIMove(controller.getModel().getDifficulty());
                    }
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
