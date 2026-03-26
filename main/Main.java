package main;

import java.util.*;

public class Main {

    // -------- EDGE --------
    static class Edge {
        String to;
        int weight;

        Edge(String to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    // -------- AGENT --------
    static class Agent {
        String id;
        String location;

        Agent(String id, String location) {
            this.id = id;
            this.location = location;
        }
    }

    // -------- ORDER --------
    static class Order {
        String id;
        String pickup;
        String drop;
        int priority;

        Order(String id, String pickup, String drop, int priority) {
            this.id = id;
            this.pickup = pickup;
            this.drop = drop;
            this.priority = priority;
        }
    }

    static Map<String, List<Edge>> graph = new HashMap<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // -------- NODES INPUT --------
        System.out.println("Enter nodes (space separated):");
        String[] nodes = sc.nextLine().trim().split(" ");

        for (String node : nodes) {
            graph.put(node, new ArrayList<>());
        }

        // -------- EDGES INPUT --------
        System.out.println("Enter edges (format: A-B-5,B-C-10):");
        String[] edges = sc.nextLine().split(",");

        for (String e : edges) {
            String[] parts = e.split("-");

            String from = parts[0].trim();
            String to = parts[1].trim();
            int weight = Integer.parseInt(parts[2].trim());

            if (!graph.containsKey(from) || !graph.containsKey(to)) {
                System.out.println("❌ Invalid edge skipped: " + e);
                continue;
            }

            graph.get(from).add(new Edge(to, weight));
            graph.get(to).add(new Edge(from, weight));
        }

        // -------- AGENTS INPUT --------
        System.out.println("Enter agents (format: D1-A,D2-B):");
        String[] agentInput = sc.nextLine().split(",");

        List<Agent> agents = new ArrayList<>();

        for (String a : agentInput) {
            String[] parts = a.split("-");

            String id = parts[0].trim();
            String loc = parts[1].trim();

            if (!graph.containsKey(loc)) {
                System.out.println("❌ Invalid agent location skipped: " + a);
                continue;
            }

            agents.add(new Agent(id, loc));
        }

        // -------- ORDERS INPUT --------
        System.out.println("Enter orders (format: Q2-B-C-5,Q1-C-A-2):");
        String[] orderInput = sc.nextLine().split(",");

        PriorityQueue<Order> orders = new PriorityQueue<>(
                (x, y) -> y.priority - x.priority
        );

        for (String o : orderInput) {
            String[] parts = o.split("-");

            String id = parts[0].trim();
            String pickup = parts[1].trim();
            String drop = parts[2].trim();
            int priority = Integer.parseInt(parts[3].trim());

            if (!graph.containsKey(pickup) || !graph.containsKey(drop)) {
                System.out.println("❌ Invalid order skipped: " + o);
                continue;
            }

            orders.add(new Order(id, pickup, drop, priority));
        }

        System.out.println("\n=================== PROCESSING ===================");

        // -------- PROCESS ORDERS --------
        while (!orders.isEmpty()) {
            Order order = orders.poll();

            System.out.println("\nProcessing Order: " + order.id +
                    " (priority: " + order.priority + ")");

            Agent bestAgent = null;
            int minDistance = Integer.MAX_VALUE;

            for (Agent agent : agents) {
                int dist = dijkstra(agent.location, order.pickup);

                if (dist < minDistance) {
                    minDistance = dist;
                    bestAgent = agent;
                }
            }

            if (bestAgent == null || minDistance == Integer.MAX_VALUE) {
                System.out.println("❌ No agent can reach pickup!");
                continue;
            }

            int deliveryDist = dijkstra(order.pickup, order.drop);
            int totalDistance = minDistance + deliveryDist;

            System.out.println("Assigned Agent: " + bestAgent.id);
           if (bestAgent.location.equals(order.pickup)) {
    System.out.println("Route: " + order.pickup + " -> " + order.drop);
} else {
    System.out.println("Route: " + bestAgent.location + " -> " +
            order.pickup + " -> " + order.drop);
}
            System.out.println("Total Distance: " + totalDistance);

            // Update agent location
            bestAgent.location = order.drop;
        }

        sc.close();
    }

    // -------- DIJKSTRA --------
    static int dijkstra(String src, String dest) {

        if (!graph.containsKey(src) || !graph.containsKey(dest)) {
            return Integer.MAX_VALUE;
        }

        Map<String, Integer> dist = new HashMap<>();

        for (String node : graph.keySet()) {
            dist.put(node, Integer.MAX_VALUE);
        }

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>(Map.Entry.comparingByValue());

        dist.put(src, 0);
        pq.add(new AbstractMap.SimpleEntry<>(src, 0));

        while (!pq.isEmpty()) {
            String curr = pq.poll().getKey();

            for (Edge edge : graph.get(curr)) {
                int newDist = dist.get(curr) + edge.weight;

                if (newDist < dist.get(edge.to)) {
                    dist.put(edge.to, newDist);
                    pq.add(new AbstractMap.SimpleEntry<>(edge.to, newDist));
                }
            }
        }

        return dist.getOrDefault(dest, Integer.MAX_VALUE);
    }
}