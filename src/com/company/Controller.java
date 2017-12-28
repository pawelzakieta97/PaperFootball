package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer, PropertyChangeListener{
    private View view;
    private Model model;
    private MouseEventHandler mouseEventHandler;
    //public AppState appState = AppState.MENU;

    public Controller (View view, Model model){
        this.view=view;
        this.model = model;

        model.setDifficulty(view.difficulty.getSelectedIndex()+1);
        toolbarSetup();
        mouseEventHandler = new MouseEventHandler();
        mouseEventHandler.assignController(this);
        view.addMouseListener(mouseEventHandler);

    }
    public void update(Observable obs, Object obj){
        System.out.println("update z controllera");
        view.repaint();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        view.modelPropertyChange(evt);
        System.out.println("property change z controllera");
        view.repaint();
    }


    public Model getModel(){
        return this.model;
    }

    private void toolbarSetup(){
        view.resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.selectedMode.getSelectedIndex()==0) {
                    startNewGame(AppState.PVP);
                }
                if (view.selectedMode.getSelectedIndex()==1) {
                    startNewGame(AppState.PVE);
                }
                model.getPitch().addObserver(Controller.this);
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
    public void startNewGame(AppState gameMode){
        model.startNewGame(gameMode);
        //model.getPitch().addObserver(this);
        model.getPitch().forceUpdate();
    }
}
