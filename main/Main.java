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
    enum Status {
    PENDING, PICKED, DELIVERED
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
        Status status;

        Order(String id, String pickup, String drop, int priority) {
            this.id = id;
            this.pickup = pickup;
            this.drop = drop;
            this.priority = priority;
            this.status = Status.PENDING;
        }
    }
    static class Result {
    int distance;
    List<String> path;

    Result(int distance, List<String> path) {
        this.distance = distance;
        this.path = path;
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

int totalOrders = 0;
int totalDistanceAll = 0;
Map<String, Integer> agentDistance = new HashMap<>();
while (!orders.isEmpty()) {
    Order order = orders.poll();

    System.out.println("\nProcessing Order: " + order.id +
            " (priority: " + order.priority + ")");

    Agent bestAgent = null;
    Result bestResult = null;
    int minDistance = Integer.MAX_VALUE;

    // Find nearest agent
    for (Agent agent : agents) {
        Result res = dijkstra(agent.location, order.pickup);

       if (res.distance < minDistance
        || (agent == bestAgent && res.distance <= minDistance + 2)) {
            minDistance = res.distance;
            bestAgent = agent;
            bestResult = res;
        }
    }

    if (bestAgent == null || minDistance == Integer.MAX_VALUE) {
        System.out.println("❌ No agent can reach pickup!");
        continue;
    }

    // From pickup to drop
    Result delivery = dijkstra(order.pickup, order.drop);
    int totalDistance = bestResult.distance + delivery.distance;

    System.out.println("Assigned Agent: " + bestAgent.id);
order.status = Status.PICKED;
    // -------- BUILD FULL PATH --------
    List<String> fullPath = new ArrayList<>();

    // agent -> pickup
    fullPath.addAll(bestResult.path);

    // pickup -> drop (avoid duplicate pickup)
    if (!delivery.path.isEmpty()) {
        fullPath.addAll(delivery.path.subList(1, delivery.path.size()));
    }

System.out.println("Route: ");

for (int i = 0; i < fullPath.size() - 1; i++) {
    System.out.println(bestAgent.id + " moving: "
            + fullPath.get(i) + " -> " + fullPath.get(i + 1));
}

System.out.println("Delivered at " + order.drop);
    System.out.println("Total Distance: " + totalDistance);
    order.status = Status.DELIVERED;
System.out.println("Status: " + order.status);

    // Update agent location
    bestAgent.location = order.drop;
    totalOrders++;
totalDistanceAll += totalDistance;

agentDistance.put(
        bestAgent.id,
        agentDistance.getOrDefault(bestAgent.id, 0) + totalDistance
);
}

System.out.println("\n========= ANALYTICS =========");

System.out.println("Total Orders Delivered: " + totalOrders);
System.out.println("Total Distance Travelled: " + totalDistanceAll);

// Most active agent
String best = "";
int max = 0;

for (String a : agentDistance.keySet()) {
    if (agentDistance.get(a) > max) {
        max = agentDistance.get(a);
        best = a;
    }
}

System.out.println("Most Active Agent: " + best);
    }

    // -------- DIJKSTRA --------
    static Result dijkstra(String src, String dest) {

    Map<String, Integer> dist = new HashMap<>();
    Map<String, String> parent = new HashMap<>();

    for (String node : graph.keySet()) {
        dist.put(node, Integer.MAX_VALUE);
    }

    PriorityQueue<Map.Entry<String, Integer>> pq =
            new PriorityQueue<>(Map.Entry.comparingByValue());

    dist.put(src, 0);
    pq.add(new AbstractMap.SimpleEntry<>(src, 0));
    parent.put(src, null);

    while (!pq.isEmpty()) {
        String curr = pq.poll().getKey();

        for (Edge edge : graph.get(curr)) {
            int newDist = dist.get(curr) + edge.weight;

            if (newDist < dist.get(edge.to)) {
                dist.put(edge.to, newDist);
                parent.put(edge.to, curr);
                pq.add(new AbstractMap.SimpleEntry<>(edge.to, newDist));
            }
        }
    }

    // Build path
    List<String> path = new ArrayList<>();
    String curr = dest;

    while (curr != null) {
        path.add(curr);
        curr = parent.get(curr);
    }

    Collections.reverse(path);

    return new Result(dist.get(dest), path);
}
}