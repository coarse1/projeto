package journeyPlanner;

import java.io.Serializable;

public record Trip(String journeyID, String origin, String departure, String destination, String arrival, int stops) implements Serializable{
	private static final long serialVersionUID = 1L;
}
