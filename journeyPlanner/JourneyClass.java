package journeyPlanner;


/**
 * Journey implemented with two arrays: the stops and, for each stop, the
 * position of its station on the line. The arrays give direct access to each stop.
 *
 * Space complexity: O(k), where k is the number of stops (two arrays of size k).
 */
public class JourneyClass implements Journey {

	private static final long serialVersionUID = 1L;

	private final String ID;

	private final int order;

	private final Stop[] stops;

	private final int[] stations;

	/**
	 * Creates a journey
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @param ID - identifier of the journey
	 * @param order - insertion order of the journey
	 * @param stops - stops of the journey
	 * @param stations - position on the line of the station of each stop
	 */
	public JourneyClass(String ID,int order, Stop[] stops, int[] stations) {
		this.ID = ID;
		this.order = order;
		this.stops = stops;
		this.stations = stations;
	}


	@Override
	public String getID() {
		return ID;
	}

	@Override
	public int getOrder(){return order;}

	@Override
	public int getNumberOfStops(){return stops.length;}

	@Override
	public Stop getStop(int position){return stops[position];}

	@Override
	public  int positionOf(int stationIndex){
		int position = -1;
		boolean found= false;
		int i = 0;
		while(!found && i < stations.length){
			if(stations[i] == stationIndex){
				position = i;
				found = true;
			}
			i++;
		}
		return position;
	}

	/**
	 * Two journeys are equal if they have the same identifier.
	 *
	 * Time complexity: best case O(1); worst case O(1) (compares the identifiers).
	 *
	 * @param other   the reference object with which to compare.
	 * @return
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Journey journey)) {
			return false;
		}
		return ID.equals(journey.getID());
	}

	/**
	 * Returns the hash code of the identifier.
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @return hash code
	 */
	@Override
	public int hashCode() {
		return ID.hashCode();
	}
}
