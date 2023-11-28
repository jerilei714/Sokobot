package solver;

import java.awt.Point;
import java.util.Set;

public class Heuristics {

    Set<Point> goalLocations;


    Heuristics(Set<Point> goalLocations){
        this.goalLocations=goalLocations;
    }

    private int manhattanDistance(Set<Point> crateLocations){
        int totalDistance = 0;

        for(Point crateLoc:crateLocations){
            int min = Integer.MAX_VALUE;
            for (Point goalLoc : goalLocations){
                //get the integer value of absolute distance of a crate from goal
                int currDistance = (int)(Math.abs(crateLoc.getX()-goalLoc.getX()) +
                        Math.abs(crateLoc.getY()-goalLoc.getY()));
                //save the nearest distance to goal of crate
                if(min > currDistance){
                    min = currDistance;
                }
            }
            //save that min distance
            totalDistance += min;

        }
        return totalDistance;
    }

    public int getCost(Set<Point> crateLocations){
        return manhattanDistance(crateLocations);
    }
}