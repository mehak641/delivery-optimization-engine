package model;
//real-world delivery person
public class DeliveryAgent{
    public String id;
    public Location currLocation;
    public boolean available = true;

    public DeliveryAgent(String id , Location location){
this.id = id;
this.currLocation = location;
this.available= true;
    }
    @Override
public String toString() {
    return "Agent{id='" + id + "', location=" + currLocation + "}";
}

}