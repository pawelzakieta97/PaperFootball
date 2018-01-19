package com.company.Controller;

import com.company.Const.GameMode;
import com.company.Model.Model;
import com.company.View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;
/**
 * This class is responsible for the logic of the entire program. It processes user input and calls the appropriate
 * methods from model class. When model changes, this class updates the view.
 */
public class Controller implements PropertyChangeListener{

    private View view;
    private Model model;
    /**
     * this object is responsible for handling mouse input (only on the pitch, not the menu bar)
     */
    private MouseEventHandler mouseEventHandler;
    /**
     * this object assigns proper actions to toolbar buttons
     */
    private ToolbarMenuController toolbarMenuController;

    /**
     * The constructor of Controller class.
     * @param view
     * @param model
     */
    public Controller (View view, Model model){
        this.view=view;
        this.model = model;
        toolbarMenuController = new ToolbarMenuController();
        toolbarMenuController.assignController(this);
        toolbarMenuController.toolbarSetup();
        model.setDifficulty(view.difficulty.getSelectedIndex()+1);
        mouseEventHandler = new MouseEventHandler();
        mouseEventHandler.assignController(this);
        view.assignController(this);
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

    /**
     * Controller class implements PropertyChangeListener - whenever a change in model is made, the controller gets
     * notified. It then rerenders the view.
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt) {
        view.modelPropertyChange(evt);
        System.out.println("property change z controllera");
        System.out.println(model.getPitch().getCurrentGameState().checkStuck());
        view.render();
    }


    public Model getModel(){
        return this.model;
    }


    public void startNewGame(GameMode gameMode){
        model.startNewGame(gameMode);
    }
}
