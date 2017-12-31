package com.company;

import java.awt.*;
import java.util.LinkedList;

import static com.company.GameMode.PVE;
import static com.company.Player.P1;
import static com.company.Player.P2;

import static com.company.Constants.*;

/**
 * Objects of this class contain information about every move that has been made.
 * It also provides methods used to make player or AI move
 */
public class GameState{
    /**
     * List of points in chronological order
     */
    private LinkedList<Point> points = new LinkedList<Point>();
    /**
     * In order to calculate AI move, a tree of possible moves is generated.
     * Each GameState object contains a List that can be filled with all possible situations and parent node
     */
    private LinkedList<GameState> children = new LinkedList<GameState>();
    private GameState parent;
    /**
     * in order to decrease time of calculations in case of high number of possible combinations, a maximum number of
     * considered bounces in move is introduced.
     */
    private int maxBouncesNum=5;
    /**
     * this value contains information whether or not in current GameState, a player or AI has scored
     * 1 if P1 scored, -1 if AI or P2 scored, 0 if none
     */
    private int scored = 0;
    private Player currentPlayer=Player.P1;
    public GameMode gameMode;



    public GameState(){
        Point p = new Point(0,0);
        points.addLast(p);
        parent = null;
    }

    /**
     * Copying constructor
     * @param g
     */
    public GameState(GameState g){
        for (Point p: g.points){
            points.addLast(p.copy());
        }
        currentPlayer = g.currentPlayer;
        gameMode = g.gameMode;
        scored = g.scored;

    }

    public int getScored() {
        return scored;
    }
    private GameState getChild(Direction direction){
        GameState child = new GameState();
        Point next = this.getLast().getNext(direction);
        child.points.removeLast();
        child.points.addLast(next);
        return child;
    }
    private GameState getCombined(){
        if (parent==null){
            return this;
        }
        GameState whole;
        whole = parent.getCombined();
        return null;
    }
    public GameMode getGameMode() {
        return gameMode;
    }
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * This method indicates whether or not player is permitted to make a move in specified direction
     * @param direction This is the direction to be checked
     * @return Boolean value - permitted or not
     */
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
                    else continue;
                }
                if (p.getNext(direction).equals(points.get(i-1)) || p.getNext(direction).equals(points.get(i+1))){
                    //System.out.print("nie mozna ruszyc");
                    return false;
                }
            }

        }
        if (Boundries.isInCorner(last.getNext(direction))){
            return false;
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

    /**
     * This method is used to switch between players.
     * Depending on current game mode (PVP or PVE) and current player, this method decides which player's turn is next
     * and sets them as currentPlayer
     */
    void switchPlayers(){
        GameMode mode = gameMode;
        //System.out.println("swittching players");
        if (mode== GameMode.PVP){
            //System.out.println("in mode PVP");

            if (currentPlayer==Player.AI){
                //System.out.println("from AI to P1");

                currentPlayer = P2;
            }
            else if (currentPlayer== P1){
                //System.out.println("from P1 to P2");
                currentPlayer = P2;
            }
            else if (currentPlayer== P2){
                //System.out.println("from P2 to P1");
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
            else if (currentPlayer== P2){
                currentPlayer = Player.P1;
            }
        }
    }

    /**
     * This method checks whether after moving to point p player should bounce and continue their move or not
     * @param p Method checks if this the game state already consists pont of this coordinates
     * or the point is on an edge
     * @return Boolean value indicating whether player bounces
     */
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

    /**
     * This method adds a point to a list of points based on direction of a move. This method DOES NOT check if the
     * move is permitted (this is checked in an earlier state of attempting a move). The method also switches players
     * if their move ends.
     * @param direction This is the direction of a move
     */
    public void move(Direction direction){
        Point newPoint = points.getLast().getNext(direction);
        newPoint.setMadeBy(currentPlayer);
        if (!bounce(newPoint)){
            switchPlayers();
        }

        points.addLast(newPoint);
    }

    /**
     * This method renders a line that has been made on specified pitch
     * @param g
     * @param pitchPos This parameter determines where the top left corner of the pitch is
     * @param squareSize This parameter determines the length of a single line
     */
    void render(Graphics g, Point pitchPos, int squareSize){
        for (int i=0; i<points.size()-1; i++){
            g.setColor(P1Color);
            int x1=pitchPos.getX()+5*squareSize+points.get(i).getX()*squareSize;
            int y1=pitchPos.getY()+4*squareSize+points.get(i).getY()*squareSize;
            int x2=pitchPos.getX()+5*squareSize+points.get(i+1).getX()*squareSize;
            int y2=pitchPos.getY()+4*squareSize+points.get(i+1).getY()*squareSize;
            if (points.get(i+1).getMadeBy()==Player.P1){
                g.setColor(P1Color);
            }
            if (points.get(i+1).getMadeBy()==Player.P2){
                g.setColor(P2Color);
            }
            if (points.get(i+1).getMadeBy()==Player.AI){
                g.setColor(AIColor);
            }

            g.drawLine(x1,y1,x2,y2);

        }
        Point ballPos = getLast();
        int radius = 2;
        int x=pitchPos.getX()+5*squareSize+ballPos.getX()*squareSize-radius;
        int y=pitchPos.getY()+4*squareSize+ballPos.getY()*squareSize-radius;
        g.fillOval(x,y,2*radius,2*radius);

    }

    /**
     * This method erases a move and switches player accordingly
     */
    public void eraseMove(){
        if (points.size()<2){
            return;
        }
        if (!bounce(getLast())){
            switchPlayers();
        }
        Player last = getLast().getMadeBy();
        while (points.getLast().getMadeBy()==last){
            points.removeLast();
        }

    }

    /**
     * @return The last point that has been added to the list
     */
    public Point getLast(){
        return points.getLast();

    }

    /**
     * copies a gameState (without tree info). Redundant because of copying constructor.
     * @return Copied GameState
     */
    private GameState copy(){
        GameState n = new GameState();
        for (int i = 1; i<points.size();i++){
            n.points.addLast(this.points.get(i));
        }
        n.setCurrentPlayer(getCurrentPlayer());
        n.setGameMode(getGameMode());
        n.scored = scored;
        return n;
    }

    /**
     * This method finds all game states that are possible to reach within one turn assuming no more
     * than a specified number of bounces
     * @param maxBounces This paramenter limits maximum number of bounces that are considered
     * @return A list of all generated game states
     */
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
                    states.addLast(n);
                }
            }
        }
        return states;
    }

    /**
     * This method generates a tree of all possible moves starting from this object.
     * @param depth This parameter determines the depth of generated tree
     */
    public void generateTree(int depth){
        if (depth==0){
            return;
        }
        this.children=findChildren(maxBouncesNum);
        for (GameState child: children){
            child.generateTree(depth-1);
        }
    }

    /**
     * This method calculates how beneficial for Player 1 the CURRENT situation is. It takes into consideration ONLY
     * the position of the last made point- the closer to the P2 or AI goal the higher the rating.
     * AI uses this value to determine the best move.
     * @return A rating. 0 means the last point is in the middle, not favorable for any side.
     * Positive rating indicates the advantage of P1
     */
    public double getCurrentRating(){
        float k=0.0f;// for each point closer to the enemy goal gets k points
        float goalRating=9999;
        if (Boundries.score(getLast())==1){
            scored = 1;
            return goalRating;

        }
        if (Boundries.score(getLast())==-1){
            scored = -1;
            return -goalRating;
        }
        int x = getLast().getX();
        int y = getLast().getY();
        return (double)(x*x*x)/(double)(y*y+3);
        /*for (Point p:points){
            if (p.getX()<x){
                rating+=k;
            }
            if (p.getX()>x){
                rating-=k;
            }
        }*/
    }

    /**
     * This method
     * @return
     */
    public double getRealRating(){//AI needs low rating
        Player current = getCurrentPlayer();
        double bestRating = 69000;
        if (current == P1){
            bestRating = -69000;
        }
        if (children.isEmpty()){
            bestRating = getCurrentRating();
        }
        getCurrentRating();
        if (scored != 0){
            return (9999*scored);
        }
        for (GameState child: children){
            double childRating = child.getRealRating();
            if (current == P1) {
                if (childRating > bestRating) {
                    bestRating = childRating;
                }
            }
            else{
                if (childRating < bestRating) {
                    bestRating = childRating;
                }
            }

        }
        return bestRating;
    }
    public GameState getBestMove(){
        double bestRating=100000;
        if (currentPlayer==P1){
            bestRating = -100000;
        }
        GameState best = new GameState();
        for (GameState child: children){
            double rating = child.getRealRating();
            if (currentPlayer == Player.P1) {
                if (rating > bestRating) {
                    bestRating = rating;
                    best = child;
                }
            }
            else {
                if (rating < bestRating) {
                    bestRating = rating;
                    best = child;
                }
            }
        }
        System.out.print("best rating: ");
        System.out.println(bestRating);
        return best;
    }

    public int treeSize(){
        int a = 0;
        if (children.size()==0){
            return 1;
        }
        for (GameState child: children){
            a+=child.treeSize();
        }
        if (a==0){
            scored = 1;
        }
        return a;
    }
    
}
