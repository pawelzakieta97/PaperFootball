package com.company.Model;

import com.company.Controller.Controller;
import com.company.Const.GameMode;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;

import static com.company.Const.Constants.*;

/**
 * This class represents model of the MVC structure. The controller calls methods of this method (in separate thread),
 * and listens for change
 */
public class Model{
    /**
     * this field is used to add the controller to listeners list.
     */
    private Controller controller;
    /**
     * This field is an integer number that describes the difficulty of the game against AI. It determines the depth
     * of the tree of possible moves. (the higher the more difficult the game, but moves can take quite a long
     * if above 2)
     */
    private int difficulty=2;
    /**
     * This field represents the pitch and contains GameState object- information about the current state of the game
     */
    private Pitch pitch;
    /**
     * This field is necessary to handle propertyChanges
     */
    private PropertyChangeSupport propertyChangeSupport;


    /**
     * The default constructor, creates a propertyChangeSupport object
     */
    public Model(){
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    public int getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Pitch getPitch() {
        return pitch;
    }
    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }
    private void addPropertyChangeListener(PropertyChangeListener l){
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    private void firePropertyChange(String propertyName, Pitch oldPitch, Pitch newPitch) {
        propertyChangeSupport.firePropertyChange(propertyName, oldPitch, newPitch);
    }

    /**
     * This method assigns controller to the model and adds the controller to the list of property change listeners
     * @param controller This is a reference of the controller to be assigned
     */
    public void assignController(Controller controller){
        this.controller= controller;
        addPropertyChangeListener(controller);
    }

    /**
     * This method creates a enw pitch object in the middle of the frame and with a grid spacing specified in Constants
     * class. Than, an appropriate game mode is set and the starting player.
     * @param mode This parameter specifies what mode the game will be- Player vs Player or Player vs Computer
     */
    public void startNewGame(GameMode mode){

        Pitch pitch = new Pitch(WIDTH/2-5*SQUARE_SIZE,HEIGHT/2-5*SQUARE_SIZE, SQUARE_SIZE);
        pitch.getCurrentGameState().setCurrentPlayer(Player.P1);
        pitch.getCurrentGameState().setGameMode(mode);
        this.pitch = pitch;
        Pitch newPitch = new Pitch(pitch);
        firePropertyChange("pitch",null,newPitch);


    }

    // Funkcja wywoływana przy kliknięciu lewego przycisku myszy. Jeśli jest tura gracza i wskazany ruch jest dozwolony,
    //jest on wykonywany.

    /**
     * This method is called whenever a left mouse button is released in the game window. First of all, it
     * checks if the player is actually supposed to make a move (and not the AI). If so, the method calls an
     * attemptMove method form the pitch. That method will then determine if the move is valid and if it is- will modify
     * the GameState object. Next, the property change notification is fired to re-render the pitch.
     *
     * @param mousePos Point objects that contains the coordinates of the mouse one the window.
     */
    public void makePlayerMove(Point mousePos){
        if (pitch.getCurrentGameState().getCurrentPlayer()==Player.AI) return;
        Pitch oldPitch = new Pitch(pitch);
        pitch.attemptMove(mousePos);
        Pitch newPitch = new Pitch(pitch);
        firePropertyChange("pitch",oldPitch,newPitch);
    }

    //Funkcja wynonywująca ruch AI jeśli jest jego tura.
    /**
     * This method manages the process of making the move by the computer. First of all, it checks if it's AI's turn.
     * Then, it caps the difficulty (1 or 2 to avoid lengthy calculations). The tree of possible moves is generated and
     * is than used to find the best move. The resulting GameState object replaces the current game state. Lastly, the
     * property change notification is fired.
     *
     * @param difficulty objects that contains the coordinates of the mouse one the window.
     */
    public void makeAIMove(int difficulty){
        if (pitch.getCurrentGameState().getCurrentPlayer()!=Player.AI) return;
        if (difficulty<1) difficulty =1;
        if (difficulty>2) difficulty = 2;
        //difficulty = 3;
        Pitch oldPitch = new Pitch(pitch);
        pitch.getCurrentGameState().generateTree(difficulty);
        System.out.println(pitch.getCurrentGameState().treeSize());
        pitch.setCurrentGameState(pitch.getCurrentGameState().getBestMove());
        Pitch newPitch = new Pitch(pitch);
        firePropertyChange("pitch",oldPitch,newPitch);
    }

    public void setCurrentGameMode(GameMode mode){
        pitch.getCurrentGameState().setGameMode(mode);
    }
    public void setMode(GameMode mode){
        pitch.getCurrentGameState().setGameMode(mode);
    }

    /**
     * This method calls eraseMove method from currentGameState and fires a property change notification
     */
    public void eraseMove(){
        Pitch oldPitch = new Pitch(pitch);
        pitch.getCurrentGameState().eraseMove();
        Pitch newPitch = new Pitch(pitch);
        firePropertyChange("pitch",oldPitch,newPitch);
    }
}
