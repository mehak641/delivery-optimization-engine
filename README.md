# 🚚 Smart Delivery Routing System

A Java-based simulation of a delivery system that assigns agents to orders using **priority scheduling** and **shortest path algorithms (Dijkstra)**.

---

## 📌 Features

* 🧭 Graph-based city map (custom input)
* 🚀 Shortest path calculation using Dijkstra’s Algorithm
* 📦 Priority-based order processing (Max Heap)
* 👨‍✈️ Dynamic agent assignment
* 🔄 Real-time agent location updates
* 🧠 Smart decision-making for nearest agent selection
* ⚡ Optimized input (single-line format)

---

## 🛠️ Tech Stack

* Java (Core Java, OOP)
* Data Structures:

  * Graph (Adjacency List)
  * Priority Queue (Max Heap)
* Algorithm:

  * Dijkstra’s Algorithm

---

## 📥 Input Format (User-Friendly)

### 1. Nodes

A B C

### 2. Edges

A-B-5,B-C-10,C-A-15

### 3. Agents

D1-A,D2-B

### 4. Orders

Q2-B-C-5,Q1-C-A-2,Q3-A-C-1

---

## 📤 Sample Output

Processing Order: Q2 (priority: 5)
Assigned Agent: D2
Route: B -> C
Total Distance: 10

Processing Order: Q1 (priority: 2)
Assigned Agent: D2
Route: C -> A
Total Distance: 15

Processing Order: Q3 (priority: 1)
Assigned Agent: D1
Route: A -> C
Total Distance: 15

---

## 🧠 How It Works

1. The system builds a graph using user input.
2. Orders are stored in a **priority queue** (highest priority first).
3. For each order:

   * The nearest available agent is selected using **Dijkstra**.
   * Total delivery distance is calculated.
   * Agent location is updated after delivery.
4. The process continues until all orders are completed.

---

## 🚀 Future Improvements

* 📍 Show actual shortest path (not just distance)
* 🌐 Convert into REST API (Node.js / Spring Boot)
* 🖥️ Add frontend dashboard (React)
* 🗄️ Database integration (MongoDB/MySQL)
* ⏱️ Real-time tracking simulation

---

## 🎯 Learning Outcomes

* Applied graph algorithms in real-world scenario
* Understood priority-based scheduling systems
* Improved problem-solving and system design skills
* Built a scalable backend logic simulation
