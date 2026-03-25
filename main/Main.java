package main;

import model.*;
import graph.Graph;
import service.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Graph graph = new Graph();

        Location A = new Location("A");
        Location B = new Location("B");
        Location C = new Location("C");

        graph.addLocation(A);
        graph.addLocation(B);
        graph.addLocation(C);

        graph.addRoad(A, B, 5);
        graph.addRoad(B, C, 10);
        graph.addRoad(A, C, 15);

        DeliveryAgent agent1 = new DeliveryAgent("D1", A);
        DeliveryAgent agent2 = new DeliveryAgent("D2", B);

        List<DeliveryAgent> agents = Arrays.asList(agent1, agent2);

        Order order = new Order("O1", C, A, 1);

        AssignmentService service = new AssignmentService();
        DeliveryAgent assigned = service.assignAgent(agents, order, graph);

    }
}