package journeyPlanner;

import dataStructures.List;
import java.io.Serializable;

/**
 * Transportation network: one line of stations and the journeys that run along it.
 *
 * Space complexity: O(S + J*k), where S is the number of stations, J the number of
 * journeys and k the number of stops of a journey.
 */
public interface JourneyPlanner extends Serializable {
	/**
	 * Adds a station at the end of the line.
	 *
	 * Time complexity: best case O(1); worst case O(S) (rehash of the table ofstations); O(1) expected.
	 *
	 * @param name - name of stations
	 */
	void addStation(String name);

	/**
	 * Checks whether a journey with the given identifier exists (case-insensitive).
	 *
	 * Time complexity: best case O(1); worst case O(J) (all identifiers in the same position of the hash table);
	 * O(1) expected.
	 *
	 * @param ID - identifier of the journey
	 * @return true if the journey exists
	 */
	boolean hasID(String ID);

	/**
	 * Checks whether the station exists on the line (case-insensitive).
	 *
	 * Time complexity: best case O(1); worst case O(S) (all names in the same position of the hash table);
	 * O(1) expected.
	 * @param name
	 * @return
	 */
	boolean hasStation(String name);

	/**
	 * Checks whether the origin comes before the destination on the line.
	 *
	 * Time complexity: best case O(1); worst case O(S) (two searches in the hash table); O(1) expected.
	 *
	 * @param origin - name of the origin station
	 * @param destination - name of the destination station
	 * @return true if the origin comes before destination
	 */
	boolean inOrder(String origin, String destination);

	/**
	 * Adds a journey to the network.
	 *
	 * Time complexity (k = stops of the journey, P = departures of a station): best case O(k); worst case O(k*P)
	 * (each departure is inserted in a degenerate tree); on average O(k log P).
	 *
	 * @param ID - identifier of the journey
	 * @param stops - stops of the journey, in the order of the line
	 */
	void addJourney(String ID, List<Stop> stops);

	/**
	 * Finds the journey with the earliest departure, at or after the given time, that goes from the origin to
	 * the destination. Ties are broken by the journey that has been in the system for longer.
	 *
	 * Time complexity (k = stops of a journey, P = departures of the origin): best case O(1) (the first departure is
	 * the answer); worst case O(P*k) (every departure is visited and the destination is searched among k stops).
	 *
	 * @param origin - name of the origin station
	 * @param destination - name of the destination station
	 * @param time - time of arrival of the passenger(HH:mm)
	 * @return result of the search, or null if there is no direct journey
	 */
	Trip findJourney(String origin, String destination, String time);

	/**
	 * Finds the journey that departs within the interval and has the fewest stops.
	 * Ties are broken by earlier arrival, earlier departure and the journey that has
	 * been in the system for longer.
	 *
	 * Time complexity (k = stops of a journey, P = departures of the origin):
	 * best case O(1) (no departures before the end of the interval);
	 * worst case O(P*k) (every departure is inside the interval).
	 *
	 * @param origin - name of the origin station
	 * @param destination - name of the destination station
	 * @param minTime - start of the interval (HH:mm)
	 * @param maxTime - end of the interval (HH:mm)
	 * @return result of the search, or null if there is no direct journey
	 */
	Trip findJourneyMinStops(String origin, String destination, String minTime, String maxTime);

	/**
	 * Removes the journey with the given identifier.
	 *
	 * Time complexity (k = stops of the journey, P = departures of a station): best case O(k); worst case O(k*P)
	 * (each departure is removed from a degenerate tree); on average O(k log P).
	 * @param ID - identifier of the journey
	 */
	void removeJourney(String ID);

}
