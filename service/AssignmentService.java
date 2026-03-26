package service;

import model.*;
import graph.*;
import java.util.*;


public class AssignmentService{
    RouteService routeService = new RouteService();
   
public DeliveryAgent assignAgent(List<DeliveryAgent> agents, Order order, Graph graph) {

    DeliveryAgent bestAgent = null;
    int minDistance = Integer.MAX_VALUE;

    List<Location> bestFullPath = null;

    for (DeliveryAgent agent : agents) {

        if (!agent.available) continue;

        // 🔹 Step 1: Agent → Pickup
        Map<Location, Integer> distToPickup =
                routeService.dijkstra(graph, agent.currLocation);

        Integer d1 = distToPickup.get(order.pickup);
        if (d1 == null) continue;

        List<Location> path1 = routeService.getPath(order.pickup);

        // 🔹 Step 2: Pickup → Drop
        Map<Location, Integer> distToDrop =
                routeService.dijkstra(graph, order.pickup);

        Integer d2 = distToDrop.get(order.drop);
        if (d2 == null) continue;

        List<Location> path2 = routeService.getPath(order.drop);

        // 🔹 Total distance
        int totalDistance = d1 + d2;

        if (totalDistance < minDistance) {
            minDistance = totalDistance;
            bestAgent = agent;

            // 🔹 Merge paths (avoid duplicate pickup)
            List<Location> fullPath = new ArrayList<>(path1);
            fullPath.addAll(path2.subList(1, path2.size()));

            bestFullPath = fullPath;
        }
    }

    if (bestAgent != null) {

        System.out.println("Assigned Agent: " + bestAgent.id);
        System.out.println("Distance: " + minDistance);
        System.out.print("Route: ");

        for (Location loc : bestFullPath) {
            System.out.print(loc.name + " ");
        }
        System.out.println();

        // 🔥 IMPORTANT: Move agent to drop location
        bestAgent.currLocation = order.drop;

        // 🔥 IMPORTANT: Make agent available again
        bestAgent.available = true;
    }

    return bestAgent;
}
    }
