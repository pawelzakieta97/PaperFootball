package com.company;

import java.util.LinkedList;
import java.util.Observable;
import static com.company.Constants.*;

public class Model extends Observable{
    LinkedList<GameObject> objects = new LinkedList<GameObject>();
    private Controller controller;

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    private int difficulty=2;

    public Pitch getPitch() {
        return pitch;
    }

    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }

    private Pitch pitch;

    public Model(){
        pitch = new Pitch(WIDTH/2-6*SQUARE_SIZE,HEIGHT/2-5*SQUARE_SIZE, SQUARE_SIZE);
    }

    public void assignController(Controller controller){
        this.controller= controller;
    }
    public void addObject(GameObject o){
        this.objects.add(o);
    }
    public void removeObject(GameObject o){
        this.objects.remove(o);
    }




    public void startNewGame(AppState mode){

        Pitch pitch = new Pitch(100,100,30);
        pitch.getCurrentGameState().setCurrentPlayer(Player.P1);
        pitch.getCurrentGameState().setGameMode(mode);
        this.pitch = pitch;
        addObject(pitch);
        setChanged();
        notifyObservers();
    }

    // Funkcja wywoływana przy kliknięciu lewego przycisku myszy. Jeśli jest tura gracza i wskazany ruch jest dozwolony,
    //jest on wykonywany.
    public void makePlayerMove(Point mousePos){
        if (pitch.getCurrentGameState().getCurrentPlayer()==Player.AI) return;
        pitch.move(mousePos);
        //attempts making AI move (wont happen if PVP selected)
        setChanged();
        notifyObservers();
        makeAIMove(difficulty);



    }

    //Funkcja wynonywująca ruch AI jeśli jest jego tura.
    public void makeAIMove(int difficulty){
        if (pitch.getCurrentGameState().getCurrentPlayer()!=Player.AI) return;
        if (difficulty<1) difficulty =1;
        if (difficulty>2) difficulty = 2;
        pitch.getCurrentGameState().generateTree(difficulty);
        pitch.setCurrentGameState(pitch.getCurrentGameState().getBestMove());
        setChanged();
        notifyObservers();

    }

    public void setCurrentGameMode(AppState mode){
        pitch.getCurrentGameState().setGameMode(mode);
    }

    public void setMode(AppState mode){
        pitch.getCurrentGameState().setGameMode(mode);
    }
    public void eraseMove(){
        pitch.getCurrentGameState().eraseMove();
        setChanged();
        notifyObservers();
    }
}
