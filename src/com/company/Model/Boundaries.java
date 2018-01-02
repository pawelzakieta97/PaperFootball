package com.company.Model;

import java.util.LinkedList;

/**
 * Tis class contains methods that are used to determine ball's position on the pitch such as being on an edge
 * or in goal
 */
public class Boundaries {
    /**
     * This method checks if the ball is on a horizontal edge
     * @param p Point that represents ball's position to check
     * @return boolean value saying whether ball is on horizontal edge
     */
    public static boolean isOnHorizontal(Point p){
        if (p.getY()==4 || p.getY()==-4){
            if (p.getX()>=-5 && p.getX()<=5){
                return true;
            }
        }
        if (p.getX()==-6 || p.getX()==-6 || p.getX()==-5 || p.getX()==6){
            if (p.getY()==1 || p.getY()==-1){
                return true;
            }
        }
        return false;
    }
    /**
     * This method checks if the ball is on a vertical edge
     * @param p Point that represents ball's position to check
     * @return boolean value saying whether ball is on vertical edge
     */
    public static boolean isOnVertical(Point p){
        if (p.getX()==-5 || p.getX()==5){
            if (p.getY()>=-4 && p.getY()<=4){
                if (p.getY()!=0){
                    return true;
                }
            }
            if (p.getX() == -6 || p.getX() == 6){
                if (p.getY()==1 || p.getY()==0 || p.getY()==-1) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * This method checks if the ball is in corner (of the pitch or of the goal)
     * @param p Point that represents ball's position to check
     * @return boolean value saying whether ball is in corner
     */
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
    /**
     * This method checks if the ball is inside the pitch (not on any edge)
     * @param p Point that represents ball's position to check
     * @return boolean value saying whether ball is inside the pitch
     */
    public static boolean isInside(Point p){
        if (p.getX()>-5 && p.getX()<5 && p.getY()>-4 && p.getY()<4){
            return true;
        }
        if (p.equals(new Point(-5,0)) || p.equals(new Point (5,0))){
            return true;
        }
        return false;
    }
    /**
     * This method checks if the ball is outside the pitch (not on any edge)
     * @param p Point that represents ball's position to check
     * @return boolean value saying whether ball is outside the pitch
     */
    public static boolean isOutside(Point p){
        return (!isInside(p)  && !isOnHorizontal(p) && !isOnVertical(p));
    }
    /**
     * This method checks if the ball is inside a goal
     * @param p Point that represents ball's position to check
     * @return int value indicating if the ball is inside a goal and which goal it is
     * (-1 if AI or P2 scored, 1 if P1 scored, 0 if none)
     */
    public static int score(Point p){
        if (p.getY()==0){
            if (p.getX()==-6){
                return -1;
            }
            if (p.getX()==6){
                return 1;
            }
        }
        return 0;
    }
}
