package service;
//(Dijkstra Algorithm) MOST IMP PART:-
import graph.Graph;
import model.*;
import java.util.*;

public class RouteService {
        public Map<Location, Location> parentMap = new HashMap<>();

    //Input → Graph + starting point,Output → shortest distance to ALL nodes
   public Map<Location , Integer> dijkstra(Graph graph , Location source){
    parentMap.clear();
   //Shortest distance from source → every node
Map<Location , Integer > dist = new HashMap<>();
//always give mini distance node,sorted on base of distance..
   PriorityQueue<Location> pq = new PriorityQueue<>(
    Comparator.comparingInt(dist::get)
   );



   //initialize all distance =infinity
   for(Location loc : graph.adjList.keySet()){
    dist.put(loc, Integer.MAX_VALUE);
   }

   dist.put(source,0);
   parentMap.put(source, null);
   pq.add(source);

   while(!pq.isEmpty()){
    Location curr = pq.poll();
for(Edge edge : graph.getNeighbors(curr)){
    int newDist = dist.get(curr)+edge.distance;
    if(newDist <dist.get(edge.destination)){
        dist.put(edge.destination , newDist);
        parentMap.put(edge.destination, curr);
        pq.add(edge.destination);
    }

}
   }
   return dist;

    
}
public List<Location> getPath(Location destination){
    List<Location> path = new ArrayList<>();
    while(destination != null){
        path.add(destination);
        destination = parentMap.get(destination);
    }
    Collections.reverse(path);
    return  path;

}


}
