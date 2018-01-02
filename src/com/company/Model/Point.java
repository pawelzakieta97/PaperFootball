package com.company.Model;

import com.company.Const.Direction;

public class Point {
    public Point(int xx, int yy){
        x=xx;
        y=yy;
    }
    private int x;
    private int y;

    public Player getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(Player madeBy) {
        this.madeBy = madeBy;
    }

    private Player madeBy;

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public Point copy(){
        Point p = new Point(getX(), getY());
        p.setMadeBy(this.madeBy);
        return p;
    }

    boolean equals(Point p){
        return (this.x==p.x && this.y==p.y);

    }
    //zwraca pozycję punktu w kierunku podanym w argumencie
    public Point getNext(Direction direction){
        Point next= new Point(this.x,this.y);
        switch (direction){
            case U:
                next.y--;
                break;
            case UR:
                next.y--;
                next.x++;
                break;
            case R:
                next.x++;
                break;
            case DR:
                next.x++;
                next.y++;
                break;
            case D:
                next.y++;
                break;
            case DL:
                next.x--;
                next.y++;
                break;
            case L:
                next.x--;
                break;
            case UL:
                next.x--;
                next.y--;
                break;
        }
        return next;
    }

    public String toString() {
        return ("("+getX()+","+getY()+")");
    }
    public double distance(Point a){
        double dx=getX()-a.getX();
        double dy=getY()-a.getY();
        return Math.sqrt(dx*dx+dy*dy);
    }
}
