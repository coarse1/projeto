package mainEnum;

/**
 * Messages written by the application. Each constant keeps the format
 * string that is given to printf.
 *
 * Space complexity: O(1) (fixed number of messages).
 */
public enum Interactions {
	HELP("addJourney: Inserts a new journey into the transportation network.%nfindJourney: Finds the earliest journey that allows a passenger to travel from one station to another.%nfindJourneyMinStops: Finds the direct journey with the minimum number of stops.%nremoveJourney: Removes the given journey from the transportation network.%nhelp: Displays the list of available commands.%nquit: Saves the transportation network and terminates the application.%n"),
	NETWORK_RESTORED("Transportation network restored successfully.%n"),
	NETWORK_NOT_FOUND("Transportation network not found.%nLoading line definition. %d stations loaded.%n"),
	NO_LINE_DEF("No line definition.%n"), JOURNEY_EXISTS("Journey already exists.%n"),
	JOURNEY_ADDED("Journey successfully added.%n"), NETWORK_SAVED("Transportation network saved.%nGoodbye.%n"),
	JOURNEY_REMOVED("Journey successfully removed.%n"), INVALID_JOURNEY("Invalid journey.%n"),
	INVALID_STATION("Invalid station.%n"), INVALID_INTERVAL("Invalid interval time.%n"), INVALID_TIME("Invalid time.%n"),
	WRONG_ORDER("Stations are not in the correct order.%n"), NO_DIRECT_JOURNEY("No direct journey is available.%n"),
	TRIP("Journey: %s%nDeparture station: %s%nDeparture time: %s%nArrival station: %s%nArrival time: %s%n"),
	STOPS("Stops: %d%n");

	private final String string;

	/**
	 * Creates a message with the given text.
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @param string - text of the message
	 */
	Interactions(String string) {
		this.string = string;
	}

	/**
	 * Returns the text of the message
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @return text of the message
	 */
	public final String getString() {
		return string;
	}

}
