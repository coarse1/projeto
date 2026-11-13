package journeyPlanner;

import java.io.Serializable;  

import dataStructures.Iterator;

/**
 * Journey of the network: identifier, insertion order and stops, in the order of
 * the line.
 *
 * Space complexity: O(k), where k is the number of stops of the journey.
 */
public interface Journey extends Serializable {
	/**
	 * Returns the identifier of the journey, as it was written.
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @return identifier os the journey
	 */
	String getID();

	/**
	 * Returns the insertion order of the journey. It is used to prefer the journey
	 * that has been in the system for longer.
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @return insertion order
	 */
	int getOrder();

	/**
	 * Returns the number of stops of the journey.
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @return number of stops
	 */
	int getNumberOfStops();

	/**
	 * Returns the stop at the given position (0 is the first stop).
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @param position - position of the stop (0 to number of stops - 1)
	 * @return stop at that position
	 */
	Stop getStop(int position);

	/**
	 * Returns the position of the stop of the journey at the given station.
	 *
	 * Time complexity: best case O(1) (it is the first stop); worst case O(k)
	 * (it is the last stop or the journey does not stop there: every stop is checked)
	 *
	 * @param stationIndex - position of the station on the line
	 * @return position of the stop , or -1 if the journey does not stop there
	 */
	int positionOf(int stationIndex);
}
