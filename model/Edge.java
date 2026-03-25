package model;
//roads between locations.
public class Edge{
    public Location destination;
    public int distance;

    public Edge(Location destination , int distance){
        this .destination = destination;
        this.distance= distance;
    }
}