package journeyPlanner;

import java.io.Serializable;

/**
 * Result of a search, ready to be written: journey identifier, departure
 * station and time, arrival station and time, and number of stops between them.
 *
 * Space complexity: O(1).
 *
 * @param journeyID - identifier of the journey
 * @param origin - departure station
 * @param departure - departure time
 * @param destination - arrival station
 * @param arrival - arrival time
 * @param stops - number os stops between the two stations
 */
public record Trip(String journeyID, String origin, String departure, String destination, String arrival, int stops) implements Serializable{
	private static final long serialVersionUID = 1L;
}
