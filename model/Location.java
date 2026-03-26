package model;
import java.util.*;

//repersents a place like A, B.
public class Location {
    public String name;

    public Location(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Location))
            return false;
        Location loc = (Location) o;
        return name.equals(loc.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
public String toString() {
    return name;
}
}