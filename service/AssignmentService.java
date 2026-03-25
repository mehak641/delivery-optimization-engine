package service;

import model.*;
import graph.*;
import java.util.*;


public class AssignmentService{
    RouteService routeService = new RouteService();
    public DeliveryAgent assignAgent(List<DeliveryAgent> agents , Order order , Graph graph ){
        DeliveryAgent bestagent = null;
        int minDistance = Integer.MAX_VALUE;
            List<Location> bestPath = null;


        for( DeliveryAgent agent : agents){
            if(!agent.available) continue;
            Map<Location , Integer> distMap = 
            routeService.dijkstra(graph, agent.currLocation);

Integer distance = distMap.get(order.pickup);
if (distance == null) continue;
            if(distance<minDistance){
                minDistance = distance;
                bestagent = agent;

                bestPath = routeService.getPath(order.pickup);
            }


                }

                if(bestagent != null){
                    bestagent.available= false;
                    System.out.println("Assigned Agent: " + bestagent.id);
                    System.out.println("Distance: " +minDistance);
                    System.out.print("Route: ");

                    for( Location loc : bestPath){
                        System.out.print(loc.name+" ");
                    }
                    System.out.println();
                }
return bestagent;
    }
}