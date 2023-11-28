package solver;

import java.awt.Point;
import java.util.Set;

public class Node {

    //state - contains crate locations
    private final Set<Point> state;
    //private String name;
    private final Point playerLoc;
    private final String actions;
    private final Set<Point> goalState;
    private final int cost;

    Node(Set<Point>state, Point playerLoc, String actions,int cost,
         Set<Point> goalState){
        //this.name = name;
        this.state = state;
        this.playerLoc = playerLoc;
        this.actions = actions;
        this.cost = cost;
        this.goalState =goalState;
    }

    public boolean isGoalState(){
        return goalState.equals(state);
    }

    public int getCost() {
        return cost;
    }

    public Point getPlayerLoc() {
        return playerLoc;
    }

    public Set<Point> getState() {
        return state;
    }

    public String getActions() {
        return actions;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node other) {
            return playerLoc.equals(other.playerLoc) &&
                    state.equals(other.state);

        }
        return false;
    }


    @Override
    public int hashCode() {
        int hashCode = 0;
        for(Point crateCoordinate : state){
            hashCode +=crateCoordinate.hashCode();
            hashCode*=31;
        }
        return (int )(playerLoc.getX()*53 + playerLoc.getY()*71 + hashCode);
    }





}
