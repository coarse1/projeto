package journeyPlanner;

import dataStructures.List; 
import dataStructures.SinglyLinkedList;
import dataStructures.Iterator;
import java.io.*;

import dataStructures.Map;
import dataStructures.Map.Entry;
import dataStructures.SepChainHashTable;

/**
 * Implementation of the transportation network:
 * - line: list of the stations, in order;
 * - stations: hash table name (lower case) -> station;
 * - journeys: hash table identifier (lower case) -> journey;
 *  - in each station, a sorted map (BSTree) with the journeys that depart from it, ordered by departure time and
 *  insertion order.
 *
 * Space complexity: O(S + J*k), where S is the number of stations, J the number of journeys and k the number of stops
 * of a journey (each journey also appears in the sorted maps of k-1 stations).
 */
public class JourneyPlannerClass implements JourneyPlanner {

	private static final long serialVersionUID = 0L;

	private transient List<String> line;

	private transient Map<String, Station> stations;

	private transient Map<String, Journey> journeys;

	private  int nextOrder;

	/**
	 * Creates an empty network.
	 *
	 * Time complexity: best case O(1); worst case O(1) (empty tables with the default size).
	 */
	public JourneyPlannerClass() {
		line = new SinglyLinkedList<String>();
		stations = new SepChainHashTable<>();
		journeys = new SepChainHashTable<>();
		nextOrder = 0;
	}

	@Override
	public void addStation(String name) {
		line.addLast(name);
		stations.put(name.toLowerCase(), new Station(name, line.size()));
	}

	@Override
	public boolean hasID(String ID) {
		return journeys.get(ID.toLowerCase()) != null;
	}

	@Override
	public boolean hasStation(String name){return stations.get(name.toLowerCase())!= null;}

	@Override
	public boolean inOrder(String origin, String destination){
		return getStation(origin).getIndex() < getStation(destination).getIndex();
	}

	@Override
	public void addJourney(String ID, List<Stop> stops) {
		int n = stops.size();
		Stop[] stopArray = new Stop[n];
		int[] stationArray = new int[n];
		Iterator<Stop> it = stops.iterator();
		for(int i = 0; i < n; i++){
			Stop stop = it.next();
			Station station = getStation(stop.name());
			stopArray[i] = new Stop(station.getName(), stop.arrival(), stop.departure());
			stationArray[i] = station.getIndex();
		}
		Journey journey = new JourneyClass(ID, nextOrder++, stopArray, stationArray);
		journeys.put(ID.toLowerCase(), journey);
		registerDepartures(journey);
	}

	@Override
	public Trip findJourney(String origin, String destination, String time) {
		Station from = getStation(origin);
		Station to = getStation(destination);
		Iterator<Entry<DepartureKey, Journey>> it = from.departures();
		while(it.hasNext()){
			Entry<DepartureKey, Journey> entry = it.next();
			if(entry.key().time().compareTo(time) >= 0){
				Journey journey = entry.value();
				int start = journey.positionOf(from.getIndex());
				int end = journey.positionOf(to.getIndex());
				if(start >= 0 && end>=0 && start < end)
					return makeTrip(journey, journey.positionOf(from.getIndex()), end);
			}
		}
		return null;

	}

	@Override
	public Trip findJourneyMinStops(String origin, String destination, String minTime, String maxTime) {
		Station from = getStation(origin);
		Station to = getStation(destination);
		Trip best = null;
		Iterator<Entry<DepartureKey, Journey>> it = from.departures();
		boolean afterInterval = false;
		while(it.hasNext() && !afterInterval){
			Entry<DepartureKey, Journey> entry = it.next();
			String time = entry.key().time();
			if(time.compareTo(maxTime) > 0)
				afterInterval = true;
			else if(time.compareTo(minTime) >= 0){
				Journey journey = entry.value();
				int start = journey.positionOf(from.getIndex());
				int end = journey.positionOf(to.getIndex());
				if(start >= 0 && end>=0 && start < end) {
					Trip trip = makeTrip(journey, journey.positionOf(from.getIndex()), end);
					if(isBetter(trip, best))
						best = trip;
				}
			}
		}
		return best;

	}

	@Override
	public void removeJourney(String ID) {
		Journey journey = journeys.remove(ID.toLowerCase());
		for(int i = 0; i < journey.getNumberOfStops() -1; i++){
			Stop stop = journey.getStop(i);
			getStation(stop.name()).removeDeparture(stop.departure(), journey.getOrder());
		}
	}

	/**
	 * Returns the station with the given name (case-insensitive).
	 *
	 * Time complexity: best case O(1); worst case O(S) (all names in the same position
	 * of the hash table); O(1) expected.
	 *
	 * @param name - name of the station
	 * @return the station, or null if it does not exist
	 */
	private Station getStation(String name){return stations.get(name.toLowerCase());}

	/**
	 * Inserts the departures of the journey (every stop except the last, which has no departure) in the sorted maps of
	 * the stations.
	 *
	 * Time complexity (k = stops of the journey, P = departures of a station): best case O(k); worst case O(k*P)
	 * (degenerate trees); on average O(k log P).
	 *
	 * @param journey - the journey
	 */
	private void registerDepartures(Journey journey){
		for(int i = 0; i < journey.getNumberOfStops() -1; i++){
			Stop stop = journey.getStop(i);
			getStation(stop.name()).addDeparture(stop.departure(), journey.getOrder(), journey );
		}
	}

	/**
	 * Builds the result of a search between two stops of a journey (the number of stops is the difference between
	 * their positions).
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @param journey - the journey
	 * @param start - position of the stop at the origin
	 * @param end - position of the stop ate the destination
	 * @return result of the search
	 */
	private Trip makeTrip(Journey journey, int start, int end){
		Stop first = journey.getStop(start);
		Stop last = journey.getStop(end);
		return  new Trip(journey.getID(), first.name(), first.departure(), last.name(), last.arrival(), end- start);
	}

	/**
	 * Checks whether a result is better than the best one so far: fewer stops, then earlier arrival, then earlier
	 * departure.
	 *
	 * Time complexity: best case O(1); worst case O(1) (compares numbers and times).
	 *
	 * @param trip - result to compare
	 * @param best - best result so far(null if there is none)
	 * @return true if trip is better than best
	 */
	private boolean isBetter(Trip trip, Trip best) {
		if(best == null) {
			return true;
		}
		
		if(trip.stops() != best.stops()) {
			return trip.stops() < best.stops();
		}
		
		int betterArrival = trip.arrival().compareTo(best.arrival());
		if(betterArrival != 0) {
			return betterArrival < 0;
		}
		
		int betterDep = trip.departure().compareTo(best.departure());
		if(betterDep != 0) {
			return betterDep < 0;
		}
		
		return false;
	}

	/**
	 * Writes the insertion counter, the stations of the line and the journeys. The stations and their sorted maps are
	 * not written: they are rebuilt when reading.
	 *
	 * Time complexity: best case O(S + J*k); worst case O(S + J*k) (every station and every journey, with its stops,
	 * is written).
	 *
	 * @param oos - output stream
	 * @throws IOException
	 */
	@Serial
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();

		oos.writeInt(line.size());
		Iterator<String> it = line.iterator();
		while (it.hasNext()) {
			oos.writeObject(it.next());
		}


		oos.writeInt(journeys.size());
		Iterator<String> keys = journeys.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			oos.writeObject(key);
			oos.writeObject(journeys.get(key));
		}
	}

	/**
	 * Reads the line and the journeys and rebuilds the stations and the sorted maps of departures.
	 *
	 * Time complexity: best case O(S + J*k); worst case O(S + J*k*P) (degenerate trees); on average O(S + J*k log P).
	 *
	 * @param ois
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Serial
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();

		int lineSize = ois.readInt();
		this.line = new SinglyLinkedList<String>();
		this.stations = new SepChainHashTable<String, Station>();
		for (int i = lineSize; i > 0; i--) {
			addStation((String) ois.readObject());
		}

		int mapSize = ois.readInt();
		this.journeys = new SepChainHashTable<String, Journey>();
		for (int i = mapSize; i > 0; i--) {
			String key = (String) ois.readObject();
			Journey journey = (Journey) ois.readObject();
			journeys.put(key, journey);
			registerDepartures(journey);
		}
	}
}
