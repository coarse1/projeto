package journeyPlanner;

import java.io.Serializable;

/**
 * Stop of a journey: name of the station, arrival time and departure time
 * (HH:mm). The first stop has no arrival (null) and the last one has no departure
 * (null).
 *
 * Space complexity: O(1).
 *
 * @param name - name of the station
 * @param arrival - arrival time(null at the first stop)
 * @param departure - departure time (null at the last stop)
 */
public record Stop(String name, String arrival, String departure) implements Serializable {
	private static final long serialVersionUID = 1L;
}
