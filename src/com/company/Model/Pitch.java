package com.company.Model;

import com.company.Const.Constants;
import com.company.Const.Direction;

import java.awt.*;
import java.util.Observable;

public class Pitch {
    private int posX;
    private int posY;
    private int squareSize;
    private GameState currentGameState;

    public int getPosX() {
        return posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getSquareSize() {
        return squareSize;
    }
    public void setSquareSize(int squareSize) {
        this.squareSize = squareSize;
    }



    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }



    public Pitch(int posX, int posY, int squareSize){
        this.posX=posX;
        this.posY=posY;
        this.squareSize =squareSize;
        currentGameState = new GameState();
    }

    //konstruktor kopiujacy
    public Pitch(Pitch p){
        posX = p.posX;
        posY = p.posY;
        squareSize = p.squareSize;
        currentGameState = new GameState(p.getCurrentGameState());

    }


    public void render(Graphics g){
        g.setColor(Color.green);
        currentGameState.render(g, new Point(posX,posY), squareSize);
        g.setColor(Constants.PitchColor);
        int borderWidth=2;
        g.fillRect(posX,posY-borderWidth/2,10*squareSize,borderWidth);              //horizontal upper line
        g.fillRect(posX,posY+8*squareSize-borderWidth/2,10*squareSize,borderWidth); //horizontal lower line
        g.fillRect(posX-borderWidth/2,posY,borderWidth,3*squareSize);               //vertical left upper
        g.fillRect(posX-borderWidth/2,posY+5*squareSize,borderWidth,3*squareSize);//vertical left lower
        g.fillRect(posX+10*squareSize-borderWidth/2,posY,borderWidth,3*squareSize); //vertical right upper
        g.fillRect(posX+10*squareSize-borderWidth/2,posY+5*squareSize,borderWidth,3*squareSize);//vertical right lower
        //goals horizontal:
        g.fillRect(posX-squareSize,posY+3*squareSize-borderWidth/2,squareSize,borderWidth);
        g.fillRect(posX+10*squareSize,posY+3*squareSize-borderWidth/2,squareSize,borderWidth);
        g.fillRect(posX-squareSize,posY+5*squareSize-borderWidth/2,squareSize,borderWidth);
        g.fillRect(posX+10*squareSize,posY+5*squareSize-borderWidth/2,squareSize,borderWidth);
        //goals vertical:
        g.fillRect(posX-squareSize-borderWidth/2,posY+3*squareSize-borderWidth/2,borderWidth,2*squareSize);
        g.fillRect(posX+11*squareSize-borderWidth/2,posY+3*squareSize-borderWidth/2,borderWidth,2*squareSize);

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
    public void attemptMove(Point viewPos){

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
