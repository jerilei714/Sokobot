package solver;

import java.awt.Point;
import java.util.*;

public class SokoBot {
  PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
  Heuristics heuFunc;
  Set<Point> wallCoordinates;
  Set<Point> goalCoordinates;
  Set<Point> deadlocks;

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    wallCoordinates = mapContCoorFinder(mapData,height,width,'#');
    goalCoordinates = mapContCoorFinder(mapData,height,width,'.');
    heuFunc = new Heuristics(goalCoordinates);
    Set<Node> visited = new HashSet<>();

    //initial state getter
    Set<Point> crateCoordinates = new HashSet<>();
    Point player_loc = null;

    for(int i=0; i<height; i++){
      for(int j=0; j<width;j++){
        if(itemsData[i][j] =='$') crateCoordinates.add(new Point(i,j));
        else if(itemsData[i][j]=='@') player_loc = new Point(i,j);
      }
    }

    int initialCost = heuFunc.getCost(crateCoordinates);

    Node initialState = new Node(crateCoordinates,player_loc,"",initialCost,goalCoordinates);


    pq.add(initialState);
    while(!pq.isEmpty()){
      Node currentState = pq.poll();
      visited.add(currentState);

      if(currentState.isGoalState()) {
        return currentState.getActions();
      }

      Set<Character> possibleMoves = getvalidMoves(currentState);

      for(char move : possibleMoves){
        Node child = getSuccS(currentState,move);

        if(!visited.contains(child)){
          pq.add(child);
        }
      }
    }
    return "";
  }


  private Set<Point> mapContCoorFinder(char[][] mapData, int height, int width, char lookingFor){
    Set<Point>  mapContCoordinates = new HashSet<>();

    for(int i=0; i<height; i++){
      for(int j=0; j<width;j++){
        if(mapData[i][j]==lookingFor)  mapContCoordinates.add(new Point(i,j));
      }
    }
    return  mapContCoordinates;
  }
  private Set<Character> getvalidMoves(Node node){
    Set<Character> validMoves = new HashSet<>();
    int x = (int) node.getPlayerLoc().getX();
    int y = (int) node.getPlayerLoc().getY();
    Point upMap = new Point(x-1,y); //coordinate in front of player
    Point downMap = new Point(x+1,y);
    Point leftMap = new Point(x,y-1);
    Point rightMap = new Point(x,y+1);

    if(!wallCoordinates.contains(upMap)){
      //if the set of crate coordinates doesn't have the coordinate
      if(!node.getState().contains(upMap)){
        validMoves.add('u');
      } else if (!isCDeadlock(upMap) && node.getState().contains(upMap)){
        upMap.setLocation(x-2,y);
        if(!wallCoordinates.contains(upMap)&& !node.getState().contains(upMap))
          validMoves.add('u');
        //if the coordinate is in crate coordinate,
        // check if the next coordinate above is free
      }
    }

    if(!wallCoordinates.contains(downMap)){
      //if the set of crate coordinates doesn't have the coordinate
      if(!node.getState().contains(downMap)){
        validMoves.add('d');
      } else if (!isCDeadlock(downMap) && node.getState().contains(downMap)){
        downMap.setLocation(x+2,y);
        if(!wallCoordinates.contains(downMap)&& !node.getState().contains(downMap))
          validMoves.add('d');
        //if the coordinate is in crate coordinate,
        // check if the next coordinate above is free
      }
    }

    if(!wallCoordinates.contains(leftMap)){
      //if the set of crate coordinates doesn't have the coordinate
      if(!node.getState().contains(leftMap)){
        validMoves.add('l');
      } else if(!isCDeadlock(leftMap) && node.getState().contains(leftMap)){
        leftMap.setLocation(x,y-2);
        if(!wallCoordinates.contains(leftMap)&& !node.getState().contains(leftMap))
          validMoves.add('l');
        //if the coordinate is in crate coordinate,
        // check if the next coordinate above is free
      }
    }

    if(!wallCoordinates.contains(rightMap)){
      //if the set of crate coordinates doesn't have the coordinate
      if(!node.getState().contains(rightMap)){
        validMoves.add('r');
      } else if (!isCDeadlock(rightMap) && node.getState().contains(rightMap)){
        rightMap.setLocation(x,y+2);
        if(!wallCoordinates.contains(rightMap)&& !node.getState().contains(rightMap))
          validMoves.add('r');
        //if the coordinate is in crate coordinate,
        // check if the next coordinate above is free
      }
    }

    return validMoves;
  }


  public boolean isCDeadlock(Point point){
    int x = (int) point.getX();
    int y = (int) point.getY();
    boolean upperwing = wallCoordinates.contains(new Point(x-1,y));
    boolean lowerwing = wallCoordinates.contains(new Point(x+1,y));
    boolean lefttwing = wallCoordinates.contains(new Point(x,y-1));
    boolean rightwing = wallCoordinates.contains(new Point(x,y+1));

    return (upperwing || lowerwing) && (lefttwing || rightwing);
  }

  private Node getSuccS (Node node, char move){
    int xLoc = (int) node.getPlayerLoc().getX();
    int yLoc = (int) node.getPlayerLoc().getY();

    Set<Point> newState = deepCopyState(node.getState());

    int newXLoc = xLoc;
    int newYLoc = yLoc;

    switch (move){
      case 'u' -> {newXLoc--;}
      case 'd'-> {newXLoc++;}
      case 'l' -> {newYLoc--;}
      case 'r' -> {newYLoc++;}
    }
      //crate a new coordinate for player position
    Point newPoint = new Point(newXLoc,newYLoc);

      //if that new point is a crate, move it
    if(newState.contains(newPoint)){
      int newCrateXLoc = newXLoc + (newXLoc-xLoc);
      int newCrateYLoc = newYLoc + (newYLoc-yLoc);
        //add the new coordinate of moved crate
      newState.add(new Point(newCrateXLoc,newCrateYLoc));
        //remove the old position of crate
      newState.remove(newPoint);
    }
    String newString = node.getActions() + move;

    int newStateCost = heuFunc.getCost(newState);

    return new Node(newState,newPoint,newString,newStateCost,goalCoordinates);
  }

  private Set<Point> deepCopyState(Set<Point> source) {
    Set<Point> clone = new HashSet<>();
    for (Point coordinate : source){
      clone.add(new Point(coordinate));
    }
    return clone;
  }

  void trace(Set<Point> points){
    for(Point coordinate: points ){
      System.out.print(coordinate+" ");
    }
    System.out.println();
  }
  void trace(Point loc){
    System.out.println(loc.getX() + " "+ loc.getY());
  }
}
