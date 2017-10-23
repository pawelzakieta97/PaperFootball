package com.company;

import java.util.LinkedList;

public class Model {
    LinkedList<GameObject> objects = new LinkedList<GameObject>();
    private Controller controller;
    public Pitch pitch;
    public void assignController(Controller controller){
        this.controller= controller;
    }
    public void addObject(GameObject o){
        this.objects.add(o);
    }
    public void removeObject(GameObject o){
        this.objects.remove(o);
    }
    public void tick(){
        if (controller.appState==AppState.PVP){

        }
    }
    public void startPVP(){

        Pitch pitch = new Pitch(100,100,30);
        pitch.currentGameState.setCurrentPlayer(Player.P1);
        this.pitch = pitch;

        addObject(pitch);
    }
}
