package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class Controller implements MouseListener{
    private View view;
    private Model model;
    private final int tick = 60;
    private Thread thread;
    private boolean running=false;
    public AppState appState = AppState.MENU;

    public Controller (View view, Model model){
        this.view=view;
        this.model = model;
        view.addMouseListener(this);

    }

    public void render(Graphics g){
        view.render();
    }
    public void run(){
        startPVPGame();
        System.out.println("niby kurwa zaczyna PVP");
        while (true){
            long begin = System.currentTimeMillis();
            while (System.currentTimeMillis()-begin<1000/tick){
                view.render();

            }
            //PointerInfo a = MouseInfo.getPointerInfo();
            //System.out.println(a.getLocation());

            model.tick();

        }
    }
    private void startPVPGame(){
        appState = AppState.PVP;
        model.startPVP();
    }

    public void mouseClicked(MouseEvent event){
    }

    public void mousePressed(MouseEvent event){
    }

    public void mouseReleased(MouseEvent event){
        if (event.getButton()==MouseEvent.BUTTON1){
            System.out.println("button 1");
            Point pos = new Point(event.getX(),event.getY());
            if (appState==AppState.PVP){
                model.pitch.move(pos);
            }
            model.pitch.currentGameState.findChildren();

        }
        if (event.getButton()==MouseEvent.BUTTON3){
            System.out.println("button 3");
            model.pitch.currentGameState.eraseMove();
        }

    }

    public void mouseEntered(MouseEvent event){}

    public void mouseExited(MouseEvent event){

    }
}
