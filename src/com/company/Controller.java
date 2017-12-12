package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Observer;

public class Controller implements MouseListener{
    private View view;
    private Model model;
    private final int tick = 60;
    private Thread thread;
    private boolean running=false;
    private MouseEventHandler mouseEventHandler;
    //public AppState appState = AppState.MENU;

    public Controller (View view, Model model){
        this.view=view;
        this.model = model;
        view.addMouseListener(this);

        model.setDifficulty(view.difficulty.getSelectedIndex()+1);
        //view.paint();
        toolbarSetup();

    }



    public void mouseClicked(MouseEvent event){
    }

    public void mousePressed(MouseEvent event){
    }

    public void mouseReleased(MouseEvent event){
        if (event.getButton()==MouseEvent.BUTTON1){
            System.out.println("button 1");
            Point pos = new Point(event.getX(),event.getY());
            model.makePlayerMove(pos);

        }
        if (event.getButton()==MouseEvent.BUTTON3){
            System.out.println("button 3");
            //model.getPitch().currentGameState.eraseMove();
            model.eraseMove();
        }


    }

    public void mouseEntered(MouseEvent event){}

    public void mouseExited(MouseEvent event){

    }
    private void toolbarSetup(){
        view.resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.selectedMode.getSelectedIndex()==0) {
                    model.startNewGame(AppState.PVP);
                }
                if (view.selectedMode.getSelectedIndex()==1) {
                    model.startNewGame(AppState.PVE);
                }
            }
        });
        view.difficulty.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                model.setDifficulty(view.difficulty.getSelectedIndex()+1);
                System.out.print("difficulty changed to ");
                System.out.println(view.difficulty.getSelectedIndex()+1);
            }
        });
        view.selectedMode.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (view.selectedMode.getSelectedIndex()==0) {
                    model.getPitch().getCurrentGameState().setGameMode(AppState.PVP);
                }
                if (view.selectedMode.getSelectedIndex()==1) {
                    model.getPitch().getCurrentGameState().setGameMode(AppState.PVE);
                }

            }
        });
    }
    public void playerMoved(){
        if(model.getPitch().getCurrentGameState().getGameMode()==AppState.PVE){
            model.makeAIMove(model.getDifficulty());
        }
        view.repaint();
    }
}
