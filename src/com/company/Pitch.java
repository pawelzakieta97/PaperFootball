package com.company;

import java.awt.*;

public class Pitch extends GameObject{
    int posX;
    int posY;
    int squareSize;

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameState currentGameState;
    public Pitch(int posX, int posY, int squareSize){
        this.posX=posX;
        this.posY=posY;
        this.squareSize =squareSize;
        currentGameState = new GameState();
    }

    protected void tick() {

    }

    public void render(Graphics g){
        int dotDiameter=4;
        g.setColor(Color.green);
        g.fillRect(posX,posY,squareSize*10,squareSize*8);
        g.fillRect(posX-squareSize,posY+3*squareSize,squareSize*12,squareSize*2);
        currentGameState.render(g, new Point(posX,posY), squareSize);
        g.setColor(Color.black);
        for (int y=0;y<9;y++){
            for (int x=0; x<11;x++){
                g.fillRect(posX+x*squareSize-dotDiameter/2, posY+y*squareSize-dotDiameter/2,dotDiameter,dotDiameter);
            }
        }
    }
    //converts pitch coordinates to view coordinates
    public Point pitch2view(Point pitch){
        return new Point(posX+5*squareSize+pitch.getX()*squareSize,posY+4*squareSize+pitch.getY()*squareSize);
    }
    //converts view coordinates to pitch coordinates (returns the closest one)
    public Point view2pitch(Point view){
        Double x = new Double(view.getX()-posX-6*squareSize)/squareSize+10.5;
        Double y = new Double(view.getY()-posY-5*squareSize)/squareSize+10.5;
        Point pitch= new Point(x.intValue()-9,y.intValue()-9);
        return pitch;

    }
    //moves to clicked position if its permitted
    public void move(Point viewPos){

        Point last = currentGameState.getLast();
        Point pitchPos = view2pitch(viewPos);
        if (last.getNext(Direction.U).equals(pitchPos)){
            if (currentGameState.isPermitted(Direction.U)) {
                currentGameState.move(Direction.U);
            }

        }
        if (last.getNext(Direction.UR).equals(pitchPos)){
            if (currentGameState.isPermitted(Direction.UR)) {
                currentGameState.move(Direction.UR);
            }
        }
        if (last.getNext(Direction.R).equals(pitchPos)){
            if (currentGameState.isPermitted(Direction.R)) {
                currentGameState.move(Direction.R);
            }
        }
        if (last.getNext(Direction.DR).equals(pitchPos)){
            if (currentGameState.isPermitted(Direction.DR)) {
                currentGameState.move(Direction.DR);
            }
        }
        if (last.getNext(Direction.D).equals(pitchPos)){
            if (currentGameState.isPermitted(Direction.D)) {
                currentGameState.move(Direction.D);
            }
        }
        if (last.getNext(Direction.DL).equals(pitchPos)){
            if (currentGameState.isPermitted(Direction.DL)) {
                currentGameState.move(Direction.DL);
            }
        }
        if (last.getNext(Direction.L).equals(pitchPos)){
            if (currentGameState.isPermitted(Direction.L)) {
                currentGameState.move(Direction.L);
            }
        }
        if (last.getNext(Direction.UL).equals(pitchPos)) {
            if (currentGameState.isPermitted(Direction.UL)) {
                currentGameState.move(Direction.UL);
            }
        }

        System.out.println(currentGameState.getCurrentRating());
    }

}
