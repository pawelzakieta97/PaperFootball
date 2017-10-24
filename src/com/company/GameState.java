package com.company;

import java.awt.*;
import java.util.LinkedList;

import static com.company.AppState.PVE;
import static com.company.Player.P1;
import static com.company.Player.P2;

//Class containing information about every move made during the game
public class GameState {
    double realRating; //0 is neutral, positive is good for p1, negative is good for p2/AI
    double potentialRating; //rating reached in future assuming best possible moves
    private LinkedList<Point> points = new LinkedList<Point>();
    private LinkedList<GameState> children = new LinkedList<GameState>();
    private int maxBouncesNum=4; //number of bounces that allowed per one move (with more complex states this decreases calculation time)

    public void setGameMode(AppState gameMode) {
        this.gameMode = gameMode;
    }

    AppState gameMode;
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    private Player currentPlayer=Player.AI;
    public GameState(){
        Point p = new Point(0,0);
        points.addLast(p);
    }

    //Checks if the line is not occupied
    public boolean isPermitted(Direction direction){
        if (points.size()<2) {
            return true;
        }
        Point last = points.getLast();
        if (last.getNext(direction).equals(points.get(points.size()-2))){
            return false;
        }

        if (Boundries.score(last.getNext(direction))!=0){
            return true;
        }

        for (int i=0; i<points.size()-2; i++){
            Point p=points.get(i);
            if (p.equals(last)){
                if (i==0){
                    if (p.getNext(direction).equals(points.get(1))){
                        return false;
                    }
                    else return true;
                }
                if (p.getNext(direction).equals(points.get(i-1)) || p.getNext(direction).equals(points.get(i+1))){
                    System.out.print("nie mozna ruszyc");
                    return false;
                }
            }

        }
        if (Boundries.isOnHorizontal(last)){
            if (Boundries.isOnHorizontal(last.getNext(direction))){
                return false;
            }
        }
        if (Boundries.isOnVertical(last)){
            if (Boundries.isOnVertical(last.getNext(direction))){
                return false;
            }
        }
        if (Boundries.isOutside(last.getNext(direction))){
            return false;
        }
        return true;
    }
    void switchPlayers(){
        AppState mode = gameMode;
        System.out.println("swittching players");
        if (mode==AppState.PVP){
            System.out.println("in mode PVP");

            if (currentPlayer==Player.AI){
                System.out.println("from AI to P1");

                currentPlayer = P1;
            }
            else if (currentPlayer== P1){
                System.out.println("from P1 to P2");
                currentPlayer = P2;
            }
            else if (currentPlayer== P2){
                System.out.println("from P2 to P1");
                currentPlayer = P1;
            }
        }
        if (mode== PVE){
            if (currentPlayer==Player.AI){
                currentPlayer = P1;
            }
            else if (currentPlayer== P1){
                currentPlayer = Player.AI;
            }
        }
    }
    //returns true if after moving to this position, you bounce
    private boolean bounce(Point p){
        for (Point point: points){
            if (p.equals(point)){
                return true;
            }
        }
        if (Boundries.isOnVertical(p) || Boundries.isOnHorizontal(p)){
            return true;
        }
        return false;
    }

    public void move(Direction direction){
        Point newPoint = points.getLast().getNext(direction);
        newPoint.setMadeBy(currentPlayer);
        if (!bounce(newPoint)){
            switchPlayers();
        }

        points.addLast(newPoint);
    }
    void render(Graphics g, Point pitchPos, int squareSize){
        for (int i=0; i<points.size()-1; i++){
            int x1=pitchPos.getX()+5*squareSize+points.get(i).getX()*squareSize;
            int y1=pitchPos.getY()+4*squareSize+points.get(i).getY()*squareSize;
            int x2=pitchPos.getX()+5*squareSize+points.get(i+1).getX()*squareSize;
            int y2=pitchPos.getY()+4*squareSize+points.get(i+1).getY()*squareSize;
            if (points.get(i+1).getMadeBy()==Player.P1){
                g.setColor(Color.blue);
            }
            if (points.get(i+1).getMadeBy()==Player.P2){
                g.setColor(Color.red);
            }
            if (points.get(i+1).getMadeBy()==Player.AI){
                g.setColor(Color.white);
            }

            g.drawLine(x1,y1,x2,y2);
        }

        for (GameState child: children){
            child.render(g, pitchPos, squareSize);
        }
    }
    public void eraseMove(){
        if (points.size()<2){
            return;
        }
        Player last = getLast().getMadeBy();
        while (points.getLast().getMadeBy()==last){
            points.removeLast();
            System.out.println("usuwanie");
        }
        switchPlayers();
    }
    public Point getLast(){
        return points.getLast();

    }
    private GameState copy(){
        GameState n = new GameState();
        for (int i = 1; i<points.size();i++){
            n.points.addLast(this.points.get(i));
        }
        return n;
    }
    public LinkedList<GameState> findChildren(int maxBounces){
        LinkedList<GameState> states = new LinkedList<GameState>();
        for (Direction dir : Direction.values()){
            GameState n=this.copy();
            if (n.isPermitted(dir)){
                Point p = n.getLast().getNext(dir);

                if (n.bounce(p)){
                    if (maxBounces>0){
                    n.move(dir);
                    states.addAll(n.findChildren(maxBounces-1));
                    }
                }
                else{
                    n.move(dir);
                    System.out.println("dodawanie dziecka");
                    states.addLast(n);
                }
            }
        }
        return states;
    }

    public void generateTree(int depth){
        if (depth==0){
            return;
        }
        this.children=findChildren(maxBouncesNum);
        for (GameState child: children){
            child.generateTree(depth-1);
        }
    }
    public void calculateRating(){
        float k=0.0f;// for each point closer to the enemy goal gets k points
        float goalRating=9999;
        if (Boundries.score(getLast())==2){
            realRating= goalRating;
            return;
        }
        if (Boundries.score(getLast())==1){
            realRating= -goalRating;
            return;
        }
        int x = getLast().getX();
        int y = getLast().getY();
        realRating = (double)(x*x*x)/(double)(y*y+3);
        for (Point p:points){
            if (p.getX()<x){
                realRating+=k;
            }
            if (p.getX()>x){
                realRating-=k;
            }
        }
    }
    
}
