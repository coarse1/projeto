package journeyPlanner;

import dataStructures.BSTree;
import dataStructures.Iterator;
import dataStructures.SortedMap;
import dataStructures.Map.Entry;
public class Station {

    private final String name;
    private final int index;
    private final SortedMap<DepartureKey, Journey> departures;

    Station(String name, int index){
        this.name = name;
        this.index = index;
        departures = new BSTree<>();
    }

    String getName(){
        return name;
    }

    int getIndex(){
        return  index;
    }

    void addDeparture(String time, int order, Journey journey){
        departures.put(new DepartureKey(time, order), journey);
    }

    void removeDeparture(String time, int order){
        departures.remove(new DepartureKey(time, order));
    }

    Iterator<Entry<DepartureKey, Journey>> departures(){
        return  departures.iterator();
    }
}
