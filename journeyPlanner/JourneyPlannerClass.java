package journeyPlanner;

import dataStructures.List; 
import dataStructures.SinglyLinkedList;
import dataStructures.Iterator;
import java.io.*;

import dataStructures.Map;
import dataStructures.Map.Entry;
import dataStructures.SepChainHashTable;

public class JourneyPlannerClass implements JourneyPlanner {

	private static final long serialVersionUID = 0L;

	private transient List<String> line;

	private transient Map<String, Station> stations;

	private transient Map<String, Journey> journeys;

	private  int nextOrder;

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
				int end = journey.positionOf(to.getIndex());
				if(end>=0)
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
				int end = journey.positionOf(to.getIndex());
				if(end>=0) {
					Trip trip = makeTrip(journey, journey.positionOf(from.getIndex()), end);
					if(best == null || trip.stops() < best.stops() ||
							(trip.stops() == best.stops() && trip.arrival().compareTo(best.arrival())<0))
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

	private Station getStation(String name){return stations.get(name.toLowerCase());}

	private void registerDepartures(Journey journey){
		for(int i = 0; i < journey.getNumberOfStops() -1; i++){
			Stop stop = journey.getStop(i);
			getStation(stop.name()).addDeparture(stop.departure(), journey.getOrder(), journey );
		}
	}

	private Trip makeTrip(Journey journey, int start, int end){
		Stop first = journey.getStop(start);
		Stop last = journey.getStop(end);
		return  new Trip(journey.getID(), first.name(), first.departure(), last.name(), last.arrival(), end- start);
	}

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
