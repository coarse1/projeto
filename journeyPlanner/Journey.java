package journeyPlanner;

import java.io.Serializable;  

import dataStructures.Iterator;

public interface Journey extends Serializable {
	String getID();

	int getOrder();

	int getNumberOfStops();

	Stop getStop(int position);

	int positionOf(int stationIndex);
}
