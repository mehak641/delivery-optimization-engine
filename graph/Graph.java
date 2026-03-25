package graph;

import model.*;
import java.util.*;


public class Graph {
        // key->location(A, B..) value-> list of neighbours...

    public Map<Location, List<Edge>> adjList = new HashMap<>();

     // adds location only and value list is still empty..
    public void addLocation(Location loc) {
        adjList.putIfAbsent(loc, new ArrayList<>());

    }
   
//function to connect two  locations..
    public void addRoad(Location src, Location dest, int distance) {
        adjList.get(src).add(new Edge(dest, distance)); //src->dest with distance..
        adjList.get(dest).add(new Edge(src , distance));

    }     

    //return all neighbours of a location
    public List<Edge> getNeighbors(Location loc){
        return adjList.get(loc);
    }

}
