package journeyPlanner;

import java.io.Serializable;

/**
 * Key of the sorted map of departures of a station. The keys are ordered by
 * departure time and, in case of a tie, by insertion order, so iterating over the
 * map gives the departures in the order used to break ties (earlier time first,
 * then the journey that has been in the system for longer).
 *
 * Space complexity: O(1)
 *
 * @param time - departure time (HH:mm)
 * @param order - insertion order of the journey
 */
public record DepartureKey(String time, int order) implements Comparable<DepartureKey>, Serializable {

	/**
	 * Compares this key with another one: first the departure time (times in the
	 * format HH:mm can be compared as strings), then the insertion order.
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @param other the object to be compared.
	 * @return negative if the key comes before, zero if equal or positive if it comes after the other one
	 */
	@Override
	public int compareTo(DepartureKey other) {
		int cmp = time.compareTo(other.time());
		if (cmp != 0)
			return cmp;
		return Integer.compare(order, other.order());
	}
}
