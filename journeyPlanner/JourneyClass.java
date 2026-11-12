package journeyPlanner;



public class JourneyClass implements Journey {

	private static final long serialVersionUID = 1L;

	private final String ID;

	private final int order;

	private final Stop[] stops;

	private final int[] stations;

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

	@Override
	public int hashCode() {
		return ID.hashCode();
	}
}
