package model;
//real-world delivery person
public class DeliveryAgent{
    public String id;
    public Location currLocation;
    public boolean available;

    public DeliveryAgent(String id , Location location){
this.id = id;
this.currLocation = location;
this.available= true;
    }

}