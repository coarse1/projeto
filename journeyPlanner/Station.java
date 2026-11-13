package journeyPlanner;

import dataStructures.BSTree;
import dataStructures.Iterator;
import dataStructures.SortedMap;
import dataStructures.Map.Entry;

/**
 * Station of the line: its name as written in the line definition, its position on the line and a sorted map (BSTree)
 * with the journeys that depart from it, ordered by departure time and insertion order.
 *
 * Space complexity: O(P), where P is the number of journeys that depart from the station.
 */
public class Station {

    private final String name;
    private final int index;
    private final SortedMap<DepartureKey, Journey> departures;

    /**
     * Returns the name of the station, as written in the line definition.
     *
     * Time complexity: best case O(1); worst case O(1).
     *
     * @param name - name of the station
     * @param index - position of the station in the line
     */
    Station(String name, int index){
        this.name = name;
        this.index = index;
        departures = new BSTree<>();
    }

    /**
     * Returns the name of the station, as written in the line definition.
     *
     * Time complexity: best case O(1); worst case O(1).
     *
     * @return
     */
    String getName(){
        return name;
    }

    /**
     * Returns the position of the station on the line.
     *
     * Time complexity: best case O(1); worst case O(1).
     *
     * @return
     */
    int getIndex(){
        return  index;
    }

    /**
     * Inserts a departure of a journey on the sorted map of the station.
     *
     * Time complexity (P = departures of the station): best case O(1) (empty map); worst case O(P) (degenerate tree);
     *
     * @param time - departure time (HH:mm)
     * @param order - insertion order of the jorney
     * @param journey - the journey
     */
    void addDeparture(String time, int order, Journey journey){
        departures.put(new DepartureKey(time, order), journey);
    }

    /**
     * Removes a departure from the sorted map of the station.
     *
     * Time complexity (P = departures of the station): best case O(1) (it is at the root); worst case O(P)
     * (degenerate tree);
     * @param time - departure time (HH:mm)
     * @param order - insertion order of the jorney
     */
    void removeDeparture(String time, int order){
        departures.remove(new DepartureKey(time, order));
    }

    /**
     * Returns the departures of the station, ordered by time and insertion order.
     *
     * Time complexity (P = departures of the station): best case O(1) (no departures); worst case O(P)
     * (goes to the leftmost node of a degenerate tree).
     *
     * @return iterator of the departures
     */
    Iterator<Entry<DepartureKey, Journey>> departures(){
        return  departures.iterator();
    }
}
