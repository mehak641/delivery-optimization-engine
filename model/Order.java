package model;
public class Order{
public String id;
public Location pickup;
public Location drop;
public int priority;
//Represents a delivery request
public Order(String id , Location pickup , Location drop , int priority){
    this.id = id;
    this.pickup = pickup;
    this.drop = drop;
    this.priority = priority;
}
}