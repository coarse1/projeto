package journeyPlanner;

import dataStructures.List;
import java.io.Serializable;

public interface JourneyPlanner extends Serializable {
	void addStation(String name);

	boolean hasID(String ID);

	boolean hasStation(String name);

	boolean inOrder(String origin, String destination);

	void addJourney(String ID, List<Stop> stops);

	Trip findJourney(String origin, String destination, String time);

	Trip findJourneyMinStops(String origin, String destination, String minTime, String maxTime);

	void removeJourney(String ID);

}
