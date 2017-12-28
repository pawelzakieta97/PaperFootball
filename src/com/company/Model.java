package com.company;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.Observable;
import static com.company.Constants.*;

public class Model{
    LinkedList<GameObject> objects = new LinkedList<GameObject>();
    private Controller controller;
    private int difficulty=2;
    private Pitch pitch;
    protected PropertyChangeSupport propertyChangeSupport;

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

    public Model(){
        pitch = new Pitch(WIDTH/2-5*SQUARE_SIZE,HEIGHT/2-5*SQUARE_SIZE, SQUARE_SIZE);
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    public void addPropertyChangeListener(PropertyChangeListener l){
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    protected void firePropertyChange(String propertyName, Pitch oldPitch, Pitch newPitch) {
        propertyChangeSupport.firePropertyChange(propertyName, oldPitch, newPitch);
    }



    public void assignController(Controller controller){
        this.controller= controller;
        addPropertyChangeListener(controller);
    }



    public void startNewGame(AppState mode){

        Pitch pitch = new Pitch(WIDTH/2-5*SQUARE_SIZE,HEIGHT/2-5*SQUARE_SIZE, SQUARE_SIZE);
        pitch.getCurrentGameState().setCurrentPlayer(Player.P1);
        pitch.getCurrentGameState().setGameMode(mode);
        this.pitch = pitch;
    }

    // Funkcja wywoływana przy kliknięciu lewego przycisku myszy. Jeśli jest tura gracza i wskazany ruch jest dozwolony,
    //jest on wykonywany.
    public void makePlayerMove(Point mousePos){
        if (pitch.getCurrentGameState().getCurrentPlayer()==Player.AI) return;
        Pitch oldPitch = new Pitch(pitch);
        pitch.attemptMove(mousePos);
        Pitch newPitch = new Pitch(pitch);
        firePropertyChange("pitch",oldPitch,newPitch);
        oldPitch = newPitch;
        makeAIMove(difficulty);
        newPitch = new Pitch(pitch);
        firePropertyChange("pitch",oldPitch,newPitch);

    }

    //Funkcja wynonywująca ruch AI jeśli jest jego tura.
    public void makeAIMove(int difficulty){
        if (pitch.getCurrentGameState().getCurrentPlayer()!=Player.AI) return;
        if (difficulty<1) difficulty =1;
        if (difficulty>2) difficulty = 2;
        pitch.getCurrentGameState().generateTree(difficulty);
        System.out.println(pitch.getCurrentGameState().treeSize());
        pitch.setCurrentGameState(pitch.getCurrentGameState().getBestMove());
    }

    public void setCurrentGameMode(AppState mode){
        pitch.getCurrentGameState().setGameMode(mode);
    }

    public void setMode(AppState mode){
        pitch.getCurrentGameState().setGameMode(mode);
    }

    public void eraseMove(){
        Pitch oldPitch = new Pitch(pitch);
        pitch.getCurrentGameState().eraseMove();
        Pitch newPitch = new Pitch(pitch);
        firePropertyChange("pitch",oldPitch,newPitch);
    }
}
