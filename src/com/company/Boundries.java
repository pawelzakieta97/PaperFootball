package com.company;

import java.util.LinkedList;

public class Boundries {
    public static boolean isOnHorizontal(Point p){
        if (p.getY()==4 || p.getY()==-4){
            if (p.getX()>=-5 && p.getX()<=5){
                return true;
            }
        }
        return false;
    }
    public static boolean isOnVertical(Point p){
        if (p.getX()==-5 || p.getX()==5){
            if (p.getY()>=-4 && p.getY()<=4){
                if (p.getY()!=0){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isInCorner(Point p){
        LinkedList<Point> corners = new LinkedList<Point>();

        corners.addLast(new Point(-5,-4));
        corners.addLast(new Point(5,-4));
        corners.addLast(new Point(5,4));
        corners.addLast(new Point(-5,4));
        corners.addLast(new Point(-6,-1));
        corners.addLast(new Point(6,-1));
        corners.addLast(new Point(6,1));
        corners.addLast(new Point(-6,1));
        for (Point c: corners){
            if (p.equals(c)){
                return true;
            }
        }
        return false;
    }
    public static boolean isInside(Point p){
        if (p.getX()>-5 && p.getX()<5 && p.getY()>-4 && p.getY()<4){
            return true;
        }
        if (p.equals(new Point(-5,0)) || p.equals(new Point (5,0))){
            return true;
        }
        return false;
    }
    public static boolean isOutside(Point p){
        return (!isInside(p) && !isInCorner(p) && !isOnHorizontal(p) && !isOnVertical(p));
    }
    //returns 1 if ball is inside left goal and 2 if in right
    public static int score(Point p){
        if (p.getY()==0){
            if (p.getX()==-6){
                return 1;
            }
            if (p.getX()==6){
                return 2;
            }
        }
        return 0;
    }
}
