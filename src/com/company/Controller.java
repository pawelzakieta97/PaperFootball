package com.company;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer, PropertyChangeListener{


    private View view;
    private Model model;



    private MouseEventHandler mouseEventHandler;
    private ToolbarMenuController toolbarMenuController;

    public Controller (View view, Model model){
        this.view=view;
        this.model = model;
        toolbarMenuController = new ToolbarMenuController();
        toolbarMenuController.assignController(this);
        toolbarMenuController.toolbarSetup();
        model.setDifficulty(view.difficulty.getSelectedIndex()+1);
        mouseEventHandler = new MouseEventHandler();
        mouseEventHandler.assignController(this);
        //view.addMouseListener(mouseEventHandler);
        view.assignController(this);
        view.
        model.assignController(this);

    }
    public MouseEventHandler getMouseEventHandler() {
        return mouseEventHandler;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
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


    public void startNewGame(GameMode gameMode){
        model.startNewGame(gameMode);
    }
}
